package com.example.stockradars_assignment.ui.indices

// IndicesViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONException
import org.json.JSONObject

class IndicesViewModel : ViewModel() {

    private val _indicesData = MutableLiveData<IndicesModel>()
    val indicesData: LiveData<IndicesModel> get() = _indicesData

    // Function to load and parse JSON data
    fun loadJsonData(jsonString: String) {
        try {
            val jsonObject = JSONObject(jsonString)

            val lastUpdate = jsonObject.getString("last_update")
            val jsonArray = jsonObject.getJSONArray("data")
            val dataList = mutableListOf<DataModel>()

            for (i in 0 until jsonArray.length()) {
                val itemObject = jsonArray.getJSONObject(i)

                // Extract individual fields from the JSON object
                val shortName = itemObject.getString("short_name")
                val price = itemObject.getDouble("price")
                val change = itemObject.getDouble("change")
                val percentChange = itemObject.getDouble("percent_change")
                val dataModel = DataModel(shortName, price, change, percentChange)

                // Add the data model to the list
                dataList.add(dataModel)
            }
            val indicesModel = IndicesModel(lastUpdate, dataList)

            _indicesData.postValue(indicesModel)
        } catch (jsonException: JSONException) {
            jsonException.printStackTrace()
        }
    }
}
