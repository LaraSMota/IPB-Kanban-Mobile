package com.example.cardbe.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cardbe.Board
import com.example.cardbe.R
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.recyclerView.HomeItem
import com.example.recyclerviewexample.HomeAdapter
import com.example.recyclerviewexample.OnHomeItemListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_first.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//ACRESCENTAR BOTÃO DE ATUALIZAR

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), OnHomeItemListener {
    lateinit var myView : View
    lateinit var myOwnerView : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_first, container, false)
        myOwnerView = inflater.inflate(R.layout.activity_home, container, false)
        getDataBoard()
        if (super.isResumed()){
            inflater.inflate(R.layout.activity_home, container, false).findViewById<FloatingActionButton>(R.id.fab).isVisible = true
        }

        return myView
    }

    fun getDataBoard() {
        val callback = NetworkUtils.request().getBoardToRecyclerView()

        callback.enqueue(object : Callback<List<HomeItem>> {
            override fun onFailure(call: Call<List<HomeItem>>, t: Throwable) {
                Log.d("getDataBoardFailuere", t.message.toString())
            }

            override fun onResponse(call: Call<List<HomeItem>>, response: Response<List<HomeItem>>) {
                if (!response.isSuccessful){
                    Log.d("getDataBoardCode", "Code: " + response.code())
                    return
                }
                showData(response.body()!!)
            }
        })
    }
    fun showData(board: List<HomeItem>){
        home_recycler_view.adapter = HomeAdapter(board, this)
        home_recycler_view.layoutManager = LinearLayoutManager(super.getContext())
        home_recycler_view.setHasFixedSize(true)
    }

    override fun onItemClick(item: HomeItem, position: Int) {
        startActivity(Intent(context, Board::class.java).putExtra("BoardId", item.boardId.toString()))
    }
}
