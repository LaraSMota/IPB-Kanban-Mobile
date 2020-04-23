package com.example.cardbe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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
        background.setAlpha(0.7)

        backgroundOptions.children.forEach {
            val tableRow = it as TableRow
            tableRow.children.forEach {
                it.setOnClickListener{
                background.setAlpha(1.0)
                background = it as ImageView
                background.setAlpha(0.1)
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
}

private fun ImageView.setAlpha(d: Double) {

}
