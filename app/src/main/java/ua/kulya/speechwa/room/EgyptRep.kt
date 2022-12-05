package ua.kulya.speechwa.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class EgyptRep(private val dao: EgyptUSerDao) {
    val allInfo: LiveData<List<EgyptUserData>> = dao.getAllUserInfo()

    @WorkerThread
    suspend fun loadIntoDb(user: EgyptUserData) {
        dao.insertIntoDb(user)
    }
}