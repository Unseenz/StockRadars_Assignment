package com.example.stockradars_assignment.ui.portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONArray

class PortfolioViewModel : ViewModel() {

    // LiveData to hold the list of portfolio items
    private val _portfolioData = MutableLiveData<List<PortfolioModel>>()
    val portfolioData: LiveData<List<PortfolioModel>> get() = _portfolioData

    // Function to load and parse JSON data
    fun loadJsonData(jsonString: String) {
        val portfolioList = mutableListOf<PortfolioModel>()

        try {
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val itemObject = jsonArray.getJSONObject(i)

                // Extract individual fields from the JSON object
                val title = itemObject.getString("title")
                val pendingPoint = itemObject.getDouble("pending_point")
                val withdrawablePoint = itemObject.getDouble("withdrawable_point")
                val change = itemObject.getDouble("change")
                val imagePlan = itemObject.getString("image_plan")
                val portModel = PortfolioModel(title, pendingPoint, withdrawablePoint, change, imagePlan)

                // Add to list
                portfolioList.add(portModel)
            }

            // Post the parsed data to LiveData
            _portfolioData.postValue(portfolioList)

        } catch (jsonException: Exception) {
            jsonException.printStackTrace()
        }
    }
}