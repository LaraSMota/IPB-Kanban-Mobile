package com.example.recyclerviewexample
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cardbe.R
import com.example.cardbe.recyclerView.HomeItem
import kotlinx.android.synthetic.main.home_item.view.*

class HomeAdapter(private val boardList: List<HomeItem>, private val clickListener: OnHomeItemListener) : RecyclerView.Adapter <HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val boardView = LayoutInflater.from(parent.context).inflate(
            R.layout.home_item,
            parent, false)
        return HomeViewHolder(boardView)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.initialize(boardList.get(position), clickListener)
    }

    override fun getItemCount() = boardList.size

    class HomeViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var recyclerBackground: ImageView = itemView.image_view_rose
        var recyclerTitle: TextView = itemView.text_view_mc
        var recyclerShareIcon: ImageView = itemView.ProjectsCard1ShareImage

        fun initialize(item: HomeItem, action:OnHomeItemListener){
            when(item.background){
                "frutas" -> recyclerBackground.setImageResource(R.drawable.frutas)
                "florbranca" -> recyclerBackground.setImageResource(R.drawable.florbranca)
                "rosas" -> recyclerBackground.setImageResource(R.drawable.rosas)
                else -> recyclerBackground.setImageResource(R.drawable.palmeira2)
            }
            recyclerTitle.text = item.title
            recyclerShareIcon.setImageResource(R.drawable.ic_share_black_24dp)

            itemView.setOnClickListener{
                action.onItemClick(item, adapterPosition)
            }
        }
    }
}

interface OnHomeItemListener{
    fun onItemClick(item: HomeItem, position: Int)
}
