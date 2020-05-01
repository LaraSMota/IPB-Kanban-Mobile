package com.example.cardbe

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children

class CreateBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_createboard)

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
                startActivity(Intent(this, Board::class.java))
            } else {
                Toast.makeText(
                    applicationContext,
                    "Be sure to choose Title",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}