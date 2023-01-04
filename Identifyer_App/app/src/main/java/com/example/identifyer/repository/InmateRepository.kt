package com.example.identifyer.repository

import androidx.lifecycle.LiveData
import com.example.identifyer.dao.InmateDao
import com.example.identifyer.model.Inmate


class InmateRepository(private val inmateDao: InmateDao) {

    val inmateEntries: LiveData<List<Inmate>> = inmateDao.inmates

    suspend fun insert (inmate: Inmate){
        inmateDao.insert(inmate)
    }

    suspend fun delete(inmate: Inmate){
        inmateDao.delete(inmate)
    }

    suspend fun update(inmate: Inmate){
        inmateDao.update(inmate)
    }

    suspend fun getAllInmate(): List<Inmate>{
        return inmateDao.getAllInmates()
    }

    suspend fun getInmateById (inmate: Inmate){
        inmateDao.findInmateById(id = inmate.id ?: 1)
    }
}