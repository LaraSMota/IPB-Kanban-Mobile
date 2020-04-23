package com.example.cardbe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cardbe.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_forgotpassword.*

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_singup)

        val firstName = findViewById<EditText>(R.id.SignUpFirstName)
        val lastName = findViewById<EditText>(R.id.SignUpLastName)
        val email = findViewById<EditText>(R.id.SignUpEmail)
        val confirmEmail = findViewById<EditText>(R.id.SignUpConfirmEmail)
        val nickname = findViewById<EditText>(R.id.SignUpNickname)
        val password = findViewById<EditText>(R.id.SignUpPassword)
        val signUpButton = findViewById<Button>(R.id.SignUpSignUpButton)
        val termBox = findViewById<CheckBox>(R.id.SignUpTermsCheckBox)
        var isValid = false
        signUpButton.isEnabled = false

        termBox.setOnClickListener{
            signUpButton.isEnabled = termBox.isChecked
        }

        signUpButton.setOnClickListener {
            val isFill = firstName.text.isNotEmpty() && lastName.text.isNotEmpty() && email.text.isNotEmpty() && confirmEmail.text.isNotEmpty() && nickname.text.isNotEmpty() && password.text.isNotEmpty()
            if (!isFill){
                Toast.makeText(
                    applicationContext,
                    "Please, fill all the spaces to continue",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()
                if(!isValid){
                    Toast.makeText(
                        applicationContext,
                        "Enter a valid e-mail adress",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if(!email.text.toString().equals(confirmEmail.text.toString())){
                        Toast.makeText(
                            applicationContext,
                            "Different e-mails, please check ",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                }
            }
        }
    }
}