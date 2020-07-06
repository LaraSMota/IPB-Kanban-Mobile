package com.example.cardbe.data

import android.database.Observable
import com.example.cardbe.data.model.BoardModel
import com.example.cardbe.data.model.CardModel
import com.example.cardbe.data.model.CollumnModel
import com.example.cardbe.data.model.UserModel
import com.example.cardbe.recyclerView.CardItem
import com.example.cardbe.recyclerView.CollumnItem
import com.example.cardbe.recyclerView.HomeItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface Endpoint {

// ----------------------------- BOARDS METHODS-----------------------------

    @GET("Boards")
    fun getBoardToRecyclerView() : Call<List<HomeItem>>

    @GET("Boards/{id}")
    fun getBoard(@Path("id") boardId : Int) : Call<BoardModel>

    @POST("Boards")
    fun postBoard(@Body post: BoardModel) : Call<BoardModel>

    //Send Full Object
    @PUT("Boards/{id}")
    fun putBoard(@Path("id") id:Int, @Body post: BoardModel) : Call<BoardModel>

    @DELETE("Boards/{id}")
    fun deleteBoard(@Path("id") id: Int) : Call<Void>

// ----------------------------- CARD METHODS-----------------------------
    @GET("Cards")
    fun getCardToRecyclerView() : Call<List<CardItem>>

    @GET("Cards")
    fun getCards() : Call<List<CardModel>>

    @GET("Cards/{id}")
    fun getCard(@Path("id") boardId : Int) : Call<CardModel>

    @POST("Cards")
    fun postCard(@Body post: CardModel) : Call<CardModel>

    //Send Full Object
    @PUT("Cards/{id}")
    fun putCard(@Path("id") id:Int, @Body post: CardModel) : Call<CardModel>

    @DELETE("Cards/{id}")
    fun deleteCard(@Path("id") id: Int) : Call<Void>

// ----------------------------- COLLUMN METHODS-----------------------------
    @GET("Collumns")
    fun getCollumnToRecyclerView() : Call<List<CollumnItem>>

    @GET("Collumns/{id}")
    fun getCollumn(@Path("id") boardId : Int) : Call<CollumnModel>

    @GET("Collumns")
    fun getCollums() : Call<List<CollumnModel>>

    @POST("Collumns")
    fun postCollumn(@Body post: CollumnModel) : Call<CollumnModel>

    //Send Full Object
    @PUT("Collumns/{id}")
    fun putCollumn(@Path("id") id:Int, @Body post: CollumnModel) : Call<CollumnModel>

    @DELETE("Collumns/{id}")
    fun deleteCollumn(@Path("id") id: Int) : Call<Void>

// ----------------------------- USER METHODS-----------------------------

    @GET("Users/{id}")
    fun getUsers(@Path("id") id:Int) : Call<UserModel>

    @GET("Users")
    fun getUsers() : Call<List<UserModel>>

    @POST("Users")
    fun postUser(@Body post: UserModel) : Call<UserModel>

    //Send Full Object
    @PUT("Users/{id}")
    fun putUser(@Path("id") id:Int, @Body post: UserModel) : Call<UserModel>

    @DELETE("Users/{id}")
    fun deleteUser(@Path("id") id: Int) : Call<Void>

    @Multipart
    @POST("user/")
    fun updateProfile(
        @Part("userId") id: RequestBody,
        @Part("firstName") firstName: RequestBody?,
        @Part("lastName") lastName: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("nickname") nickname: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("beNotified") beNotified: RequestBody?,
        @Part picture: MultipartBody.Part?
    ): Call<RequestBody>
}