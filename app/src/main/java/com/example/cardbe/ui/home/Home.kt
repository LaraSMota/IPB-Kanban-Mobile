package com.example.cardbe.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.example.cardbe.CreateBoard
import com.example.cardbe.CreateTeam
import com.example.cardbe.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            startActivity(Intent(this, CreateBoard::class.java))
        }

        viewPager.viewTreeObserver.addOnScrollChangedListener {
            if (tabs.selectedTabPosition == 0){
                findViewById<FloatingActionButton>(R.id.fab).show()
                findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
                    startActivity(Intent(this, CreateBoard::class.java))
                }
            }
            if (tabs.selectedTabPosition == 1){
                findViewById<FloatingActionButton>(R.id.fab).show()
                findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
                    startActivity(Intent(this, CreateTeam::class.java))
                }
            }
            if (tabs.selectedTabPosition == 2){
                findViewById<FloatingActionButton>(R.id.fab).hide()
                }
            }
        }

}


