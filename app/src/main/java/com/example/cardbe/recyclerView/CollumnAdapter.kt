package com.example.recyclerviewexample
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.ActionProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cardbe.Board
import com.example.cardbe.CreateCard
import com.example.cardbe.CreateColumn
import com.example.cardbe.R
import com.example.cardbe.data.NetworkUtils
import com.example.cardbe.recyclerView.*
import com.example.cardbe.ui.home.Home
import kotlinx.android.synthetic.main.activity_board.*
import kotlinx.android.synthetic.main.collumn_item.view.*
import kotlinx.android.synthetic.main.home_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CollumnAdapter(private val collumnList: List<CollumnItem>, private val clickListener: OnCollumnItemListener, private val contexto: Context) : RecyclerView.Adapter <CollumnAdapter.CollumnViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollumnViewHolder {
        val boardView = LayoutInflater.from(parent.context).inflate(
            R.layout.collumn_item,
            parent, false)
        return CollumnViewHolder(boardView)
    }

    override fun onBindViewHolder(holder: CollumnViewHolder, position: Int) {
        holder.initialize(collumnList.get(position), clickListener, contexto)
    }

    override fun getItemCount() = collumnList.size

    class CollumnViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var recyclerTitle: TextView = itemView.BoardScreenCollumnTitle
        var recyclerCardList: RecyclerView = itemView.recycler_view_card

        fun initialize(item: CollumnItem, action:OnCollumnItemListener, contexto: Context){
            recyclerTitle.text = item.title
            getCardData(item.collumnId, recyclerCardList, contexto)
            itemView.BoardScreenCollumnAddCardTextView.setOnClickListener{
                action.onItemClick(item, adapterPosition)
            }
            itemView.icon2card.setOnClickListener{
                val myIntent = Intent(contexto, DialogDeleteCollumnDealer::class.java)
                    .putExtra("BoardId", item.boardId.toString())
                    .putExtra("CollumnId", item.collumnId.toString())
                    .putExtra("From", "Collumn")
                     myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(contexto, myIntent, null)
            }
        }
    }
    fun swapItems(fromPosition: Int, toPosition: Int) {
        Collections.swap(collumnList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }
}

interface OnCollumnItemListener{
    fun onItemClick(item: CollumnItem, position: Int)
}

fun getCardData(collumnId: Int?, recyclerview: RecyclerView, contexto: Context){
    val callback = NetworkUtils.request().getCardToRecyclerView()
    callback.enqueue(object : Callback<List<CardItem>> {
        override fun onFailure(call: Call<List<CardItem>>, t: Throwable) {
            Log.d("getDataBoardFailuere", t.message.toString())
        }

        override fun onResponse(call: Call<List<CardItem>>, response: Response<List<CardItem>>) {
            if (!response.isSuccessful){
                Log.d("getDataBoardCode", "Code: " + response.code())
                return
            }
            var cardList: MutableList<CardItem> = mutableListOf()
            response.body()!!.forEach(){
                if(it.collumnId!!.toInt() == collumnId){
                    cardList.add(it)
                }
            }
            showDataCardData(cardList, recyclerview, contexto)
        }
    })
}

fun showDataCardData(card: MutableList<CardItem>, recyclerview: RecyclerView, contexto: Context){
    recyclerview.adapter = CardAdapter(card, contexto)
    recyclerview.layoutManager = LinearLayoutManager(contexto, LinearLayoutManager.VERTICAL, false)
    recyclerview.setHasFixedSize(true)

    val dividerItemDecoration = DividerItemDecoration(contexto , DividerItemDecoration.VERTICAL)
    recyclerview.addItemDecoration(dividerItemDecoration)
    val callback = DragManageAdapterCard(CardAdapter(card, contexto), contexto, ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), 0)
    val helper = ItemTouchHelper(callback)
    helper.attachToRecyclerView(recyclerview)
}

