package com.example.cardbe.data

import com.example.cardbe.data.model.BoardModel
import com.example.cardbe.data.model.CardModel
import com.example.cardbe.data.model.CollumnModel
import com.example.cardbe.data.model.UserModel
import retrofit2.Call
import retrofit2.http.*

interface Endpoint {

// ----------------------------- BOARDS METHODS-----------------------------

//    @GET("Boards")
//    fun getPosts() : Call<List<BoardModel>>

    @GET("Boards")
    fun getBoard(
        @Query("id") userId: Int?,
        @Query("_sort") sort: String?,
        @Query("_order") order: String?
    ) : Call<List<BoardModel>>

    @POST("Boards")
    fun postBoard(@Body post: BoardModel) : Call<BoardModel>

    //Send Full Object
    @PUT("Boards/{id}")
    fun putBoard(@Path("id") id:Int, @Body post: BoardModel) : Call<BoardModel>


    @DELETE("Boards/{id}")
    fun deleteBoard(@Path("id") id: Int) : Call<Void>

// ----------------------------- CARD METHODS-----------------------------

//    @GET("Cards")
//    fun getPosts() : Call<List<CardModel>>

    @GET("Cards")
    fun getCards(
        @Query("id") userId: Int?,
        @Query("_sort") sort: String?,
        @Query("_order") order: String?
    ) : Call<List<CardModel>>

    @POST("Cards")
    fun postCard(@Body post: CardModel) : Call<CardModel>

    //Send Full Object
    @PUT("Cards/{id}")
    fun putCard(@Path("id") id:Int, @Body post: CardModel) : Call<CardModel>


    @DELETE("Cards/{id}")
    fun deleteCard(@Path("id") id: Int) : Call<Void>

// ----------------------------- COLLUMN METHODS-----------------------------

//    @GET("Collumns")
//    fun getPosts() : Call<List<CollumnModel>>

    @GET("Collumns")
    fun getCollumns(
        @Query("id") userId: Int?,
        @Query("_sort") sort: String?,
        @Query("_order") order: String?
    ) : Call<List<CollumnModel>>

    @POST("Collumns")
    fun postCollumn(@Body post: CollumnModel) : Call<CollumnModel>

    //Send Full Object
    @PUT("Collumns/{id}")
    fun putCollumn(@Path("id") id:Int, @Body post: CollumnModel) : Call<CollumnModel>


    @DELETE("Collumns/{id}")
    fun deleteCollumn(@Path("id") id: Int) : Call<Void>

// ----------------------------- USER METHODS-----------------------------

//    @GET("Users")
//    fun getPosts() : Call<List<UserModel>>

    @GET("Users")
    fun getUsers(
        @Query("id") userId: Int?,
        @Query("_sort") sort: String?,
        @Query("_order") order: String?
    ) : Call<List<UserModel>>

    @POST("Users")
    fun postUser(@Body post: UserModel) : Call<UserModel>

    //Send Full Object
    @PUT("Users/{id}")
    fun putUser(@Path("id") id:Int, @Body post: UserModel) : Call<UserModel>


    @DELETE("Users/{id}")
    fun deleteUser(@Path("id") id: Int) : Call<Void>

}