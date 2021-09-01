package ru.guybydefault.powergain.fragments

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ExerciseTypeViewHolder(
    val cardView: CardView,
    val date: TextView,
    val trainingDesc: TextView
) : RecyclerView.ViewHolder(cardView) {

}