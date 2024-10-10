package com.example.stockradars_assignment.ui.portfolio

data class PortfolioModel(
    val title: String,
    val pending_point: Double,
    val withdrawable_point: Double,
    val change: Double,
    val image_plan: String
)
