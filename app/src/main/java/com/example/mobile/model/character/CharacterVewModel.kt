package com.example.mobile.model.character

import android.content.Context
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
class CharacterViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val api: ApiServiceImpl = ApiServiceImpl()
    private var _characterList = MutableStateFlow(listOf<Character>())
    val characterList = _characterList.asStateFlow()

    fun addCharacter( name: String,
                      race: String,
                      characterClasses: Array<CharacterClass>,
                      baseStats: BaseStats,
                      proficiency: CharacterProficiency,
                      background: String,
                      traits: Array<Trait>) {

        val hp = calculateCharHp(characterClasses, baseStats.con)

        val character = Character(name, race, characterClasses, baseStats, proficiency, background, traits, hp)
        val newList = _characterList.value + character
        viewModelScope.launch {
            _characterList.emit(newList)
        }
    }

    private fun calculateCharHp(
        characterClasses: Array<CharacterClass>,
        constitution: Int
    ): CharacterHp {

        var total: Int = (constitution - 10)/2

        for (c in characterClasses) {
            api.getHitDie(
                c.name,
                context = context,
                onSuccess = { total += it * c.level },
                loadingFinished = { },
                onFail = { total += 1 * c.level }
            )
        }

        return CharacterHp(total)
    }
}