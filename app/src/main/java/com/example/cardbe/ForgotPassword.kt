package com.example.cardbe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cardbe.ui.login.LoginActivity
import com.example.cardbe.ui.login.afterTextChanged
import kotlinx.android.synthetic.main.activity_forgotpassword.*
import kotlinx.android.synthetic.main.activity_login.*

class ForgotPassword : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forgotpassword)
        val email = findViewById<EditText>(R.id.ForgotPasswordEmail)
        val sendButton = findViewById<Button>(R.id.ForgotPasswordSendButton)
        var isValid = false

        sendButton.setOnClickListener {
            isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches() && email.text.toString().isNotEmpty()
            if (!isValid){
                Toast.makeText(
                    applicationContext,
                    "Enter a valid e-mail adress",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Recover Link sent to " + email.text.toString(),
                    Toast.LENGTH_LONG
                ).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}