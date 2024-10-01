package com.example.mobile.model.spell

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.apiManager.ApiServiceImpl
import com.example.mobile.data.DungeonsHelperDatabase
import com.example.mobile.data.Spell
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SpellViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: ApiServiceImpl // Injected via Hilt
) : ViewModel() {

    private val dungeonsHelperDatabase = DungeonsHelperDatabase.getDatabase(context)

    // StateFlow for the list of spells
    private val _spellList = MutableStateFlow<List<Spell>>(emptyList())
    val spellList = _spellList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val spells = dungeonsHelperDatabase.spellDao().getAll()
            _spellList.value = spells
        }
    }

    private var _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private var _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    suspend fun addSpell(name: String, level: Int, damage: String, damageType: String, description: String, castingTime: String) {
        val newSpell = Spell(name, level, damage, damageType, description, castingTime)

        withContext(Dispatchers.IO) {
            dungeonsHelperDatabase.spellDao().insert(newSpell)
        }

        val updatedList = _spellList.value + newSpell
        _spellList.emit(updatedList)
    }

    // Fetch spells from API and update the spell list
    fun getSpells() {
        _loadingState.value = true
        api.getSpells(
            context,
            onSuccess = { fetchedSpells ->
                viewModelScope.launch {
                    val currentSpells = _spellList.value
                    val updatedList = currentSpells + fetchedSpells.filterNot { fetchedSpell ->
                        currentSpells.any { it.name == fetchedSpell.name }
                    }
                    _spellList.emit(updatedList)
                    _loadingState.emit(false)
                }
            },
            onFail = {
                viewModelScope.launch {
                    _loadingState.emit(false)
                    _errorState.emit("Failed to fetch spells.")
                }
            },
            loadingFinished = {
                _loadingState.value = false
            }
        )
    }
}
