package ua.kulya.speechwa.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.kulya.speechwa.room.EgyptRep
import ua.kulya.speechwa.room.EgyptUserData
import ua.kulya.speechwa.room.EgyptUserDb

class EgyptUserVM(app: Application) : AndroidViewModel(app) {
    val allInfo: LiveData<List<EgyptUserData>>
    val repo: EgyptRep

    init {
        val dao = EgyptUserDb.makeDb(app).getUserDataDAo()
        repo = EgyptRep(dao)
        allInfo = repo.allInfo
    }

    fun addUserDAta(egyptUserData: EgyptUserData) = viewModelScope.launch(Dispatchers.IO) {
        repo.loadIntoDb(egyptUserData)
    }
}

//class FactoryForVM(private val app: Application, private val egyptRep: EgyptRep) :
//    ViewModelProvider.AndroidViewModelFactory(app) {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(EgyptUserVM::class.java)) {
//            return EgyptUserVM(app, egyptRep) as T
//        }
//        throw IllegalArgumentException("VM Factory Error")
//    }
//}