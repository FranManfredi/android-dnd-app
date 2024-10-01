package com.example.mobile.model.item

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.apiManager.ApiServiceImpl
import com.example.mobile.data.DungeonsHelperDatabase
import com.example.mobile.data.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: ApiServiceImpl // Injected via Hilt
) : ViewModel() {

    private val dungeonsHelperDatabase = DungeonsHelperDatabase.getDatabase(context)

    // StateFlow for the list of items
    private val _itemList = MutableStateFlow<List<Item>>(emptyList())
    val itemList = _itemList.asStateFlow()

    init {
        // Fetch data asynchronously in a coroutine
        viewModelScope.launch(Dispatchers.IO) {
            val items = dungeonsHelperDatabase.itemsDao().getAllItems()
            _itemList.value = items
        }
    }

    private var _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private var _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    suspend fun addItem(name: String, type: String, charges: String, description: String, recharge: String) {
        val newItem = Item(name, type, charges, description, recharge)
        val updatedList = _itemList.value + newItem
        dungeonsHelperDatabase.itemsDao().insert(item = newItem)
        viewModelScope.launch {
            _itemList.emit(updatedList)
        }
    }

    // Fetch items from API and update the item list
    fun getItems() {
        _loadingState.value = true // Set loading state to true
        api.getItems(
            context,
            onSuccess = { fetchedItems ->
                viewModelScope.launch {
                    val currentItems = _itemList.value
                    val updatedList = currentItems + fetchedItems.filterNot { fetchedItem ->
                        currentItems.any { it.name == fetchedItem.name } // Ensure no duplicates
                    }
                    _itemList.emit(updatedList)
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
