package com.example.cardbe

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children

class ReportProblem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_reportaproblem)

        //Set back button and back button color
        var toolbar = findViewById<Toolbar>(R.id.ReportAProblemtoolbar)
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