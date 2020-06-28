package com.example.cardbe

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.model.UserModel
import com.example.cardbe.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//LER USUÁRIO DO USUARIO LOGADO
//FAZER A TELA DESLOGAR

class ChangePassword : AppCompatActivity() {
    var userId = 3
    var firsName = ""
    var lastName = ""
    var email = ""
    var nickname = ""
    var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_changepassword)
        val passwordInput = findViewById<EditText>(R.id.ChangePasswordPassword)
        val newPassword = findViewById<EditText>(R.id.ChangePasswordNewPassword)
        val confirmPassword = findViewById<EditText>(R.id.ChangePasswordConfirmNewPassword)
        val sendButton = findViewById<Button>(R.id.ChangePasswordSaveButton)

        val callbackGet = NetworkUtils.request().getUsers(userId)
        callbackGet.enqueue(object : Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.d("getDataUserFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (!response.isSuccessful) {
                    Log.d("getDataUserCode", "Code: " + response.code())
                    return
                }
                val get = response.body()
                firsName = get!!.firstName
                lastName = get.lastName
                email = get.email
                nickname = get.nickname
                password = get.password
            }
        })
        sendButton.setOnClickListener {
            newPassword.setText(
                newPassword.text.toString().replace(" ", ""),
                TextView.BufferType.EDITABLE
            )
            confirmPassword.setText(
                confirmPassword.text.toString().replace(" ", ""),
                TextView.BufferType.EDITABLE
            )
            if (passwordInput.text.toString() == password) {
                if (newPassword.text.toString() == confirmPassword.text.toString()) {
                    if (newPassword.text.toString().length > 6 && newPassword.text.toString()
                            .isNotBlank()
                    ) {
                        password = newPassword.text.toString()
                        val post = UserModel(null, firsName, lastName, email, nickname, password, null, "true")
                        val callbackPut = NetworkUtils.request().putUser(userId, post)

                        callbackPut.enqueue(object : Callback<UserModel> {
                            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                                Log.d("updateDataUserFailuere", t.message.toString())
                            }

                            override fun onResponse(
                                call: Call<UserModel>,
                                response: Response<UserModel>
                            ) {
                                if (!response.isSuccessful) {
                                    Log.d("updateDataUserCode", "Code: " + response.code())
                                    return
                                }
                                Toast.makeText(
                                    applicationContext,
                                    "Password saved",
                                    Toast.LENGTH_LONG
                                ).show()
                                finish()
                            }
                        })
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
        val toolbar = findViewById<Toolbar>(R.id.ChangePasswordToolbar)
        setSupportActionBar(toolbar)

        val myColorFilter = PorterDuffColorFilter(
            ResourcesCompat.getColor(resources, R.color.button, null),
            PorterDuff.Mode.MULTIPLY
        )

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.children.forEach {
            if (it is ImageButton) {
                it.drawable.colorFilter = myColorFilter
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
