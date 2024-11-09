package com.example.mobile.model.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.DungeonsHelperDatabase
import com.example.mobile.data.Settings
import com.example.mobile.data.THEME_OPTION
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val database: DungeonsHelperDatabase by lazy {
        DungeonsHelperDatabase.getDatabase(context)
    }

    // Observe the theme option directly from the database as a flow
    private val _themeOption = MutableStateFlow(THEME_OPTION.DEFAULT)
    val themeOption: StateFlow<THEME_OPTION> = _themeOption

    init {
        // Load theme from the database as a flow to detect real-time changes
        viewModelScope.launch {
            database.settingsDao().getSetting().collect { setting ->
                _themeOption.value = setting?.themeOption ?: THEME_OPTION.DEFAULT
            }
        }
    }

    fun setThemeOption(option: THEME_OPTION) {
        viewModelScope.launch {
            val setting = Settings(themeOption = option)
            database.settingsDao().insertSetting(setting)
            // Update the flow to reflect the immediate change
            _themeOption.value = option
        }
    }
}