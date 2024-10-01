package com.example.mobile.model.race

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.apiManager.ApiServiceImpl
import com.example.mobile.data.DungeonsHelperDatabase
import com.example.mobile.data.Race
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: ApiServiceImpl // Injected via Hilt
) : ViewModel() {

    private val dungeonsHelperDatabase = DungeonsHelperDatabase.getDatabase(context)

    private val _raceList = MutableStateFlow<List<Race>>(emptyList())
    val raceList = _raceList.asStateFlow()

    private var _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private var _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val races = dungeonsHelperDatabase.raceDao().getAll()
            _raceList.value = races
        }
    }

    suspend fun addRace(
        name: String,
        size: String,
        speed: Int,
        description: String,
        specialAbilities: String
    ) {
        val newRace = Race(name, size, speed, description, specialAbilities)

        viewModelScope.launch(Dispatchers.IO) {
            // Insert race into the database
            dungeonsHelperDatabase.raceDao().insert(newRace)

            // Fetch and emit the updated list
            val updatedList = dungeonsHelperDatabase.raceDao().getAll()
            _raceList.emit(updatedList)
        }
    }

    // Fetch races from API and update the list
    fun getRaces() {
        _loadingState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val races = dungeonsHelperDatabase.raceDao().getAll()
                _raceList.emit(races)
                _loadingState.emit(false)
            } catch (e: Exception) {
                _loadingState.emit(false)
                _errorState.emit("Failed to load races from database.")
            }
        }

        // Optionally fetch from the API
        api.getRaces(
            context,
            onSuccess = { fetchedItems ->
                viewModelScope.launch(Dispatchers.IO) {
                    val currentItems = _raceList.value
                    val newItems = fetchedItems.filterNot { fetchedItem ->
                        currentItems.any { it.name == fetchedItem.name }
                    }
                    newItems.forEach { dungeonsHelperDatabase.raceDao().insert(it) }
                    val updatedList = dungeonsHelperDatabase.raceDao().getAll()
                    _raceList.emit(updatedList)
                    _loadingState.emit(false)
                }
            },
            onFail = {
                viewModelScope.launch {
                    _loadingState.emit(false)
                    _errorState.emit("Failed to fetch races.")
                }
            },
            loadingFinished = {
                _loadingState.value = false
            }
        )
    }
}
