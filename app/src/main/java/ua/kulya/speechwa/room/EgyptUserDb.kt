package ua.kulya.speechwa.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EgyptUserData::class], version = 1)
abstract class EgyptUserDb : RoomDatabase() {
    abstract fun getUserDataDAo(): EgyptUSerDao

    companion object {

        @Volatile
        private var dbInstance: EgyptUserDb? = null

        fun makeDb(ctx: Context): EgyptUserDb {
            synchronized(this) {
                var mInstance = dbInstance
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(
                        ctx,
                        EgyptUserDb::class.java,
                        "egyptDb"
                    ).allowMainThreadQueries().build()
                    dbInstance = mInstance
                }
                return mInstance
            }
        }
    }
}