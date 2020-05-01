package com.example.cardbe

import android.content.Intent
import android.os.Bundle
import android.widget.*
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
            val isFill = firstName.text.isNotBlank() && lastName.text.isNotBlank() && email.text.isNotBlank() && confirmEmail.text.isNotBlank() && nickname.text.isNotBlank() && password.text.isNotBlank()
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
                        password.setText(password.text.toString().replace(" ",""), TextView.BufferType.EDITABLE)
                        if(password.text.toString().length > 6 && password.text.toString().isNotBlank()){
                            val formPassword = password.text.toString()
                            val formFirsName = firstName.text.toString()
                            val formLastName = lastName.text.toString()
                            val formEmail = email.text.toString()
                            val formNickname = nickname.text.toString()
                            Toast.makeText(
                                applicationContext,
                                "Sign Up finished",
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
                    }
                }
            }
        }
    }
}