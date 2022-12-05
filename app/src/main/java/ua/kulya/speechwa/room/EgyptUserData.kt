package ua.kulya.speechwa.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userData")
data class EgyptUserData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val link: String? = null
)
