package com.example.cardbe

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
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
        var adapterListSelected = mutableListOf("")

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, adapterList)
        listView.adapter = myAdapter
        val myAdapterSelected = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, adapterListSelected)
        listViewSelected.adapter = myAdapterSelected
        myAdapterSelected.clear()
        runOnUiThread { myAdapterSelected.notifyDataSetChanged() }

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

        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                var add = true
                for(item in 0 until  myAdapterSelected.count step 1){
                    add = add && !myAdapterSelected.getItem(item).equals(myAdapter.getItem(position))
                }
                if (add){
                    myAdapterSelected.insert(myAdapter.getItem(position), myAdapterSelected.count)
                    runOnUiThread { myAdapterSelected.notifyDataSetChanged() }
                }
            }

        listViewSelected.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                myAdapterSelected.remove(myAdapterSelected.getItem(position))
                runOnUiThread { myAdapterSelected.notifyDataSetChanged() }
            }

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
