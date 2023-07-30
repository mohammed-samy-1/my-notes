package com.devmo.mynotes.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import com.devmo.mynotes.ui.theme.*

data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val id: Int? = null
) {
    companion object {
        val colors: List<Color> = listOf(
            RedOrange,
            LightGreen,
            Violet,
            BabyBlue,
            RedPink
        )
    }
}

class InvalidNoteException(message:String):Exception(message)
