package com.app.database.model

import androidx.compose.runtime.Immutable

@Immutable
data class Route(
    val id: Int,
    val name: String,
    val description: String,
    val city: String,
    val country: String,
    val points: List<Point>,
    val imageUrl: String,
)

@Immutable
data class Point(
    val name: String,
    val description: String,
    val imageUrl: String,
    val rate : Int,
    val latitude: String,
    val longitude: String
)
