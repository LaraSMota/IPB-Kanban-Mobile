package com.example.cardbe

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.model.UserModel
import com.example.cardbe.information.AboutCardBe
import com.example.cardbe.information.ReportProblem
import com.example.cardbe.ui.login.LoginActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class Settings : AppCompatActivity() {
    val userId = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)
        val editProfile = findViewById<TextView>(R.id.SettingsEditProfile)
        val changePassword = findViewById<TextView>(R.id.SettingsChangePassword)
        val reportProblem = findViewById<TextView>(R.id.SettingsReportaProblem)
        val deleteAccount = findViewById<TextView>(R.id.SettingsDeleteAccount)
        val aboutCardBe = findViewById<TextView>(R.id.SettingsAboutCardBe)
        val logOut = findViewById<TextView>(R.id.SettingsLogout)
        val enableNotifications = findViewById<Switch>(R.id.SettingsEnableNotifications)

        getDataUser(enableNotifications)

        editProfile.setOnClickListener{
            startActivity(Intent(this, Profile::class.java))
        }

        changePassword.setOnClickListener{
            startActivity(Intent(this, ChangePassword::class.java))
        }

        reportProblem.setOnClickListener{
            startActivity(Intent(this, ReportProblem::class.java))
        }

        deleteAccount.setOnClickListener{
            startActivity(Intent(this, DeleteAccount::class.java))
        }

        aboutCardBe.setOnClickListener{
            startActivity(Intent(this, AboutCardBe::class.java))
        }

        logOut.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        enableNotifications.setOnClickListener{
            updateDataUser()
        }


        //Set back button and back button color
        var toolbar = findViewById<Toolbar>(R.id.SettingsToolbar)
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


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun getDataUser(beInformed : Switch) {
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
                //beInformed.isChecked = get!!.beNotified
            }
        })
    }

    fun updateDataUser(){
        val callback = NetworkUtils.request().getUsers(userId)
        val lastName : String
        val firstName : String
        val email : String
        val nickname : String
        val password : String
        val beNotified: String
        val picture : String

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
                val file = File("/storage/emulated/0/Download/Corrections 6.jpg")
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                val userIdRB = RequestBody.create(MediaType.parse("multipart/form-data"), userId.toString())
                val firstNameRB = RequestBody.create(MediaType.parse("multipart/form-data"), get!!.lastName)
                val lastNameRB = RequestBody.create(MediaType.parse("multipart/form-data"), get.firstName)
                val emailRB = RequestBody.create(MediaType.parse("multipart/form-data"), get.email)
                val nicknameRB = RequestBody.create(MediaType.parse("multipart/form-data"), get.nickname)
                val passwordRB = RequestBody.create(MediaType.parse("multipart/form-data"), get.password)
                val beNotifiedRB = RequestBody.create(MediaType.parse("multipart/form-data"), get.beNotified)
                val pictureRB = RequestBody.create(MediaType.parse("multipart/form-data"), get.picture)

                //val callback = NetworkUtils.request().updateProfile(userIdRB, firstNameRB, lastNameRB, emailRB, nicknameRB, passwordRB, beNotifiedRB, pictureRB)
                //callback.enqueue(object : Callback<RequestBody> {
                //    override fun onFailure(call: Call<RequestBody>, t: Throwable) {
                 //       Log.d("updateDataUserFailuere", t.message.toString())
                 //   }

                 //   override fun onResponse(call: Call<RequestBody>, response: Response<RequestBody>) {
                 //       if (!response.isSuccessful){
                 //           Log.d("updateDataUserCode", "Code: " + response.code())
                 //           return
                 //       }
                 //       finish()
                //    }
                //})
            }
        })
    }
}