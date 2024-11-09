package com.example.mobile.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "weapon")
data class Weapon(
    @PrimaryKey() val name: String,
    val damage: String,
    val to_hit: Int,
    val image_url: String,
    val damage_type: String,
    val description: String,
)

@Entity(tableName = "spell")
data class Spell(
    @PrimaryKey() val name: String,
    val level: Int,
    val damage: String,
    val damage_type: String,
    val description: String,
    val casting_time: String,
)

@Entity(tableName = "item")
data class Item(
    @PrimaryKey() val name: String,
    val type: String,
    val charges: String,
    val recharge: String,
    val description: String
)

@Entity(tableName = "class")
data class CharClass (
    @PrimaryKey() val name: String,
    val hit_die: String,
    val description: String,
    val saving_throws: String,
    val primary_ability: String
)

@Entity(tableName = "race")
data class Race (
    @PrimaryKey() val name: String,
    val size: String,
    val speed: Int,
    val description: String,
    val special_abilities: String
)

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey val id: String = "default",
    val themeOption: THEME_OPTION = THEME_OPTION.DEFAULT,  // Foreign key reference to Character
)

enum class THEME_OPTION {
    DEFAULT,
    DARK,
    LIGHT
}

@Entity(tableName = "character")
data class Character(
    @PrimaryKey val name: String,
    val race: String?,
    val baseStatsId: Long?,
    val proficiencyId: Long?,
    val hpId: Long?
)

@Entity(tableName = "base_stats")
data class BaseStats(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val str: Int,
    val dex: Int,
    val con: Int,
    val intelligence: Int,  // Renamed to avoid conflict with `int` keyword
    val wis: Int,
    val cha: Int
)

@Entity(tableName = "character_hp")
data class CharacterHp(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val totalHitPoints: Int,
    val currentHitPoints: Int = totalHitPoints
)

@Entity(tableName = "character_proficiency")
data class CharacterProficiency(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    // Saving throws
    val strSt: Boolean,
    val dexSt: Boolean,
    val conSt: Boolean,
    val intSt: Boolean,
    val wisSt: Boolean,
    val chaSt: Boolean,
    // Skills
    val acrobatics: Boolean,
    val animalHandling: Boolean,
    val arcana: Boolean,
    val athletics: Boolean,
    val deception: Boolean,
    val history: Boolean,
    val insight: Boolean,
    val intimidation: Boolean,
    val investigation: Boolean,
    val medicine: Boolean,
    val nature: Boolean,
    val perception: Boolean,
    val performance: Boolean,
    val persuasion: Boolean,
    val religion: Boolean,
    val sleightOfHand: Boolean,
    val stealth: Boolean,
    val survival: Boolean
)

@Entity(tableName = "character_class")
data class CharacterClass(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val characterName: String,  // Foreign key reference to Character
    val name: String,
    val level: Int
)

@Entity(tableName = "trait")
data class Trait(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val characterName: String,  // Foreign key reference to Character
    val name: String,
    val description: String?
)

// Define relations using Room's @Relation annotation
data class CharacterWithDetails(
    @Embedded val character: Character,

    @Relation(
        parentColumn = "baseStatsId",
        entityColumn = "id"
    )
    val baseStats: BaseStats,

    @Relation(
        parentColumn = "proficiencyId",
        entityColumn = "id"
    )
    val proficiency: CharacterProficiency,

    @Relation(
        parentColumn = "hpId",
        entityColumn = "id"
    )
    val hp: CharacterHp,

    @Relation(
        parentColumn = "name",
        entityColumn = "characterName"
    )
    val characterClasses: List<CharacterClass>,

    @Relation(
        parentColumn = "name",
        entityColumn = "characterName"
    )
    val traits: List<Trait>
)
