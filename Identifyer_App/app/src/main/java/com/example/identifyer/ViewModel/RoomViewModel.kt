package com.example.identifyer.ViewModel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.*
import com.example.identifyer.database.ApplicationDatabase
import com.example.identifyer.model.Inmate
import com.example.identifyer.model.Room
import com.example.identifyer.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.concurrent.ExecutionException

class RoomViewModel(application: Application)  : AndroidViewModel(application) {

    private val roomRepository :RoomRepository


    val mRoomEntries: LiveData<List<Room>>
    var scannedRoom = Room()

    val mSecurityLevel by lazy { MutableLiveData(0) }
    val mInmateList by lazy {MutableLiveData<String?>(null)}

    val errorMsg by lazy {MutableLiveData<String?>(null)}

    init {
        val roomDao = ApplicationDatabase.getDatabase(application).roomDao()
        roomRepository = RoomRepository(roomDao)
        mRoomEntries = roomDao.rooms

    }
    fun getRoomData(s: String): LiveData<List<Room>> {
        return Transformations.map(mRoomEntries) { items ->
            items.filter {
                it.id.toString().contains(s)

            }

        }
    }

    fun findRoomById (id : Long):Room{
        return roomRepository.getRoomById(id)
    }

    fun getAllRoomsByRoomId(id : Long): List<Room>{
        return roomRepository.getRoomsByRoomId(id)
    }
    fun getAllRooms():List<Room>{
        return roomRepository.getAllRooms()
    }

    fun findRoomByName(roomName : String): Room{
        return roomRepository.getRoomByName(roomName)
    }

    fun insert(newRoom :Room)= viewModelScope.launch{
        errorMsg.value= null

        if(newRoom.roomNumber.isNullOrEmpty()){
            newRoom.roomNumber ="no Room Name"
        }

        if(newRoom.inmateList.isNullOrEmpty()){
            newRoom.inmateList = emptyList()
        }
        if(newRoom.securityLevel==0||newRoom.securityLevel==null){
            newRoom.securityLevel = 99
        }

        val roomName = newRoom.roomNumber
        val inmateList = newRoom.inmateList
        val securityLevel = newRoom.securityLevel

        if(roomName.isNullOrEmpty()){
            errorMsg.value = "Empty error! One or all Attributes are empty."
        }else{
            try {
                withContext(Dispatchers.IO){
                    try {
                        roomRepository.insert(newRoom)
                        Log.d("Success", "Insert Success" + newRoom.roomNumber)
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