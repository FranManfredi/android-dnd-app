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
import javax.inject.Inject

@HiltViewModel
class SpellViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: ApiServiceImpl // Injected via Hilt
) : ViewModel() {

    private val dungeonsHelperDatabase = DungeonsHelperDatabase.getDatabase(context)

    private val _spellList = MutableStateFlow<List<Spell>>(emptyList())
    val spellList = _spellList.asStateFlow()

    private var _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private var _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val spells = dungeonsHelperDatabase.spellDao().getAll()
            _spellList.value = spells
        }
    }

    suspend fun addSpell(
        name: String,
        level: Int,
        damage: String,
        damageType: String,
        description: String,
        castingTime: String
    ) {
        val newSpell = Spell(name, level, damage, damageType, description, castingTime)

        viewModelScope.launch(Dispatchers.IO) {
            // Insert spell into the database
            dungeonsHelperDatabase.spellDao().insert(newSpell)

            // Fetch and emit the updated list
            val updatedList = dungeonsHelperDatabase.spellDao().getAll()
            _spellList.emit(updatedList)
        }
    }

    // Fetch spells from API and update the list
    fun getSpells() {
        _loadingState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val spells = dungeonsHelperDatabase.spellDao().getAll()
                _spellList.emit(spells)
                _loadingState.emit(false)
            } catch (e: Exception) {
                _loadingState.emit(false)
                _errorState.emit("Failed to load spells from database.")
            }
        }

        // Optionally fetch from the API
        api.getSpells(
            context,
            onSuccess = { fetchedItems ->
                viewModelScope.launch(Dispatchers.IO) {
                    val currentItems = _spellList.value
                    val newItems = fetchedItems.filterNot { fetchedItem ->
                        currentItems.any { it.name == fetchedItem.name }
                    }
                    newItems.forEach { dungeonsHelperDatabase.spellDao().insert(it) }
                    val updatedList = dungeonsHelperDatabase.spellDao().getAll()
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
