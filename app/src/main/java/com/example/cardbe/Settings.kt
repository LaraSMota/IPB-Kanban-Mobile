package com.example.cardbe

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.example.cardbe.information.AboutCardBe
import com.example.cardbe.information.ReportProblem
import com.example.cardbe.ui.login.LoginActivity

class Settings : AppCompatActivity() {
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
        var beInformed = true

        enableNotifications.isChecked = beInformed

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
            beInformed = enableNotifications.isChecked
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
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        return true
    }


//    override fun onBackPressed() {
//        super.onBackPressed()
//        startActivity(Intent(this, Home::class.java))
//    }
}