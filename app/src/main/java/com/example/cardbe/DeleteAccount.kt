package com.example.cardbe

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children

class DeleteAccount  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_deleteaccount)
        val cancelButton = findViewById<Button>(R.id.DeleteCancel)
        val confirmButton = findViewById<Button>(R.id.DeleteConfirm)

        //Set back button and back button color
        var toolbar = findViewById<Toolbar>(R.id.DeleteAccountToolbar)
        setSupportActionBar(toolbar)

        val myColorFilter = PorterDuffColorFilter(ResourcesCompat.getColor(resources, R.color.button, null), PorterDuff.Mode.MULTIPLY)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.children.forEach {
            if (it is ImageButton){
                it.drawable.colorFilter = myColorFilter
            }
        }

        cancelButton.setOnClickListener{
            finish()
        }

        confirmButton.setOnClickListener{
            //Apagar usuario do banco
            val password = "Teste01"
            val confirmPassword = findViewById<EditText>(R.id.DeleteAccountConfirmNewPassword)
            if(password == confirmPassword.text.toString()){

                super.finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Password don't match",
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