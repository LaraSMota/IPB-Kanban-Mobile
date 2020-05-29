package com.example.cardbe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.model.UserModel
import com.example.cardbe.ui.login.LoginActivity
import com.example.cardbe.ui.login.afterTextChanged
import kotlinx.android.synthetic.main.activity_forgotpassword.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun getDataUser() {
        val callback = NetworkUtils.request().getUsers()

        callback.enqueue(object : Callback<List<UserModel>> {
            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                Log.d("getDataUserFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                if (!response.isSuccessful){
                    Log.d("getDataUserCode", "Code: " + response.code())
                    return
                }

//                var posts = response.body()
//                posts?.forEach {
//                    var content = ""
//                    content += "ID: " + it.board_id + "\n"
//                    content += "Title: " + it.title + "\n"
//                    content += "Body: " + it.description + "\n\n"
//                    text.append(content)
//                }
            }
        })
    }
}