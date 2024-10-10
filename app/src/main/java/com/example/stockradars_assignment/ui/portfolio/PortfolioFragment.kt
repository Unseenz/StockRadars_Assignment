package com.example.stockradars_assignment.ui.portfolio

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stockradars_assignment.R
import java.io.IOException

class PortfolioFragment : Fragment() {

    private lateinit var recyclerViewPortfolio: RecyclerView
    private lateinit var portfolioAdapter: PortfolioAdapter
    private lateinit var portfolioViewModel: PortfolioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_portfolio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Change the status bar color to match the portfolio's theme
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.portfolio_bar)

        // Initialize ViewModel
        portfolioViewModel = ViewModelProvider(this).get(PortfolioViewModel::class.java)

        // Set up the RecyclerView for portfolio
        recyclerViewPortfolio = view.findViewById(R.id.portfolioRecyclerView)
        recyclerViewPortfolio.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewPortfolio.setHasFixedSize(true)

        portfolioAdapter = PortfolioAdapter(emptyList())
        recyclerViewPortfolio.adapter = portfolioAdapter

        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        recyclerViewPortfolio.addItemDecoration(dividerItemDecoration)

        // Observe data from ViewModel
        portfolioViewModel.portfolioData.observe(viewLifecycleOwner) { portfolioItems ->
            portfolioAdapter.updateData(portfolioItems)
        }
        loadJsonData()
    }

    // Loads JSON data from 'portfolio.json'
    private fun loadJsonData() {
        val jsonString: String
        try {
            val context: Context = requireContext()
            jsonString =
                context.assets.open("portfolio.json").bufferedReader().use { it.readText() }
            portfolioViewModel.loadJsonData(jsonString)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show()
        }

    }

    // Reset the status bar color to black when leave portfolio fragment
    override fun onDestroyView() {
        super.onDestroyView()
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.black)
    }
}