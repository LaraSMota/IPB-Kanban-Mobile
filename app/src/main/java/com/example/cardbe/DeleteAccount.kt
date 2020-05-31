package com.example.cardbe

import android.content.Intent
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
import com.example.cardbe.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//DESLOGAR E NÃO PERMITIR O CLICAR PARA VOLTAR

class DeleteAccount  : AppCompatActivity() {
    var userId = 5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deleteaccount)
        val cancelButton = findViewById<Button>(R.id.DeleteCancel)
        val confirmButton = findViewById<Button>(R.id.DeleteConfirm)
        val intent = Intent( this, LoginActivity::class.java)

        var password = ""
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
                password = get!!.password
            }
        })

        //Set back button and back button color
        val toolbar = findViewById<Toolbar>(R.id.DeleteAccountToolbar)
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
            val confirmPassword = findViewById<EditText>(R.id.DeleteAccountConfirmNewPassword)
            if(password == confirmPassword.text.toString()){
                val callbackDelete = NetworkUtils.request().deleteUser(userId)
                callbackDelete.enqueue(object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("deleteDataUserFailuere", t.message.toString())
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.d("deleteDataUserCode", "Code: " + response.code())
                    }
                })
                super.finish()
                startActivity(intent)
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
}