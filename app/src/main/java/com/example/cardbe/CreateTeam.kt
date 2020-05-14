package com.example.cardbe

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class CreateTeam : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_team)
        val search = findViewById<SearchView>(R.id.CreateTeamSearch)
        val listView = findViewById<ListView>(R.id.CreateTeamListView)
        val listViewSelected = findViewById<ListView>(R.id.CreateTeamListViewSelected)
        var users = mutableListOf("Lara", "Aline", "Lucas", "Leonardo")
        var adapterList = mutableListOf("Lara", "Aline", "Lucas", "Leonardo")

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, adapterList)
        listView.adapter = myAdapter

        search.setOnCloseListener {
            myAdapter.clear()
            users.forEach {
                myAdapter.insert(it, myAdapter.count)
            }
            runOnUiThread { myAdapter.notifyDataSetChanged() }
            false
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                myAdapter.clear()
                users.forEach {
                    if (it.toLowerCase().contains(search.query.toString().toLowerCase())){
                        myAdapter.insert(it, myAdapter.count)
                    }
                    runOnUiThread { myAdapter.notifyDataSetChanged() }
                }
                return false
            }

        })

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
