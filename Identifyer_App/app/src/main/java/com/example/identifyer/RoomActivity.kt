package com.example.identifyer

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.identifyer.ViewModel.InmateViewModel

import com.example.identifyer.ViewModel.RoomViewModel
import com.example.identifyer.model.Room
import kotlinx.coroutines.*

class RoomActivity : AppCompatActivity(){

    companion object {
        const val ROOM_KEY = "ROOM_KEY"
    }


    lateinit var scannedRoomViewModel: RoomViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanned_room)
        scannedRoomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        var roomAdapter : RoomAdapter = RoomAdapter()
        val roomRecyclerView = findViewById<RecyclerView>(R.id.rv_scannedRoom)
        val layoutManger = LinearLayoutManager(this)

        roomRecyclerView.layoutManager = layoutManger
        roomRecyclerView.setHasFixedSize(true)
        roomRecyclerView.adapter = roomAdapter

        val btnInmateList: Button = findViewById<Button>(R.id.btn_inmateList)
        val scannedRoom = intent.getStringExtra(ROOM_KEY).toString()

        scannedRoomViewModel.getRoomData(scannedRoom).observe(this){ entries ->
            roomAdapter.items = entries
        }

        btnInmateList.setOnClickListener {
            val intentList = Intent(this, InmateListActivity::class.java)
            intentList.putExtra(InmateListActivity.LIST_KEY, scannedRoom)
            startActivity(intentList)
        }

    }


}