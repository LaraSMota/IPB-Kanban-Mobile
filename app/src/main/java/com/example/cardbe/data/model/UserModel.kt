package com.example.cardbe.data.model

data class UserModel(

    val usersId : Int?,

    val firstName : String,

    val lastName : String,

    val email : String,

    val nickname : String,

    val password : String,

    val picture : String?,

    val beNotified : String?
)