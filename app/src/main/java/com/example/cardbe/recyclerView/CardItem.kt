package com.example.cardbe.recyclerView

data class CardItem (

    val cardId : Int?,

    val title : String,

    val comments : String?,

    val attachments : String?,

    val labels : String?,

    val dueDate : String?,

    val description : String?,

    val checklist : String?,

    val collumnId : Int
)