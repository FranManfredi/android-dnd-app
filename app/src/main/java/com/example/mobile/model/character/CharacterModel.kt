package com.example.mobile.model.character

data class Character(
    val name: String,
    val race: String?,
    val characterClasses: Array<CharacterClass>?,
    val baseStats: BaseStats?,
    val proficiency: CharacterProficiency?,
    val traits: Array<Trait>?,
    val hp: CharacterHp?,
)

data class BaseStats(
    val str: Int,
    var dex: Int,
    val con: Int,
    val int: Int,
    val wis: Int,
    val cha: Int,
)

data class CharacterHp (
    val totalHitPoints: Int,
    val currentHitPoints: Int = totalHitPoints,
)

data class CharacterProficiency(
    //saving throws
    val strSt: Boolean,
    var dexSt: Boolean,
    val conSt: Boolean,
    val intSt: Boolean,
    val wisSt: Boolean,
    val chaSt: Boolean,
    //skills
    var acrobatics: Boolean,
    var animalHandling: Boolean,
    var arcana: Boolean,
    var athletics: Boolean,
    var deception: Boolean,
    var history: Boolean,
    var insight: Boolean,
    var intimidation: Boolean,
    var investigation: Boolean,
    var medicine: Boolean,
    var nature: Boolean,
    var perception: Boolean,
    var performance: Boolean,
    var persuasion: Boolean,
    var religion: Boolean,
    var sleightOfHand: Boolean,
    var stealth: Boolean,
    var survival: Boolean,
)

data class CharacterClass(
    val name: String,
    val level: Int,
)

data class Trait(
    val name: String,
    val description: String?,
)