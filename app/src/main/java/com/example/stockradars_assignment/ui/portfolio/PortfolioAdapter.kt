package com.example.stockradars_assignment.ui.portfolio

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stockradars_assignment.R

// RecyclerView Adapter to display portfolio items
class PortfolioAdapter(private var portfolioList: List<PortfolioModel>) :
    RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder>() {

    class PortfolioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val pendingTextView: TextView = itemView.findViewById(R.id.pendingTextView)
        val pendingValue: TextView = itemView.findViewById(R.id.pendingValue)
        val withdrawableValue: TextView = itemView.findViewById(R.id.withdrawableValue)
        val changeTextView: TextView = itemView.findViewById(R.id.changeTextView)
        val imageView: ImageView = itemView.findViewById(R.id.planImageView)
    }

    // Called when RecyclerView needs a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.portfolio_item, parent, false)
        return PortfolioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        val portfolioItem = portfolioList[position]

        // Set the title and values for the item
        holder.titleTextView.text = portfolioItem.title
        holder.pendingValue.text = "${String.format("%.2f", portfolioItem.pending_point)}"
        holder.withdrawableValue.text = "${String.format("%.2f",portfolioItem.withdrawable_point)}"

        // If the pending point is zero make it's invisible
        if (portfolioItem.pending_point == 0.0) {
            holder.pendingTextView.visibility = View.INVISIBLE
            holder.pendingValue.visibility = View.INVISIBLE
        } else {
            holder.pendingTextView.visibility = View.VISIBLE
            holder.pendingValue.visibility = View.VISIBLE
        }

        val change = portfolioItem.change
        when{
            change > 0.0 -> {
                holder.changeTextView.text = "(+${String.format("%.2f", change)})" // If change is greater than 0.0 add "+" sign in the change
                holder.changeTextView.setTextColor(Color.parseColor("#4CAF50")) // If change is greater than 0.0 set text color to green
            }
            change < 0.0 -> {
                holder.changeTextView.text = "(${String.format("%.2f", change)})"
                holder.changeTextView.setTextColor(Color.parseColor("#ff4e67")) // If change is less than 0.0 set text color to red
            }
            change == 0.0 -> {
                holder.changeTextView.text = "(${String.format("%.2f", change)})"
                holder.changeTextView.setTextColor(Color.parseColor("#ffd46e")) // If change is equal to 0.0 set text color to yellow
            }
        }

        // Load the image_plan from URL
        Glide.with(holder.itemView.context)
            .load(portfolioItem.image_plan)
            .circleCrop()
            .into(holder.imageView)
    }

    // Returns the total number of items in the portfolio list
    override fun getItemCount(): Int = portfolioList.size

    // Updates the data in the adapter
    fun updateData(newPortfolioList: List<PortfolioModel>) {
        portfolioList = newPortfolioList
        notifyDataSetChanged()
    }
}

