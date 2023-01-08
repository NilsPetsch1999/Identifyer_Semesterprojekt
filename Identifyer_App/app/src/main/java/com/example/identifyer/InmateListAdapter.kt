package com.example.identifyer

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.identifyer.model.Inmate

class InmateListAdapter(items : List<Inmate> = listOf()) : RecyclerView.Adapter<InmateListAdapter.ItemViewHolder>() {



    var items = items
        set(value){
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener : ((Inmate)->Unit)? = null

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val mItemFirstnameTextView = itemView.findViewById<TextView>(R.id.tv_inmate_firstname)

        init {
            itemView.setOnClickListener {
                itemClickListener?.invoke(items[absoluteAdapterPosition])
                notifyDataSetChanged()
            }
        }

        fun bind (index: Int){
            mItemFirstnameTextView.text= items[index].firstname + " " + items[index].lastname

        }


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val context = parent.context
        val layoutIdForListItem: Int = R.layout.inmate_list_item
        val inflater = LayoutInflater.from(context)
        val view = inflater. inflate(layoutIdForListItem, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Log.d("POSITION: ", position.toString())
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}