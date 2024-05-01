package com.app.database.repository

import com.app.database.model.Route
import com.app.database.testdatasource.RouteLocalDataSource
import javax.inject.Inject

class RouteRepository @Inject constructor(
    private val routeLocalDataSource: RouteLocalDataSource
) {
    val routes: List<Route> = routeLocalDataSource.routes

    fun getRoute(routeName: Route): Route? {
        return routeLocalDataSource.routes.firstOrNull {
            it.name == routeName.name
        }
    }
}