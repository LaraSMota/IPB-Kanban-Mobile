package com.example.cardbe

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.NetworkUtils.Companion.request
import com.example.cardbe.data.model.BoardModel
import com.example.cardbe.data.model.CardModel
import com.example.cardbe.data.model.CollumnModel
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//GERAR O CONTEUDO DINAMICAMENTE


class Board : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_board)
        val linearLayout = findViewById<LinearLayout>(R.id.BoardLinearLayoutHorizontal)

        //Set back button and back button color
        var toolbar = findViewById<Toolbar>(R.id.BoardToolbar)
        setSupportActionBar(toolbar)

        val myColorFilter = PorterDuffColorFilter(ResourcesCompat.getColor(resources, R.color.button, null), PorterDuff.Mode.MULTIPLY)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.children.forEach {
            if (it is ImageButton){
                it.drawable.colorFilter = myColorFilter
            }
        }

        linearLayout.children.forEach {
            val linerLayoutVertical = it as LinearLayout
            linerLayoutVertical.getChildAt(1).setOnClickListener{
                startActivity(Intent(this, CreateCard::class.java))
            }
        }

        val boardId = intent.getStringExtra("BoardId")
        getDataBoard(boardId!!.toInt())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.board_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.board_menu_add -> {
                startActivity(Intent(this, CreateColumn::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getDataBoard(boardId: Int) {
        val text = findViewById<TextView>(R.id.BoardScreenCollumn1Card1Title)
        val callback = request().getBoard(boardId)

        callback.enqueue(object : Callback<BoardModel> {
            override fun onFailure(call: Call<BoardModel>, t: Throwable) {
                Log.d("getDataBoardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<BoardModel>, response: Response<BoardModel>) {
                if (!response.isSuccessful){
                    Log.d("getDataBoardCode", "Code: " + response.code())
                    return
                }
                val boardTitle = findViewById<Toolbar>(R.id.BoardToolbar)
                boardTitle.title = response.body()!!.title
            }
        })
    }

    fun deleteDataBoard(){
        val text = findViewById<TextView>(R.id.BoardScreenCollumn1Card1Title)
        val callback = request().deleteBoard(5)

        callback.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("deleteDataBoardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("deleteDataBoardCode", "Code: " + response.code())
            }
        })
    }

    fun updateDataBoard(){
        val text = findViewById<TextView>(R.id.BoardScreenCollumn1Card1Title)
        val post = BoardModel(null, "new Title", "new Description", null)
        val callback = request().putBoard(5, post)

        callback.enqueue(object : Callback<BoardModel> {
            override fun onFailure(call: Call<BoardModel>, t: Throwable) {
                Log.d("updateDataBoardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<BoardModel>, response: Response<BoardModel>) {
                if (!response.isSuccessful){
                    Log.d("updateDataBoardCode", "Code: " + response.code())
                    return
                }

                var postResponse : BoardModel? = response.body()

                var content = ""
                content += "Code: " + response.code() + "\n"
                content += "ID: " + (postResponse?.boardId ?: "empty") + "\n"
                content += "Title: " + (postResponse?.title ?: "empty") + "\n"
                content += "Body: " + (postResponse?.description ?: "empty") + "\n\n"

                text.append(content)
            }
        })
    }

//    fun getDataCollumn() {
//        val callback = request().getCollumns(nu)
//
//        callback.enqueue(object : Callback<List<CollumnModel>> {
//            override fun onFailure(call: Call<List<CollumnModel>>, t: Throwable) {
//                Log.d("getDataCollumnFailuere", t.message.toString())
//            }
//
//            override fun onResponse(call: Call<List<CollumnModel>>, response: Response<List<CollumnModel>>) {
//                if (!response.isSuccessful){
//                    Log.d("getDataCollumnCode", "Code: " + response.code())
//                    return
//                }
//            }
//        })
//    }

//    fun getDataCard() {
//        val text = findViewById<TextView>(R.id.BoardScreenCollumn1Card1Title)
//        val callback = request().getCards()
//
//        callback.enqueue(object : Callback<List<CardModel>> {
//            override fun onFailure(call: Call<List<CardModel>>, t: Throwable) {
//                Log.d("getDataBoardFailuere", t.message.toString())
//            }
//
//            override fun onResponse(call: Call<List<CardModel>>, response: Response<List<CardModel>>) {
//                if (!response.isSuccessful){
//                    Log.d("getDataBoardCode", "Code: " + response.code())
//                    return
//                }
//            }
//        })
//    }

}