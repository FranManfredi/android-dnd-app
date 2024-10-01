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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RaceViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: ApiServiceImpl // Injected via Hilt
) : ViewModel() {

    private val dungeonsHelperDatabase = DungeonsHelperDatabase.getDatabase(context)

    // StateFlow for the list of races
    private val _raceList = MutableStateFlow<List<Race>>(emptyList())
    val raceList = _raceList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val races = dungeonsHelperDatabase.raceDao().getAll()
            _raceList.value = races
        }
    }

    private var _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private var _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    suspend fun addRace(name: String, size: String, speed: Int, description: String, specialAbilities: String) {
        val newRace = Race(name, size, speed, description, specialAbilities)

        // Insert race on the IO dispatcher to avoid blocking the main thread
        withContext(Dispatchers.IO) {
            dungeonsHelperDatabase.raceDao().insert(newRace)
        }

        // Emit the updated list to the StateFlow
        val updatedList = _raceList.value + newRace
        _raceList.emit(updatedList)
    }

    // Fetch races from API and update the race list
    fun getRaces() {
        _loadingState.value = true
        api.getRaces(
            context,
            onSuccess = { fetchedRaces ->
                viewModelScope.launch {
                    val currentRaces = _raceList.value
                    val updatedList = currentRaces + fetchedRaces.filterNot { fetchedRace ->
                        currentRaces.any { it.name == fetchedRace.name }
                    }
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
