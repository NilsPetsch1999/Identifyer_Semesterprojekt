package com.example.identifyer

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InmateListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inmatelist)

        val inmateRecyclerView = findViewById<RecyclerView>(R.id.rv_inmate_list)
        val layoutManager = LinearLayoutManager(this)

        inmateRecyclerView.layoutManager = layoutManager
        inmateRecyclerView.setHasFixedSize(true)
        //inmateRecyclerView.adapter = ada

    }
}