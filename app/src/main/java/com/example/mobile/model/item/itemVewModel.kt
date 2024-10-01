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

    private val _itemList = MutableStateFlow<List<Item>>(emptyList())
    val itemList = _itemList.asStateFlow()

    private var _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private var _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val items = dungeonsHelperDatabase.itemsDao().getAllItems()
            _itemList.value = items
        }
    }

    suspend fun addItem(
        name: String,
        type: String,
        charges: String,
        description: String,
        recharge: String
    ) {
        val newItem = Item(name, type, charges, description, recharge)

        viewModelScope.launch(Dispatchers.IO) {
            // Insert item into the database
            dungeonsHelperDatabase.itemsDao().insert(newItem)

            // Fetch and emit the updated list
            val updatedList = dungeonsHelperDatabase.itemsDao().getAllItems()
            _itemList.emit(updatedList)
        }
    }

    // Fetch items from API and update the item list
    fun getItems() {
        _loadingState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items = dungeonsHelperDatabase.itemsDao().getAllItems()
                _itemList.emit(items)
                _loadingState.emit(false)
            } catch (e: Exception) {
                _loadingState.emit(false)
                _errorState.emit("Failed to load items from database.")
            }
        }

        // Optionally fetch from the API
        api.getItems(
            context,
            onSuccess = { fetchedItems ->
                viewModelScope.launch(Dispatchers.IO) {
                    val currentItems = _itemList.value
                    val newItems = fetchedItems.filterNot { fetchedItem ->
                        currentItems.any { it.name == fetchedItem.name }
                    }
                    newItems.forEach { dungeonsHelperDatabase.itemsDao().insert(it) }
                    val updatedList = dungeonsHelperDatabase.itemsDao().getAllItems()
                    _itemList.emit(updatedList)
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
