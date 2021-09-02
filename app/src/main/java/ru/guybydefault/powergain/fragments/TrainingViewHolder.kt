package ru.guybydefault.powergain.fragments

import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class TrainingViewHolder(
    val cardView: CardView,
    val date: TextView,
    val trainingDesc: TextView,
    val editBtn: ImageButton
) : RecyclerView.ViewHolder(cardView) {

}