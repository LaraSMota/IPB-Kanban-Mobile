package com.example.cardbe.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.example.cardbe.CreateBoard
import com.example.cardbe.CreateTeam
import com.example.cardbe.R
import com.example.cardbe.Settings
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.model.BoardModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        }

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.homeMenu, menu)
        return true
    }

    fun getDataBoard() {
        val text = findViewById<TextView>(R.id.BoardScreenCollumn1Card1Title)
        val callback = NetworkUtils.request().getBoard(null, null, null)

        callback.enqueue(object : Callback<List<BoardModel>> {
            override fun onFailure(call: Call<List<BoardModel>>, t: Throwable) {
                Log.d("getDataBoardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<List<BoardModel>>, response: Response<List<BoardModel>>) {
                if (!response.isSuccessful){
                    Log.d("getDataBoardCode", "Code: " + response.code())
                    return
                }

                var posts = response.body()
                posts?.forEach {
                    var content = ""
                    content += "ID: " + it.board_id + "\n"
                    content += "Title: " + it.title + "\n"
                    content += "Body: " + it.description + "\n\n"
                    text.append(content)
                }
            }
        })
    }
}


