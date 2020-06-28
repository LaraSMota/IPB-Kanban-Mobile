package com.example.cardbe

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.data.NetworkUtils.Companion.request
import com.example.cardbe.data.model.BoardModel
import com.example.cardbe.data.model.CardModel
import com.example.cardbe.data.model.CollumnModel
import com.example.cardbe.recyclerView.*
import com.example.cardbe.ui.home.Home
import com.example.recyclerviewexample.CollumnAdapter
import com.example.recyclerviewexample.HomeAdapter
import com.example.recyclerviewexample.OnCollumnItemListener
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.fragment_first.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//GERAR O CONTEUDO DINAMICAMENTE


class Board : AppCompatActivity(), OnCollumnItemListener {
    var boardId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        boardId = intent.getStringExtra("BoardId")!!.toInt()
        setContentView(R.layout.activity_board)
        //val linearLayout = findViewById<LinearLayout>(R.id.BoardLinearLayoutHorizontal)

        //Set back button and back button color
        var toolbar = findViewById<Toolbar>(R.id.BoardToolbar)
        setSupportActionBar(toolbar)

        val myColorFilter = PorterDuffColorFilter(ResourcesCompat.getColor(resources, R.color.button, null), PorterDuff.Mode.MULTIPLY)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.children.forEach {
            if (it is ImageButton){
                it.drawable.colorFilter = myColorFilter
            }
        }
        getDataBoard(boardId)
        getDataCollumn()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.board_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.board_menu_add -> {
                startActivity(Intent(this, CreateColumn::class.java).putExtra("BoardId", boardId.toString()))
                true
            }
            R.id.board_menu_Edit -> {
                startActivity(Intent(this, CreateBoard::class.java).putExtra("BoardId", boardId.toString()))
                true
            }
            R.id.board_menu_Delete -> {
                val myIntent = Intent(this, DialogDeleteCollumnDealer::class.java)
                    .putExtra("BoardId", boardId.toString())
                    .putExtra("CollumnId", "0")
                    .putExtra("From", "Board")
                myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                ContextCompat.startActivity(this, myIntent, null)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getDataBoard(boardId: Int) {
        val callback = request().getBoard(boardId)

        callback.enqueue(object : Callback<BoardModel> {
            override fun onFailure(call: Call<BoardModel>, t: Throwable) {
                Log.d("getDataBoardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<BoardModel>, response: Response<BoardModel>) {
                if (!response.isSuccessful){
                    Log.d("getDataBoardCode", "Code: " + response.code())
                    return
                }
                val boardTitle = findViewById<Toolbar>(R.id.BoardToolbar)
                val bordBackground = findViewById<ImageView>(R.id.BoardScreenBackground)
                boardTitle.title = response.body()!!.title
                when(response.body()!!.background){
                    "frutas" -> bordBackground.setImageResource(R.drawable.frutas)
                    "florbranca" -> bordBackground.setImageResource(R.drawable.florbranca)
                    "rosas" -> bordBackground.setImageResource(R.drawable.rosas)
                    else -> bordBackground.setImageResource(R.drawable.palmeira2)
                }
            }
        })
    }

    fun deleteDataBoard(){
        //val text = findViewById<TextView>(R.id.BoardScreenCollumn1Card1Title)
        val callback = request().deleteBoard(5)

        callback.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("deleteDataBoardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("deleteDataBoardCode", "Code: " + response.code())
            }
        })
    }

    fun updateDataBoard(){
        //val text = findViewById<TextView>(R.id.BoardScreenCollumn1Card1Title)
        val post = BoardModel(null, "new Title", "new Description", null, null)
        val callback = request().putBoard(5, post)

        callback.enqueue(object : Callback<BoardModel> {
            override fun onFailure(call: Call<BoardModel>, t: Throwable) {
                Log.d("updateDataBoardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<BoardModel>, response: Response<BoardModel>) {
                if (!response.isSuccessful){
                    Log.d("updateDataBoardCode", "Code: " + response.code())
                    return
                }

                var postResponse : BoardModel? = response.body()

                var content = ""
                content += "Code: " + response.code() + "\n"
                content += "ID: " + (postResponse?.boardId ?: "empty") + "\n"
                content += "Title: " + (postResponse?.title ?: "empty") + "\n"
                content += "Body: " + (postResponse?.description ?: "empty") + "\n\n"

                //text.append(content)
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, Home::class.java))
    }

    fun getDataCollumn() {
        val callback = NetworkUtils.request().getCollumnToRecyclerView()
        callback.enqueue(object : Callback<List<CollumnItem>> {
            override fun onFailure(call: Call<List<CollumnItem>>, t: Throwable) {
                Log.d("getDataBoardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<List<CollumnItem>>, response: Response<List<CollumnItem>>) {
                if (!response.isSuccessful){
                    Log.d("getDataBoardCode", "Code: " + response.code())
                    return
                }
                var collumnList: MutableList<CollumnItem> = mutableListOf()
                response.body()!!.forEach(){
                    if(it.boardId!!.toInt() == boardId){
                        collumnList.add(it)
                    }
                }
                showData(collumnList)
            }
        })
    }
    fun showData(collumn: List<CollumnItem>){
        board_recycler_view.adapter = CollumnAdapter(collumn, this, applicationContext)
        board_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        board_recycler_view.setHasFixedSize(true)

        val dividerItemDecoration = DividerItemDecoration(this , DividerItemDecoration.VERTICAL)
        board_recycler_view.addItemDecoration(dividerItemDecoration)
        val callback = DragManageAdapter(
            CollumnAdapter(collumn, this, applicationContext), this, ItemTouchHelper.RIGHT.or(
                ItemTouchHelper.LEFT), 0)
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(board_recycler_view)
    }

    override fun onItemClick(item: CollumnItem, position: Int) {
        startActivity(Intent(this, CreateCard::class.java)
            .putExtra("CollumnId", item.collumnId.toString())
            .putExtra("BoardId", boardId.toString()))
    }
}