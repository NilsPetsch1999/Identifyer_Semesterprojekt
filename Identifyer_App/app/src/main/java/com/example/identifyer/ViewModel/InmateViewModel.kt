package com.example.identifyer.ViewModel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.identifyer.database.ApplicationDatabase
import com.example.identifyer.model.Inmate
import com.example.identifyer.model.User
import com.example.identifyer.repository.InmateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.concurrent.ExecutionException

class InmateViewModel(application: Application) : AndroidViewModel(application) {

    private val inmateRepository : InmateRepository

    val mInmateEntries : LiveData<List<Inmate>>

    val mFirstname by lazy { MutableLiveData("") }
    val mLastname by lazy { MutableLiveData("") }
    val errorMsg by lazy { MutableLiveData<String?>(null) }

    init{
        val inmateDao = ApplicationDatabase.getDatabase(application).inmateDao()
        inmateRepository = InmateRepository(inmateDao)
        mInmateEntries = inmateRepository.inmateEntries
        }

    //find user by id
    fun findInmateById(id: Long) : Inmate {

        return inmateRepository.getInmateById(id)
    }

    fun getAllInmates(): List<Inmate>{
        return inmateRepository.getAllInmates()
    }

    fun getAllInmatesByRoomId(id : Long): List<Inmate>{
        return inmateRepository.getInmatesByRoomId(id)
    }

    fun insert(inmate: Inmate) = viewModelScope.launch {
        errorMsg.value = null
        if(inmate.firstname.isNullOrEmpty()){
            inmate.firstname = "NoFirstname"
        }

        if( inmate.lastname.isNullOrEmpty()){
            inmate.lastname = "NoLastname"
        }

        val firstname = inmate.firstname
        val lastname = inmate.lastname

        if(firstname.isNullOrEmpty()||lastname.isNullOrEmpty()){
            errorMsg.value = "Empty error! One or all Attributes are empty."
        }else{
            try {
                withContext(Dispatchers.IO){
                    try {
                        inmateRepository.insert(inmate)

                    }catch (ex : ExecutionException){
                        Log.d("Error", "an error was thrown")
                    }
                }
            }catch (ex : SQLiteConstraintException){
                errorMsg.value = "This entry already exists!"

            }catch (ex: Exception){
                errorMsg.value = "An error occured. Please try again later!"
            }
        }
    }
}