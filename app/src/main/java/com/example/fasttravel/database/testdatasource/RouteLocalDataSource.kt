package com.app.database.testdatasource

import com.app.database.model.Route
import javax.inject.Inject

private const val DEFAULT_IMAGE_WIDTH = "250"


class RouteLocalDataSource @Inject constructor() {
    val routes = listOf(
        Route(
            id = 1,
            name = "Route 1",
            description = "Description of route 1",
            city = "City1",
            country = "Country1",
            points = listOf(point1, point2, point3),
            imageUrl = "https://images.unsplash.com/photo-1544735716-392fe2489ffa?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=$DEFAULT_IMAGE_WIDTH"
        ),
        Route(
            id = 2,
            name = "Route 2",
            description = "Description of route 2",
            city = "City2",
            country = "Country2",
            points = listOf(point4, point5, point6),
            imageUrl = "https://images.unsplash.com/photo-1539037116277-4db20889f2d4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=$DEFAULT_IMAGE_WIDTH"
        ),
        Route(
            id = 3,
            name = "Route 3",
            description = "Description of route 3",
            city = "City3",
            country = "Country3",
            points = listOf(point7, point1, point2),
            imageUrl = "https://images.unsplash.com/photo-1518548419970-58e3b4079ab2?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=$DEFAULT_IMAGE_WIDTH"
        ),
    )
}