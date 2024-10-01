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
import javax.inject.Inject

@HiltViewModel
class CharClassViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: ApiServiceImpl // Injected via Hilt
) : ViewModel() {

    private val dungeonsHelperDatabase = DungeonsHelperDatabase.getDatabase(context)

    private val _classList = MutableStateFlow<List<CharClass>>(emptyList())
    val classList = _classList.asStateFlow()

    private var _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private var _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val charClasses = dungeonsHelperDatabase.charClassDao().getAll()
            _classList.value = charClasses
        }
    }

    suspend fun addClass(
        name: String,
        hitDie: String,
        description: String,
        savingThrows: String,
        primaryAbility: String
    ) {
        val newClass = CharClass(name, hitDie, description, savingThrows, primaryAbility)

        viewModelScope.launch(Dispatchers.IO) {
            // Insert class into the database
            dungeonsHelperDatabase.charClassDao().insert(newClass)

            // Fetch and emit the updated list
            val updatedList = dungeonsHelperDatabase.charClassDao().getAll()
            _classList.emit(updatedList)
        }
    }

    // Fetch classes from API and update the list
    fun getClasses() {
        _loadingState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val charClasses = dungeonsHelperDatabase.charClassDao().getAll()
                _classList.emit(charClasses)
                _loadingState.emit(false)
            } catch (e: Exception) {
                _loadingState.emit(false)
                _errorState.emit("Failed to load classes from database.")
            }
        }

        // Optionally fetch from the API
        api.getClasses(
            context,
            onSuccess = { fetchedItems ->
                viewModelScope.launch(Dispatchers.IO) {
                    val currentItems = _classList.value
                    val newItems = fetchedItems.filterNot { fetchedItem ->
                        currentItems.any { it.name == fetchedItem.name }
                    }
                    newItems.forEach { dungeonsHelperDatabase.charClassDao().insert(it) }
                    val updatedList = dungeonsHelperDatabase.charClassDao().getAll()
                    _classList.emit(updatedList)
                    _loadingState.emit(false)
                }
            },
            onFail = {
                viewModelScope.launch {
                    _loadingState.emit(false)
                    _errorState.emit("Failed to fetch classes.")
                }
            },
            loadingFinished = {
                _loadingState.value = false
            }
        )
    }
}
