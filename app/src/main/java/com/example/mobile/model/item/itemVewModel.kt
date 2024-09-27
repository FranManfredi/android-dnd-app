package com.example.mobile.model.item

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.apiManager.ApiServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: ApiServiceImpl // Injected via Hilt
) : ViewModel() {

    private var _itemList = MutableStateFlow<List<Items>>(emptyList())
    val itemList = _itemList.asStateFlow()

    private var _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private var _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    // Function to add an item manually
    fun addItem(name: String, type: String, charges: String, description: String, recharge: String) {
        val newItem = Items(name, type, charges, description, recharge)
        val updatedList = _itemList.value + newItem
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
                Log.d("API", "testing onSuccess")
                viewModelScope.launch {
                    _itemList.emit(fetchedItems)
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
