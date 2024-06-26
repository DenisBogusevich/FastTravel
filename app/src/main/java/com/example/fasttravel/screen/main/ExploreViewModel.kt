package com.example.fasttravel.screen.main

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.database.model.Route
import com.app.database.repository.RouteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



class ExploreViewModel @Inject constructor(
    private val routeRepository: RouteRepository,
    //private val locationTracker: LocationTracker

) : ViewModel() {

    var currentLocation by mutableStateOf<Location?>(null)

    private val _exploreList = MutableStateFlow<List<Route>>(emptyList())
    val exploreList: StateFlow<List<Route>> = _exploreList

    private val _scrollOffset = MutableStateFlow(0f)
    val scrollOffset: StateFlow<Float> = _scrollOffset

    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: StateFlow<Boolean> = _isExpanded

    private val _selectedRoute = MutableStateFlow<Route?>(null)
    val selectedRoute: StateFlow<Route?> = _selectedRoute



    init {
        fetchExploreList()
    }



    fun toggleExpanded() {
        _isExpanded.value = !_isExpanded.value
    }

    fun selectRoute(route: Route) {
        _selectedRoute.value = route
    }


    fun updateScrollOffset(offset: Float) {
        _scrollOffset.value = offset
    }

    private fun fetchExploreList() {
        _exploreList.value = routeRepository.routes
    }
}
