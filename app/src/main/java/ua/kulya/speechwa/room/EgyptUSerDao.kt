package ua.kulya.speechwa.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EgyptUSerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoDb(user: EgyptUserData)

    @Query("SELECT * FROM userData LIMIT 1")
    fun getAllUserInfo(): LiveData<List<EgyptUserData>>
}