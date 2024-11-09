import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {
    // Use MutableStateFlow to allow changes in the theme state
    private val _isDarkTheme = MutableStateFlow(false) // or true, depending on default theme
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    fun toggleTheme() {
        // Toggle the theme value
        _isDarkTheme.value = !_isDarkTheme.value
    }
}