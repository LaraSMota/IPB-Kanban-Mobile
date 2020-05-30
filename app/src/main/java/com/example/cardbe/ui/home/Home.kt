package com.example.cardbe.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.example.cardbe.CreateBoard
import com.example.cardbe.CreateTeam
import com.example.cardbe.R
import com.example.cardbe.Settings
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout


class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val menuIcon = findViewById<ImageView>(R.id.HomeMenu)

        menuIcon.setOnClickListener{
            startActivity(Intent(this, Settings::class.java))
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            startActivity(Intent(this, CreateBoard::class.java))
        }

        viewPager.viewTreeObserver.addOnScrollChangedListener {
            if (tabs.selectedTabPosition == 0){
                findViewById<FloatingActionButton>(R.id.fab).show()
                findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
                    finish()
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

//        val pullToRefresh = findViewById<SwipeRefreshLayout>(R.id.pullToRefresh);
//        pullToRefresh.setOnRefreshListener {
//            refreshData()
//            pullToRefresh.isRefreshing = false
//        }
    }

    fun refreshData(){
        
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.homeMenu, menu)
        return true

    }
}


