package com.example.stockradars_assignment.ui.indices
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stockradars_assignment.R
import java.text.NumberFormat
import java.util.Locale

// RecyclerView Adapter to display Indices items
class IndicesAdapter(private var dataList: List<DataModel>) :
    RecyclerView.Adapter<IndicesAdapter.IndicesViewHolder>() {

    inner class IndicesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shortNameTextView: TextView = itemView.findViewById(R.id.textViewShortName)
        val priceTextView: TextView = itemView.findViewById(R.id.textViewPrice)
        val changeTextView: TextView = itemView.findViewById(R.id.textViewChange)
        val percentChangeTextView: TextView = itemView.findViewById(R.id.textViewPercentChange)
        val layoutContainer: LinearLayout = itemView.findViewById(R.id.layoutContainer)
    }
    // Method called to create each new item in the RecyclerView.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_item, parent, false)
        return IndicesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IndicesViewHolder, position: Int) {
        val currentItem = dataList[position]

        holder.shortNameTextView.text = currentItem.short_name

        val priceFormatter = NumberFormat.getInstance(Locale.getDefault())
        holder.priceTextView.text = priceFormatter.format(currentItem.price)

        val changeText = if (currentItem.change >= 0) {
            "+${String.format("%.2f", currentItem.change)}"// Add "+" sign if the change is positive.
        } else {
            String.format("%.2f", currentItem.change)
        }
        holder.changeTextView.text = changeText


        val percentChangeText = if (currentItem.percent_change >= 0) {
            "+${String.format("%.2f%%", currentItem.percent_change)}" // Add "+" sign if the percentage change is positive.
        } else {
            String.format("%.2f%%", currentItem.percent_change)
        }
        holder.percentChangeTextView.text = percentChangeText

        // Change the background color based on the percentChange value
        val value_percent_change = currentItem.percent_change
        when {
            value_percent_change > 0.5 -> {
                // If the percent change is greater than 0.5, set the background to dark green
                holder.layoutContainer.setBackgroundColor(Color.parseColor("#0fcc58"))
            }
            value_percent_change > 0 -> {
                // If the percent change is positive but less than 0.5, set the background to green
                holder.layoutContainer.setBackgroundColor(Color.parseColor("#4cec8a"))
            }
            value_percent_change < 0 -> {
                // If the percent change is less than 0, set the background to red
                holder.layoutContainer.setBackgroundColor(Color.parseColor("#ff4e67"))
            }
        }
    }

    // Returns the number of items in the list
    override fun getItemCount() = dataList.size

    // Method called to update the data list in the adapter.
    fun updateData(newDataList: List<DataModel>) {
        dataList = newDataList
        notifyDataSetChanged()
    }
}
