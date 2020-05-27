package com.example.cardbe.data.model

data class CardModel(

    val card_id : Int?,

    val title : String,

    val comments : String?,

    val attachments : String?,

    val labels : String?,

    val dueDate : String?,

    val description : String?,

    val checklist : String?,

    val collumn_id : Int
)