package com.example.cardbe

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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