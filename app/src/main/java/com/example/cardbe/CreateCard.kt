package com.example.cardbe

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.model.CardModel
import com.google.android.material.internal.ContextUtils.getActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS


//IMPLEMENTAR O ADICIONAR DOCUMENTOS
//ADICIONAR O CHECKLIST
//ADICIONAR LABELS
//ADICIONAR DUE DATE
//ADICIONAR MEMBERS

class CreateCard : AppCompatActivity() {
    var collumnId = 0
    var boardId = "0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_createcard)
        collumnId = intent.getStringExtra("CollumnId")!!.toInt()
        boardId = intent.getStringExtra("BoardId")

        //Set back button and back button color
        val toolbar = findViewById<Toolbar>(R.id.CreateCardToolbar)
        val title = findViewById<TextView>(R.id.CreateCardTitle)
        val description = findViewById<TextView>(R.id.CreateCardDescription)
        val attachments = findViewById<TextView>(R.id.CreateCardAtachDocument)
        val checklist = findViewById<TextView>(R.id.CreateCardAddChecklist)
        val label = findViewById<TextView>(R.id.CreateCardAddLabels)
        val dueDate = findViewById<TextView>(R.id.CreateCardAddDueDate)
        val members = findViewById<TextView>(R.id.CreateCardAddMembers)
        val comments = findViewById<TextView>(R.id.CreateCardComment)
        val saveButton = findViewById<Button>(R.id.CreateCardSave)
        var date: String = ""
        setSupportActionBar(toolbar)
        val myColorFilter = PorterDuffColorFilter(ResourcesCompat.getColor(resources, R.color.button, null), PorterDuff.Mode.MULTIPLY)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.children.forEach {
            if (it is ImageButton){
                it.drawable.colorFilter = myColorFilter
            }
        }

        dueDate.setOnClickListener{
            val c: Calendar = Calendar.getInstance()
            val year: Int = c.get(Calendar.YEAR)
            val month: Int = c.get(Calendar.MONTH)
            val day: Int = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // Display Selected date in textbox
                    date = "$monthOfYear, $dayOfMonth"
                }, year, month, day
            )
            dpd.show()
        }

        saveButton.setOnClickListener{
            val cardTitle = title.text.toString()
            val cardDescription = description.text.toString()
            val cardComments = comments.text.toString()

            if(cardTitle.isNotBlank() && cardTitle.isNotEmpty()){
                postDataCard(cardTitle, cardComments, cardDescription)
                onBackPressed()
            }else{
                Toast.makeText(
                    applicationContext,
                    "Make sure to enter a title",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun postDataCard(title: String, comments: String, description: String){
        val post = CardModel(null,
            title,
            comments,
            null,
            null,
            null,
            description,
            null,
            collumnId,
            0)
        val callback = NetworkUtils.request().postCard(post)

        callback.enqueue(object : Callback<CardModel> {
            override fun onFailure(call: Call<CardModel>, t: Throwable) {
                Log.d("postDataCardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<CardModel>, response: Response<CardModel>) {
                if (!response.isSuccessful){
                    Log.d("postDataCardode", "Code: " + response.code())
                    return
                }
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, Board::class.java).putExtra("BoardId", boardId))
    }
}