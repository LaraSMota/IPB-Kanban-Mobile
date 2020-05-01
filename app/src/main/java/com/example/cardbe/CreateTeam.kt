package com.example.cardbe

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_create_team.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class CreateTeam : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_team)

        //Set back button and back button color
        var toolbar = findViewById<Toolbar>(R.id.CreateTeamToolbar)
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
}
