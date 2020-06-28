package com.example.cardbe.recyclerView

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.cardbe.Board
import com.example.cardbe.CreateColumn
import com.example.cardbe.R
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.model.BoardModel
import com.example.cardbe.data.model.CardModel
import com.example.cardbe.data.model.CollumnModel
import com.example.cardbe.ui.home.Home
import com.example.recyclerviewexample.OnCollumnItemListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DialogDeleteCollumnDealer : AppCompatActivity() {
    var collumnId = 0
    var boardId = 0
    var from = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collumnId = intent.getStringExtra("CollumnId")!!.toInt()
        boardId = intent.getStringExtra("BoardId")!!.toInt()
        from = intent.getStringExtra("From")!!.toString()

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to Delete?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                if(from == "Collumn"){
                    deleteCollumn()
                }
                if(from == "Board"){
                    deleteBoard()
                }
            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
                onBackPressed()
            }
        val alert = builder.create()
        alert.show()
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(from == "Collumn"){
            val myIntent = Intent(applicationContext, Board::class.java)
                .putExtra("BoardId", boardId.toString())
            myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            ContextCompat.startActivity(applicationContext, myIntent, null)
        }
        if(from == "Board"){
            val myIntent = Intent(applicationContext, Home::class.java)
            myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            ContextCompat.startActivity(applicationContext, myIntent, null)
        }

    }

    fun deleteBoard(){
        //SELECIONA COLUNAS
        val callback = NetworkUtils.request().getCollums()
        callback.enqueue(object : Callback<List<CollumnModel>> {
            override fun onFailure(call: Call<List<CollumnModel>>, t: Throwable) {
                Log.d("getDataBoardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<List<CollumnModel>>, response: Response<List<CollumnModel>>) {
                if (!response.isSuccessful){
                    Log.d("getDataBoardCode", "Code: " + response.code())
                    return
                }
                //ACHA AS COLUNAS QUE PERTENCEM A ESSE BOARD
                response.body()!!.forEach {
                    if(it.boardId == boardId){
                        val currentCollumn = it.collumnId!!.toInt()

                        //SELECIONA OS CARDS DA COLUNA ATUAL
                        val callback2 = NetworkUtils.request().getCards()
                        callback2.enqueue(object : Callback<List<CardModel>> {
                            override fun onFailure(call: Call<List<CardModel>>, t: Throwable) {
                                Log.d("getDataBoardFailuere", t.message.toString())
                            }

                            override fun onResponse(call: Call<List<CardModel>>, response: Response<List<CardModel>>) {
                                if (!response.isSuccessful){
                                    Log.d("getDataBoardCode", "Code: " + response.code())
                                    return
                                }

                                //IDENTIFICA OS CARDS A SEREM DELETADOS
                                response.body()!!.forEach {
                                    if(currentCollumn == it.collumnId!!.toInt()){
                                        val callback3 = NetworkUtils.request().deleteCard(it.cardId!!.toInt())
                                        callback3.enqueue(object : Callback<Void> {
                                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                                Log.d("deleteDataBoardFailuere", t.message.toString())
                                            }
                                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                                Log.d("deleteDataBoardCode", "Code: " + response.code())
                                            }
                                        })
                                    }
                                }
                                val callback4 = NetworkUtils.request().deleteCollumn(currentCollumn)
                                callback4.enqueue(object : Callback<Void> {
                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        Log.d("deleteDataBoardFailuere", t.message.toString())
                                    }
                                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                        Log.d("deleteDataBoardCode", "Code: " + response.code())
                                    }
                                })
                                //------------------DELETAR O PROPRIO BOARD--------------------
                                val callback5 = NetworkUtils.request().deleteBoard(boardId)
                                callback5.enqueue(object : Callback<Void> {
                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        Log.d("deleteDataBoardFailuere", t.message.toString())
                                    }
                                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                        Log.d("deleteDataBoardCode", "Code: " + response.code())
                                        onBackPressed()
                                    }
                                })
                                //-------------------FIM DO DELETAR BOARD--------------------
                            }
                        })
                    }
                }
                //------------------DELETAR O PROPRIO BOARD--------------------
                val callback5 = NetworkUtils.request().deleteBoard(boardId)
                callback5.enqueue(object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("deleteDataBoardFailuere", t.message.toString())
                    }
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.d("deleteDataBoardCode", "Code: " + response.code())
                        onBackPressed()
                    }
                })
                //-------------------FIM DO DELETAR BOARD--------------------
            }
        })
    }

    fun deleteCollumn(){
        val callback = NetworkUtils.request().getCards()
        callback.enqueue(object : Callback<List<CardModel>> {
            override fun onFailure(call: Call<List<CardModel>>, t: Throwable) {
                Log.d("getDataBoardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<List<CardModel>>, response: Response<List<CardModel>>) {
                if (!response.isSuccessful){
                    Log.d("getDataBoardCode", "Code: " + response.code())
                    return
                }
                response.body()!!.forEach {
                    if(it.collumnId == collumnId){
                        val callback = NetworkUtils.request().deleteCard(it.cardId!!.toInt())
                        callback.enqueue(object : Callback<Void> {
                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Log.d("deleteDataBoardFailuere", t.message.toString())
                            }
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                Log.d("deleteDataBoardCode", "Code: " + response.code())
                            }
                        })
                    }
                }
                val callback2 = NetworkUtils.request().deleteCollumn(collumnId.toInt())
                callback2.enqueue(object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("deleteDataBoardFailuere", t.message.toString())
                    }
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.d("deleteDataBoardCode", "Code: " + response.code())
                        if(from == "Collumn"){
                            onBackPressed()
                        }
                    }
                })
            }
        })
    }
}