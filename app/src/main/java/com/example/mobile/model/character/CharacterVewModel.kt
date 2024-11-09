package com.example.mobile.model.character

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.*
import com.example.mobile.data.DungeonsHelperDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val dungeonsHelperDatabase = DungeonsHelperDatabase.getDatabase(context)

    private val _characterList = MutableStateFlow<List<Character>>(emptyList())
    val characterList = _characterList.asStateFlow()

    private var _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private var _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        _loadingState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val characters = dungeonsHelperDatabase.characterDao().getAll()
                _characterList.emit(characters)
                _loadingState.emit(false)
            } catch (e: Exception) {
                _loadingState.emit(false)
                _errorState.emit("Failed to load characters from database.")
            }
        }
    }

    fun addCharacter(
        name: String,
        race: String?,
        characterClasses: List<CharacterClass>,
        baseStats: BaseStats,
        proficiency: CharacterProficiency,
        traits: List<Trait>,
        hp: CharacterHp
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Insert base stats, proficiency, and hp first to generate IDs
                val baseStatsId = dungeonsHelperDatabase.baseStatsDao().insert(baseStats)
                val proficiencyId = dungeonsHelperDatabase.characterProficiencyDao().insert(proficiency)
                val hpId = dungeonsHelperDatabase.characterHpDao().insert(hp)

                val newCharacter = Character(
                    name = name,
                    race = race,
                    baseStatsId = baseStatsId,
                    proficiencyId = proficiencyId,
                    hpId = hpId
                )

                dungeonsHelperDatabase.characterDao().insert(newCharacter)

                // Insert character classes and traits
                characterClasses.forEach { charClass ->
                    dungeonsHelperDatabase.characterClassDao().insert(charClass.copy(characterName = name))
                }
                traits.forEach { trait ->
                    dungeonsHelperDatabase.traitDao().insert(trait.copy(characterName = name))
                }

                val updatedList = characterList.value + newCharacter
                _characterList.emit(updatedList)

            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Failed to add character: ${e.localizedMessage}")
                _errorState.emit("Failed to add character.")
            }
        }
    }

    fun deleteCharacter(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dungeonsHelperDatabase.characterDao().delete(character)
                // Refresh the character list after deletion
                loadCharacters()
            } catch (e: Exception) {
                _errorState.emit("Failed to delete character.")
            }
        }
    }

    fun getCharacterByName(name: String): Flow<CharacterWithDetails?> {
        return dungeonsHelperDatabase.characterWithDetails().getCharacterWithDetails(name)
    }
}
