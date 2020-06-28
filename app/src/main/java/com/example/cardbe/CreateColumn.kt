package com.example.cardbe

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.model.CardModel
import com.example.cardbe.data.model.CollumnModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//ATUALIZAR AS COLUNAS QUANDO VOLTAR PARA O BOARD

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class CreateColumn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_column)
        val saveButton = findViewById<Button>(R.id.CreateColumnSaveButton)
        val title = findViewById<EditText>(R.id.CreateColumnTitle)
        //Set back button and back button color
        var toolbar = findViewById<Toolbar>(R.id.CreateColumnToolbar)
        setSupportActionBar(toolbar)

        val myColorFilter = PorterDuffColorFilter(ResourcesCompat.getColor(resources,
            R.color.button, null), PorterDuff.Mode.MULTIPLY)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.children.forEach {
            if (it is ImageButton){
                it.drawable.colorFilter = myColorFilter
            }
        }
        saveButton.setOnClickListener{
            if(title.text.toString().isNotBlank()){
                val boardId = intent.getStringExtra("BoardId")!!.toInt()
                postDataCollumn(title.text.toString(), boardId)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please enter a title",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val boardId = intent.getStringExtra("BoardId")!!.toInt()
        startActivity(Intent(this, Board::class.java).putExtra("BoardId", boardId.toString()))
    }

    fun postDataCollumn(title: String, boardId: Int){
        val post = CollumnModel(null, title, boardId, 0)
        val callback = NetworkUtils.request().postCollumn(post)

        callback.enqueue(object : Callback<CollumnModel> {
            override fun onFailure(call: Call<CollumnModel>, t: Throwable) {
                Log.d("postDataCollumnFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<CollumnModel>, response: Response<CollumnModel>) {
                if (!response.isSuccessful){
                    Log.d("postDataCollumnode", "Code: " + response.code())
                    return
                }
                onBackPressed()
            }
        })
    }
}
