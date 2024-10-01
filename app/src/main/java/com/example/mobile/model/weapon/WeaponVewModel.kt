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

    suspend fun addWeapon(
        name: String,
        damage: String,
        to_hit: Int,
        image_url: String,
        damage_type: String,
        description: String
    ) {
        val newWeapon = Weapon(name, damage, to_hit, image_url, damage_type, description)

        viewModelScope.launch(Dispatchers.IO) {
            // Insert weapon into the database
            dungeonsHelperDatabase.weaponDao().insert(newWeapon)

            // Fetch and emit the updated list
            val updatedList = dungeonsHelperDatabase.weaponDao().getAll()
            _weaponList.emit(updatedList)
        }
    }

    // Fetch items from API and update the item list
    fun getWeapons() {
        _loadingState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weapons = dungeonsHelperDatabase.weaponDao().getAll()
                _weaponList.emit(weapons) // Emit the initial database state
                _loadingState.emit(false)
            } catch (e: Exception) {
                _loadingState.emit(false)
                _errorState.emit("Failed to load weapons from database.")
            }
        }

        // Optionally fetch from the API if you want to update the list dynamically
        api.getWeapons(
            context,
            onSuccess = { fetchedItems ->
                viewModelScope.launch(Dispatchers.IO) {
                    val currentItems = _weaponList.value
                    val newItems = fetchedItems.filterNot { fetchedItem ->
                        currentItems.any { it.name == fetchedItem.name }
                    }
                    // Persist the new items to the database
                    newItems.forEach { dungeonsHelperDatabase.weaponDao().insert(it) }

                    // Fetch the updated list from the database
                    val updatedList = dungeonsHelperDatabase.weaponDao().getAll()
                    _weaponList.emit(updatedList)
                    _loadingState.emit(false)
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
