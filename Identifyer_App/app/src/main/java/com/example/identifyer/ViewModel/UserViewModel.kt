package com.example.identifyer.ViewModel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.identifyer.database.ApplicationDatabase
import com.example.identifyer.model.User
import com.example.identifyer.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.concurrent.ExecutionException

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository : UserRepository

    val mUserEntries: LiveData<List<User>>


    val mUsername by lazy { MutableLiveData("") }
    val mPassword by lazy { MutableLiveData("") }
    val errorMsg by lazy { MutableLiveData<String?>(null)}

    init {
        val userDao = ApplicationDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
        mUserEntries = userRepository.userEntries
    }

    //find user by id
    fun findUserById(id: Long) : User{

        return userRepository.getUserById(id)
    }

    //get all User
    fun getAllUsers():List<User>{
        return userRepository.getUsers()
    }

    fun insert(user: User) = viewModelScope.launch {
        errorMsg.value = null
        if(user.username.isNullOrEmpty()){
            user.username = "NoUsername"
        }

        if( user.password.isNullOrEmpty()){
            user.password = "NoPassword"
        }

        val username = user.username
        val password = user.password

        if(username.isNullOrEmpty()||password.isNullOrEmpty()){
            errorMsg.value = "Empty error! One or all Attributes are empty."
        }else{
            try {
                withContext(Dispatchers.IO){
                    try {
                        userRepository.insert(user)

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