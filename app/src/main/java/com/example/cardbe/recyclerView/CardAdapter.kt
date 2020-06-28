package com.example.cardbe.recyclerView

import android.graphics.Color
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.cardbe.R
import com.example.cardbe.ui.home.FirstFragment
import com.example.cardbe.ui.home.SecondFragment
import com.example.cardbe.ui.home.ThirdFragment
import kotlinx.android.synthetic.main.card_item.view.*

class CardAdapter(private val cardList: List<CardItem>) : RecyclerView.Adapter <CardAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_item,
            parent, false)

        return CardViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.initialize(cardList.get(position))
    }

    override fun getItemCount() = cardList.size

    class CardViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        var recyclerTitle: TextView = itemView.titulocard
        var recyclerattachments: ImageView = itemView.icon2card
        var recyclerlabels: CardView = itemView.label
        var recyclerduedate: TextView = itemView.duedatecard
        var recyclercomments: ImageView = itemView.icon3card
        var recyclerchecklist: ImageView = itemView.icon1card


        fun initialize(item: CardItem){
            recyclerTitle.text = item.title.toString()

            if(item.dueDate.isNullOrEmpty()){
                recyclerduedate.visibility = View.GONE
            }else{
                recyclerduedate.text= item.dueDate.toString()
            }

            if(item.attachments.isNullOrEmpty()){recyclerattachments.visibility = View.GONE}
            if(item.checklist.isNullOrEmpty()){recyclerchecklist.visibility = View.GONE}
            if(item.comments.isNullOrEmpty()){recyclercomments.visibility = View.GONE}

            when(item.labels){
                "verde" -> recyclerlabels.setCardBackgroundColor(Color.parseColor("#19b338"))
                "amarelo" -> recyclerlabels.setCardBackgroundColor(Color.parseColor("#fff23b"))
                "vermelho" -> recyclerlabels.setCardBackgroundColor(Color.parseColor("#b8124c"))
                "azul" -> recyclerlabels.setCardBackgroundColor(Color.parseColor("#0e5cc9"))
                else -> recyclerlabels.visibility = View.GONE
            }

            itemView.setOnClickListener{
                //action.onItemClick(item, adapterPosition)
            }
        }
    }
}