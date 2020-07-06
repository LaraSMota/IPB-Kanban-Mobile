package com.example.cardbe.recyclerView

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cardbe.Card
import com.example.cardbe.R
import com.example.cardbe.ui.home.FirstFragment
import com.example.cardbe.ui.home.SecondFragment
import com.example.cardbe.ui.home.ThirdFragment
import kotlinx.android.synthetic.main.card_item.view.*
import java.util.*

class CardAdapter(private val cardList: MutableList<CardItem>, private val contexto: Context) : RecyclerView.Adapter <CardAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_item,
            parent, false)

        return CardViewHolder(cardView,  contexto)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.initialize(cardList.get(position), contexto)
    }

    override fun getItemCount() = cardList.size

    class CardViewHolder (itemView: View,  contexto: Context) : RecyclerView.ViewHolder(itemView){

        var recyclerTitle: TextView = itemView.titulocard
        var recyclerattachments: ImageView = itemView.icon2card
        var recyclerlabels: CardView = itemView.label
        var recyclerduedate: TextView = itemView.duedatecard
        var recyclercomments: ImageView = itemView.icon3card
        var recyclerchecklist: ImageView = itemView.icon1card


        fun initialize(item: CardItem,  contexto: Context){
            recyclerTitle.text = item.title.toString()

            if(item.dueDate.isNullOrEmpty()){
                recyclerduedate.visibility = View.GONE
            }else{
                recyclerduedate.text= item.dueDate.toString()
            }

            if(item.attachments.isNullOrEmpty()){recyclerattachments.visibility = View.GONE}
            if(item.checklist.isNullOrEmpty()){recyclerchecklist.visibility = View.GONE}
            if(item.comments.isNullOrEmpty()){recyclercomments.visibility = View.GONE}

            when(item.lables){
                "verde" -> recyclerlabels.setCardBackgroundColor(Color.parseColor("#19b338"))
                "amarelo" -> recyclerlabels.setCardBackgroundColor(Color.parseColor("#fff23b"))
                "vermelho" -> recyclerlabels.setCardBackgroundColor(Color.parseColor("#b8124c"))
                "azul" -> recyclerlabels.setCardBackgroundColor(Color.parseColor("#0e5cc9"))
                else -> recyclerlabels.visibility = View.GONE
            }

            itemView.setOnClickListener{
                val myIntent = Intent(contexto, Card::class.java)
                    .putExtra("CollumnId", item.collumnId.toString())
                    .putExtra("CardId", item.cardId.toString())
                myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                ContextCompat.startActivity(contexto, myIntent, null)
            }
        }
    }

    /**
     * Function called to swap dragged items
     */
    fun swapItems(fromPosition: Int, toPosition: Int) {
        cardList[fromPosition] = cardList[toPosition].also { cardList[toPosition] = cardList[fromPosition]}
        //Collections.swap(cardList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }
}