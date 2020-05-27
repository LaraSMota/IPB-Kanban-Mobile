package com.example.cardbe

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun getDataUser() {
        val callback = NetworkUtils.request().getUsers(null, null, null)

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