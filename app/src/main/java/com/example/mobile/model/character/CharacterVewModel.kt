package com.example.mobile.model.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor() : ViewModel() {

    private var _characterList = MutableStateFlow(listOf<Character>())
    val characterList = _characterList.asStateFlow()

    fun addCharacter( name: String, level: Int, race: String, charClass: String, background: String, str: Int, dex: Int, con: Int, int: Int, wis: Int, cha: Int, totalHitPoints: Int, currentHitPoints: Int) {
        val character = Character(name, level, race, charClass, background, str, dex, con, int, wis, cha, totalHitPoints, currentHitPoints)
        val newList = _characterList.value + character
        viewModelScope.launch {
            _characterList.emit(newList)
        }
    }
}