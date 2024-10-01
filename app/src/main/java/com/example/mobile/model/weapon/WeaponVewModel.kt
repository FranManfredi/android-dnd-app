package com.example.mobile.model.weapon

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.apiManager.ApiServiceImpl
import com.example.mobile.data.DungeonsHelperDatabase
import com.example.mobile.data.Weapon
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeaponViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: ApiServiceImpl // Injected via Hilt
) : ViewModel() {

    private val dungeonsHelperDatabase = DungeonsHelperDatabase.getDatabase(context)

    private val _weaponList = MutableStateFlow<List<Weapon>>(emptyList())
    val weaponsList = _weaponList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val weapons = dungeonsHelperDatabase.weaponDao().getAll()
            _weaponList.value = weapons
        }
    }

    private var _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private var _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    suspend fun addWeapon(name: String, damage: String, to_hit: Int, image_url: String, damage_type: String, description: String) {
        val newWeapon = Weapon(name, damage, to_hit, image_url, damage_type, description)

        // Insert weapon on the IO dispatcher to avoid blocking the main thread
        withContext(Dispatchers.IO) {
            dungeonsHelperDatabase.weaponDao().insert(newWeapon)
        }

        // Emit the updated list to the StateFlow
        val updatedList = _weaponList.value + newWeapon
        _weaponList.emit(updatedList)
    }

    // Fetch items from API and update the item list
    fun getWeapons() {
        _loadingState.value = true // Set loading state to true
        api.getWeapons(
            context,
            onSuccess = { fetchedItems ->
                viewModelScope.launch {
                    val currentItems = _weaponList.value
                    val updatedList = currentItems + fetchedItems.filterNot { fetchedItem ->
                        currentItems.any { it.name == fetchedItem.name } // Ensure no duplicates
                    }
                    _weaponList.emit(updatedList)
                    _loadingState.emit(false) // Stop loading
                }
            },
            onFail = {
                viewModelScope.launch {
                    _loadingState.emit(false)
                    _errorState.emit("Failed to fetch items.")
                }
            },
            loadingFinished = {
                _loadingState.value = false
            }
        )
    }
}
