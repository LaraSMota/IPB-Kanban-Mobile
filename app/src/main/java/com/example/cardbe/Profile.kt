package com.example.cardbe

import android.app.Dialog
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profilescreen)
        val firstNameInput = findViewById<EditText>(R.id.EditProfileFirstName)
        val lastNameInput = findViewById<EditText>(R.id.EditProfileLastName)
        val emailInput = findViewById<EditText>(R.id.EditProfileEmail)
        val nicknameInput = findViewById<EditText>(R.id.EditProfileNickname)
        val saveButton = findViewById<Button>(R.id.EditProfileSaveButton)
        val profileImageInput = findViewById<ImageView>(R.id.EditProfileProfileImage)
        var firstName = ""
        var lastName = ""
        var email = ""
        var nickname = ""
        var profileImage = R.drawable.ic_person_white_24dp
        firstNameInput.setText(firstName, TextView.BufferType.EDITABLE)
        lastNameInput.setText(lastName, TextView.BufferType.EDITABLE)
        emailInput.setText(email, TextView.BufferType.EDITABLE)
        nicknameInput.setText(nickname, TextView.BufferType.EDITABLE)

        //Set back button and back button color
        val toolbar = findViewById<Toolbar>(R.id.EditProfileToolbar)
        setSupportActionBar(toolbar)

        val myColorFilter = PorterDuffColorFilter(ResourcesCompat.getColor(resources, R.color.button, null), PorterDuff.Mode.MULTIPLY)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.children.forEach {
            if (it is ImageButton){
                it.drawable.colorFilter = myColorFilter
            }
        }

        profileImageInput.setOnClickListener{

        }

        saveButton.setOnClickListener {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput.text.toString()).matches() && emailInput.text.toString().isNotEmpty()) {
                if(firstNameInput.text.toString().isNotBlank() && lastNameInput.text.toString().isNotBlank() && nicknameInput.text.toString().isNotBlank()){
                    email = emailInput.text.toString()
                    firstName = firstNameInput.text.toString()
                    lastName = lastNameInput.text.toString()
                    nickname = nicknameInput.text.toString()
                    finish()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Empty spaces are not allowed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            else{
                Toast.makeText(
                    applicationContext,
                    "Enter a valid e-mail",
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