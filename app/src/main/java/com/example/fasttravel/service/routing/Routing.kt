package com.example.fasttravel.service.routing

import com.app.database.model.Point
import com.app.database.model.Route
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.TravelMode
import com.google.android.gms.maps.model.LatLng as GmsLatLng
import com.google.maps.model.LatLng as MapsLatLng

fun getRoute(points: List<Point>): List<GmsLatLng> {

    val context = GeoApiContext.Builder()
        .apiKey()
        .build()

    val origin = points.first()
    val destination = points.last()

    val directionsResult = DirectionsApi.newRequest(context)
        .origin(origin.toMapsLatLng())
        .waypoints(*points.subList(1, points.size - 1)
            .map { it.toMapsLatLng() }.toTypedArray())
        .destination(destination.toMapsLatLng())
        .mode(TravelMode.WALKING)
        .await()

    return directionsResult.routes[0].overviewPolyline.decodePath().map { it.toGmsLatLng() }
}


fun Point.toMapsLatLng() = MapsLatLng(this.latitude.toDouble(), this.longitude.toDouble())

private fun MapsLatLng.toGmsLatLng() = GmsLatLng(this.lat, this.lng)