package com.example.identifyer.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.identifyer.R
import com.example.identifyer.model.Inmate
import java.text.SimpleDateFormat
import java.util.*

class InmateListAdapter(items : List<Inmate> = listOf()) : RecyclerView.Adapter<InmateListAdapter.ItemViewHolder>() {



    var items = items
        set(value){
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener : ((Inmate)->Unit)? = null

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val mItemFullNameTextView = itemView.findViewById<TextView>(R.id.tv_inmate_full_name)
        private val mItemGenderTextView = itemView.findViewById<TextView>(R.id.tv_inmate_gender)
        private val mItemDateOfBirthTextView = itemView.findViewById<TextView>(R.id.tv_inmate_date_of_birth)
        private val mItemNationalityTextView = itemView.findViewById<TextView>(R.id.tv_inmate_nationality)
        private val mItemSentenceTextView = itemView.findViewById<TextView>(R.id.tv_inmate_sentence)
        private val mItemArrivalDateTextView = itemView.findViewById<TextView>(R.id.tv_inmate_arrival_date)
        //private val mItemTimeOfSentenceTextView = itemView.findViewById<TextView>(R.id.tv_inmate_time_of_sentence)
        private val mItemSecurityLvlTextView = itemView.findViewById<TextView>(R.id.tv_inmate_security_lvl)
        private val mItemPhysicalWellnessTextView = itemView.findViewById<TextView>(R.id.tv_inmate_physical_wellness)
        private val mItemMentalWellnessTextView = itemView.findViewById<TextView>(R.id.tv_inmate_mental_wellness)
        private val mItemOffenseListTextView = itemView.findViewById<TextView>(R.id.tv_inmate_offense_list)
        //private val mItemOffenseAccomplicesTextView = itemView.findViewById<TextView>(R.id.tv_inmate_offense_Accomplices)
        private val mItemCombatExperienceTextView = itemView.findViewById<TextView>(R.id.tv_inmate_combat_experience)
        private val mItemAdditionalNotesTextView = itemView.findViewById<TextView>(R.id.tv_inmate_additional_notes)
        //private val mItemRoomIdTextView = itemView.findViewById<TextView>(R.id.tv_inmate_room_id)


        init {
            itemView.setOnClickListener {
                itemClickListener?.invoke(items[absoluteAdapterPosition])
                notifyDataSetChanged()
            }
        }

        fun bind (index: Int){

            mItemFullNameTextView.text = items[index].firstname + " " + items[index].lastname
            mItemGenderTextView.text = items[index].gender
            mItemDateOfBirthTextView.text = items[index].dateOfBirth.toString()
            mItemArrivalDateTextView.text = items[index].arrivalDate.toString()
            mItemNationalityTextView.text = items[index].nationality
            mItemSentenceTextView.text = items[index].sentence
            //mItemTimeOfSentenceTextView.text = items[index].timeOfSentence.toString()
            mItemSecurityLvlTextView.text = items[index].securityLevel.toString()
            mItemPhysicalWellnessTextView.text = items[index].physicalWellness
            mItemMentalWellnessTextView.text = items[index].mentalWellness
            mItemOffenseListTextView.text = items[index].offenseList?.joinToString("\n")
            //mItemOffenseAccomplicesTextView.text = "Offense List: " + items[index].offenseAccomplices!!.joinToString("-")
            mItemCombatExperienceTextView.text = items[index].combatExperience
            mItemAdditionalNotesTextView.text = "# " + items[index].additionalNotes!!.joinToString(" \n# ")
           // mItemRoomIdTextView.text = items[index].room_id.toString()
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

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }
}