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

        private val mItemFullNameTextView = itemView.findViewById<TextView>(R.id.tv_inmate_full_name)
        private val mItemGenderTextView = itemView.findViewById<TextView>(R.id.tv_inmate_gender)
        private val mItemDateOfBirthTextView = itemView.findViewById<TextView>(R.id.tv_inmate_date_of_birth)
        private val mItemArrivalDateTextView = itemView.findViewById<TextView>(R.id.tv_inmate_arrival_date)
        private val mItemTimeOfSentenceTextView = itemView.findViewById<TextView>(R.id.tv_inmate_time_of_sentence)
        private val mItemSecurityLvlTextView = itemView.findViewById<TextView>(R.id.tv_inmate_security_lvl)
        private val mItemPhysicalWellnessTextView = itemView.findViewById<TextView>(R.id.tv_inmate_physical_wellness)
        private val mItemMentalWellnessTextView = itemView.findViewById<TextView>(R.id.tv_inmate_mental_wellness)
        private val mItemOffenseListTextView = itemView.findViewById<TextView>(R.id.tv_inmate_offense_list)
        private val mItemOffenseAccomplicesTextView = itemView.findViewById<TextView>(R.id.tv_inmate_offense_Accomplices)
        private val mItemCombatExperienceTextView = itemView.findViewById<TextView>(R.id.tv_inmate_combat_experience)
        private val mItemAdditionalNotesTextView = itemView.findViewById<TextView>(R.id.tv_inmate_additional_notes)
        private val mItemRoomIdTextView = itemView.findViewById<TextView>(R.id.tv_inmate_room_id)


        init {
            itemView.setOnClickListener {
                itemClickListener?.invoke(items[absoluteAdapterPosition])
                notifyDataSetChanged()
            }
        }

        fun bind (index: Int){

            mItemFullNameTextView.text= "Full Name: " + items[index].firstname + " " + items[index].lastname
            mItemGenderTextView.text = "Gender: " + items[index].gender
            mItemDateOfBirthTextView.text = "Date of Birth: " + items[index].dateOfBirth
            mItemArrivalDateTextView.text = "Arrival Date: " + items[index].arrivalDate

            mItemTimeOfSentenceTextView.text = "Time of Sentence: "+ items[index].timeOfSentence
            mItemSecurityLvlTextView.text = "SecurityLevel: "+ items[index].securityLevel
            mItemPhysicalWellnessTextView.text = "Physical Wellness: " + items[index].physicalWellness
            mItemMentalWellnessTextView.text = "Mental Wellness: "+ items[index].mentalWellness
            mItemOffenseListTextView.text = "Offense List: " + items[index].offenseList?.joinToString(";")
            mItemOffenseAccomplicesTextView.text = "Offense List: " + items[index].offenseAccomplices!!.joinToString("-")
            mItemCombatExperienceTextView.text = "Combat Experience: " + items[index].combatExperience
            mItemAdditionalNotesTextView.text = "Notes: \n###" +  items[index].additionalNotes!!.joinToString(" \n### ")
            mItemRoomIdTextView.text = "Room ID: " +items[index].room_id
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