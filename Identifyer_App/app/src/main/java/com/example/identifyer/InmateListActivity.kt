package com.example.identifyer

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.identifyer.ViewModel.InmateViewModel
import com.example.identifyer.model.Inmate
import com.example.identifyer.model.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InmateListActivity : AppCompatActivity() {
    companion object {
        const val LIST_KEY = "LIST_KEY"
    }
    lateinit var inmateViewModel: InmateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inmatelist)
        inmateViewModel = ViewModelProvider(this).get(InmateViewModel::class.java)
        var adapter: InmateListAdapter = InmateListAdapter()
        val inmateRecyclerView = findViewById<RecyclerView>(R.id.rv_inmate_list)
        val layoutManager = LinearLayoutManager(this)



        inmateRecyclerView.layoutManager = layoutManager
        inmateRecyclerView.setHasFixedSize(true)
        inmateRecyclerView.adapter = adapter





        Log.d("DFASDFASDF",intent.getStringExtra(LIST_KEY)!! )



      //  val scannedRoom = ((intent.getSerializableExtra(ROOM_KEY)as Room).id).toString()

        inmateViewModel.getFilteredList(intent.getStringExtra(LIST_KEY)!!).observe(this){ entries->
            adapter.items = entries
        }

    }
}