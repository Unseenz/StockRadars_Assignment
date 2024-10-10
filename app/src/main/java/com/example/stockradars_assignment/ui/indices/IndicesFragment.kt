package com.example.stockradars_assignment.ui.indices

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stockradars_assignment.R
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IndicesFragment : Fragment() {

    private lateinit var lastUpdateTextView: TextView
    private lateinit var recyclerViewGrid: RecyclerView
    private lateinit var gridAdapter: IndicesAdapter
    private lateinit var indicesViewModel: IndicesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_indices, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        indicesViewModel = ViewModelProvider(this).get(IndicesViewModel::class.java)

        recyclerViewGrid = view.findViewById(R.id.recyclerViewGrid)
        lastUpdateTextView = view.findViewById(R.id.textViewLastUpdate)

        // Set the RecyclerView with a GridLayout 2 columns
        recyclerViewGrid.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewGrid.setHasFixedSize(true)

        gridAdapter = IndicesAdapter(emptyList())
        recyclerViewGrid.adapter = gridAdapter

        indicesViewModel.indicesData.observe(viewLifecycleOwner) { indiceData ->
            val formatteddata = formatDate(indiceData.lastUpdate)
            // Display the last updated date in the TextView
            lastUpdateTextView.text = "Last Update ${formatteddata}"
            // Update the RecyclerView with the new data from the ViewModel
            gridAdapter.updateData(indiceData.dataList)
        }

        loadJsonData()
    }

    // Load the JSON data from 'indices.json'
    private fun loadJsonData() {
        val jsonString: String
        try {
            val context: Context = requireContext()
            jsonString = context.assets.open("indices.json").bufferedReader().use { it.readText() }
            indicesViewModel.loadJsonData(jsonString)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show()
        }
    }

    // format the date string from "yyyy-MM-dd HH:mm:ss" to "dd MMM yyyy HH:mm:ss"
    private fun formatDate(dateString: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val desiredFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date: Date? = originalFormat.parse(dateString)
        return if (date != null) {
            "${desiredFormat.format(date)}"
        } else {
            "Invalid Date"
        }
        }
}
