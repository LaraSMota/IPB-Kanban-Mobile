package com.example.cardbe

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.model.BoardModel
import com.example.cardbe.ui.home.Home
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateBoard : AppCompatActivity() {
    var boardId: Int? = null
    var isToUpdate = false
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_createboard)

        if(intent.getStringExtra("BoardId")!! != "CREATE"){
            isToUpdate = true
        }
        val description = findViewById<EditText>(R.id.CreateBoardDescription)
        val title = findViewById<EditText>(R.id.CreateBoardTitle)
        val backgroundOptions = findViewById<TableLayout>(R.id.CreateBoardTableLayout)
        val saveButton = findViewById<Button>(R.id.CreateBoardSaveButton)
        var background = findViewById<ImageView>(R.id.CreateBoardBackground1)
        background.setAlpha(1.0f)

        //Set back button and back button color
        var toolbar = findViewById<Toolbar>(R.id.CreateBoardToolbar)
        setSupportActionBar(toolbar)

        val myColorFilter = PorterDuffColorFilter(ResourcesCompat.getColor(resources, R.color.button, null), PorterDuff.Mode.MULTIPLY)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.children.forEach {
            if (it is ImageButton){
                it.drawable.colorFilter = myColorFilter
            }
        }

        //Set onClickListener for each background image option and keep the choosed one
        backgroundOptions.children.forEach {
            val tableRow = it as TableRow
            tableRow.children.forEach {

                it.setOnClickListener{
                background.setAlpha(0.5f)
                background = it as ImageView
                background.setAlpha(1.0f)
                }
            }
        }

        saveButton.setOnClickListener{
            if (title.text.toString().isNotEmpty()){
                if (isToUpdate){
                    updateDataBoard(this, title.text.toString(), description.text.toString(), background.transitionName.toString())
                } else {
                    postDataBoard(this, title.text.toString(), description.text.toString(), background.transitionName.toString())
                }
                onBackPressed()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Be sure to choose Title",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(isToUpdate){
            val boardId = intent.getStringExtra("BoardId")!!.toInt()
            startActivity(Intent(this, Board::class.java).putExtra("BoardId", boardId.toString()))
        } else {
            startActivity(Intent(this, Home::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun postDataBoard(contexto : Context, title: String, description: String?, background: String){
        val post = BoardModel(null, title, description, background, null)
        val callback = NetworkUtils.request().postBoard(post)

        callback.enqueue(object : Callback<BoardModel> {
            override fun onFailure(call: Call<BoardModel>, t: Throwable) {
                Log.d("postDataBoardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<BoardModel>, response: Response<BoardModel>) {
                if (!response.isSuccessful){
                    Log.d("postDataBoardCode", "Code: " + response.code())
                    return
                }
            }
        })
    }

    fun updateDataBoard(contexto : Context, title: String, description: String?, background: String){
        val boardId = intent.getStringExtra("BoardId")!!.toInt()
        val post = BoardModel(boardId, title, description, background, null)
        val callback = NetworkUtils.request().putBoard(boardId, post)

        callback.enqueue(object : Callback<BoardModel> {
            override fun onFailure(call: Call<BoardModel>, t: Throwable) {
                Log.d("postDataBoardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<BoardModel>, response: Response<BoardModel>) {
                if (!response.isSuccessful){
                    Log.d("postDataBoardCode", "Code: " + response.code())
                    return
                }
            }
        })
    }
}