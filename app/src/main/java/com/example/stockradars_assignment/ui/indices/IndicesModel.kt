package com.example.stockradars_assignment.ui.indices

data class IndicesModel(
    val lastUpdate: String,
    val dataList: List<DataModel>
)

data class DataModel(
    val short_name: String,
    val price: Double,
    val change: Double,
    val percent_change: Double
)
