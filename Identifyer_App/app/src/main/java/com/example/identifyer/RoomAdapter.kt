package com.example.identifyer

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.identifyer.model.Room


class RoomAdapter(items : List<Room> = listOf()) : RecyclerView.Adapter<RoomAdapter.RoomItemViewHolder>() {



    var items = items
        set(value){
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener : ((Room)->Unit)? = null

    inner class RoomItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val mItemRoomName = itemView.findViewById<TextView>(R.id.tv_room)
        private val mItemSecurityLevel = itemView.findViewById<TextView>(R.id.tv_securityLvl)
        private val mItemTract = itemView.findViewById<TextView>(R.id.tv_tract)
        private val mItemMaxCapacity = itemView.findViewById<TextView>(R.id.tv_max_capacity)

        init {
            itemView.setOnClickListener {
                itemClickListener?.invoke(items[absoluteAdapterPosition])
                notifyDataSetChanged()
            }

        }

        fun bind (index: Int){
            mItemRoomName.text =  "Roomname: " + items[index].roomNumber
            mItemSecurityLevel.text = "Security Level: " +items[index].securityLevel.toString()
            mItemTract.text = "Tract: " + items[index].tract.toString()
            mItemMaxCapacity.text = "Max Capacity: " + items[index].maxCapacity.toString()


        }


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomItemViewHolder {
        val context = parent.context
        val layoutIdForListItem: Int = R.layout.room_list_item
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layoutIdForListItem, parent, false)
        return RoomItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomItemViewHolder, position: Int) {
        Log.d("POSITION: ", position.toString())
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}