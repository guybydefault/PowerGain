package ru.guybydefault.powergain.fragments

import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ExerciseTypeViewHolder(
    val cardView: CardView,
    val exerciseNameTextView: TextView,
    val maxWeightTextView: TextView,
    val oneRepMaxTextView: TextView,
    val chartBtn: ImageButton,
    val overViewBtn: ImageButton,
    val addExerciseBtn: ImageButton
) : RecyclerView.ViewHolder(cardView)