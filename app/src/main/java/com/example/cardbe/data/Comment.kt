package com.example.cardbe.data

import com.google.gson.annotations.SerializedName

data class Comment (
    var postId : Int,

    var id : String,

    var name : String,

    var email : String,
    @SerializedName("body")
    var text : String
)