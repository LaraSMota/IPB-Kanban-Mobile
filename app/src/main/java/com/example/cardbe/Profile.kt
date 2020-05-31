package com.example.cardbe

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//PEGAR O ID DO USUARIO LOGADO
//IMPLEMENTAR O ENVIAR FOTO DO USUARIO

class Profile : AppCompatActivity() {
    val userId = 3
    var password = ""

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

        getDataUser()

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
                    updateDataUser(firstName, lastName, email, nickname)
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

    fun getDataUser() {
        val callback = NetworkUtils.request().getUsers(userId)
        callback.enqueue(object : Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.d("getDataUserFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (!response.isSuccessful){
                    Log.d("getDataUserCode", "Code: " + response.code())
                    return
                }

                val get = response.body()
                findViewById<EditText>(R.id.EditProfileFirstName).setText(get!!.firstName)
                findViewById<EditText>(R.id.EditProfileLastName).setText(get!!.lastName)
                findViewById<EditText>(R.id.EditProfileEmail).setText(get!!.email)
                findViewById<EditText>(R.id.EditProfileNickname).setText(get!!.nickname)
                password = get!!.password
            }
        })
    }

    fun updateDataUser(firstName: String, lastName: String, email: String, nickname: String){
        val body = UserModel(null, firstName, lastName, email, nickname, password)
        val callback = NetworkUtils.request().putUser(userId, body)

        callback.enqueue(object : Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.d("updateDataUserFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (!response.isSuccessful){
                    Log.d("updateDataUserCode", "Code: " + response.code())
                    return
                }
                finish()
            }
        })
    }
}