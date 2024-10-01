package com.example.mobile.model.classes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.apiManager.ApiServiceImpl
import com.example.mobile.data.CharClass
import com.example.mobile.data.DungeonsHelperDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharClassViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: ApiServiceImpl // Injected via Hilt
) : ViewModel() {

    private val dungeonsHelperDatabase = DungeonsHelperDatabase.getDatabase(context)

    // StateFlow for the list of character classes
    private val _classList = MutableStateFlow<List<CharClass>>(emptyList())
    val classList = _classList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val classes = dungeonsHelperDatabase.charClassDao().getAll()
            _classList.value = classes
        }
    }

    private var _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private var _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    suspend fun addClass(name: String, hitDie: String, description: String, savingThrows: String, primaryAbility: String) {
        val newClass = CharClass(name, hitDie, description, savingThrows, primaryAbility)

        // Insert class on the IO dispatcher to avoid blocking the main thread
        withContext(Dispatchers.IO) {
            dungeonsHelperDatabase.charClassDao().insert(newClass)
        }

        // Emit the updated list to the StateFlow
        val updatedList = _classList.value + newClass
        _classList.emit(updatedList)
    }

    fun getClasses() {
        _loadingState.value = true
        api.getClasses(
            context,
            onSuccess = { fetchedClasses ->
                viewModelScope.launch {
                    val currentClasses = _classList.value
                    val updatedList = currentClasses + fetchedClasses.filterNot { fetchedClass ->
                        currentClasses.any { it.name == fetchedClass.name }
                    }
                    _classList.emit(updatedList)
                    _loadingState.emit(false)
                }
            },
            onFail = {
                viewModelScope.launch {
                    _loadingState.emit(false)
                    _errorState.emit("Failed to fetch character classes.")
                }
            },
            loadingFinished = {
                _loadingState.value = false
            }
        )
    }
}