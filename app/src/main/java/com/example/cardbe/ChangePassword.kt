package com.example.cardbe

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children

class ChangePassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_changepassword)
        var password = "Exemplo"
        val passwordInput = findViewById<EditText>(R.id.ChangePasswordPassword)
        val newPassword = findViewById<EditText>(R.id.ChangePasswordNewPassword)
        val confirmPassword = findViewById<EditText>(R.id.ChangePasswordConfirmNewPassword)
        val sendButton = findViewById<Button>(R.id.ChangePasswordSaveButton)

        sendButton.setOnClickListener{
            newPassword.setText(newPassword.text.toString().replace(" ",""), TextView.BufferType.EDITABLE)
            confirmPassword.setText(confirmPassword.text.toString().replace(" ",""), TextView.BufferType.EDITABLE)
            if(passwordInput.text.toString() == password){
                if(newPassword.text.toString() == confirmPassword.text.toString()){
                    if(newPassword.text.toString().length > 6 && newPassword.text.toString().isNotBlank()){
                        password = newPassword.text.toString()
                        Toast.makeText(
                            applicationContext,
                            "Password saved",
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Password too short",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Passwords doesn't match",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Wrong current password",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        //Set back button and back button color
        var toolbar = findViewById<Toolbar>(R.id.ChangePasswordToolbar)
        setSupportActionBar(toolbar)

        val myColorFilter = PorterDuffColorFilter(ResourcesCompat.getColor(resources, R.color.button, null), PorterDuff.Mode.MULTIPLY)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.children.forEach {
            if (it is ImageButton){
                it.drawable.colorFilter = myColorFilter
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}