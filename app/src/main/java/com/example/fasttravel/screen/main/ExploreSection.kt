package com.example.fasttravel.screen.main


import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest.Builder
import com.app.database.model.Route
import com.example.fasttravel.service.routing.getRoute
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun ExploreSection(
    viewModel: ExploreViewModel,
) {
    val isExpanded by viewModel.isExpanded.collectAsState()
    val selectedRoute by viewModel.selectedRoute.collectAsState()
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 1f)
    }

    Box(modifier = Modifier.fillMaxSize()) {


        MapScreen(
            viewModel = viewModel,
        )

        if (!isExpanded) {
            ExploreList(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                viewModel = viewModel
            )
        } else {
            FullScreenContent(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                item = selectedRoute!!,
                viewModel = viewModel
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(viewModel: ExploreViewModel) {
    val selectedRoute by viewModel.selectedRoute.collectAsState()
    val singapore = LatLng(1.35, 103.87)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 1f)
    }







    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {


        selectedRoute?.let { route ->
            val startPoint = LatLng(route.points[0].latitude.toDouble(), route.points[0].longitude.toDouble())
            cameraPositionState.position = CameraPosition.fromLatLngZoom(startPoint, 10f)


            val routePoints = getRoute(route.points)

            route.points.forEach { point ->
                val pointPosition = LatLng(point.latitude.toDouble(), point.longitude.toDouble())
                Polyline(points = routePoints, color = Color.Blue, width = 5f)
                Marker(
                    state = MarkerState(position = pointPosition),
                    title = point.name,
                    snippet = point.description,
                    draggable = false
                )
            }
        }
    }
}
/*
@Composable
fun MapScreen(
    viewModel: ExploreViewModel,
) {
    val selectedRoute by viewModel.selectedRoute.collectAsState()
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 1f)
    }



    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,

        ) {


        if (selectedRoute != null) {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                LatLng(
                    selectedRoute!!.points[0].latitude.toDouble(),
                    selectedRoute!!.points[0].longitude.toDouble()
                ), 10f
            )

            val routePoints = getRoute(
                LatLng(selectedRoute!!.points[1].latitude.toDouble(), selectedRoute!!.points[1].longitude.toDouble()),

                LatLng(selectedRoute!!.points[0].latitude.toDouble(), selectedRoute!!.points[0].longitude.toDouble())
            )

            selectedRoute!!.points.forEach { point ->



              Polyline(points = routePoints,
                  color = Color.Blue,
                  width = 5f)

                Marker(
                    state = MarkerState(
                        position = LatLng(point.latitude.toDouble(), point.longitude.toDouble())
                    ),
                    title = point.name,
                    snippet = point.description,
                    draggable = false
                )

        }
    }
    }
}*/


@Composable
fun ExploreList(
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel,

    ) {
    val exploreList by viewModel.exploreList.collectAsStateWithLifecycle()


    LazyRow(
        modifier = modifier


    ) {

        items(exploreList) { route ->
            Spacer(modifier = Modifier.width(24.dp))


            ExploreItem(

                modifier = Modifier,
                viewModel = viewModel,


                item = route

            )
        }
    }
}


@Composable
fun ExploreItem(
    modifier: Modifier = Modifier,
    item: Route,
    viewModel: ExploreViewModel
) {

    val scrollOffset by viewModel.scrollOffset.collectAsState()
    // val selectedRoute by viewModel.selectedRoute.collectAsState()
    Surface(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier

            .height(400.dp - scrollOffset.dp)

            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { delta: Float ->
                    viewModel.updateScrollOffset((scrollOffset + delta).coerceIn(0f, 360f))
                    delta
                }
            )
            //.width(250.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        viewModel.selectRoute(item)
                        // Set isExpanded to true when the item is pressed
                        // viewModel.toggleExpanded()
                    },
                    onLongPress = {
                        // Handle long-press event
                        viewModel.toggleExpanded()
                        viewModel.selectRoute(item)
                    },

                    )
            }
    ) {
        RegularContent(modifier = modifier, viewModel = viewModel, item = item)
    }
}

@Composable
fun RegularContent(
    modifier: Modifier = Modifier,
    item: Route,
    viewModel: ExploreViewModel,
) {
    val scrollState = rememberScrollState()
    val scrollOffset by viewModel.scrollOffset.collectAsState()

    Column(
        modifier = modifier

            .width(250.dp)
            .let { if (viewModel.scrollOffset.value <= 270) it.verticalScroll(scrollState) else it }

    ) {
        Surface(Modifier.size(width = 250.dp, height = 180.dp)) {
            Box {
                val painter = rememberAsyncImagePainter(
                    model = Builder(LocalContext.current)
                        .data(item.imageUrl)
                        .crossfade(true)
                        .build()
                )
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )

            }
        }
        Column() {
            Text(text = item.name)
            Text(text = item.city)
            Text(text = item.country)
            Text(text = item.toString())
        }
    }
}

@Composable
fun FullScreenContent(
    modifier: Modifier,
    item: Route,
    viewModel: ExploreViewModel,
) {
    Column(
        // modifier = modifier.fillMaxSize()

    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        {
            Box {
                val painter = rememberAsyncImagePainter(
                    model = Builder(LocalContext.current)
                        .data(item.imageUrl)
                        .crossfade(true)
                        .build()
                )
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )

            }
        }
        Column() {
            Text(text = item.name)
            Text(text = item.city)
            Text(text = item.country)
            Text(text = item.toString())
        }
    }
}









