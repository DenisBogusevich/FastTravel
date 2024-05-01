package com.example.fasttravel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.database.repository.RouteRepository
import com.app.database.testdatasource.RouteLocalDataSource
import com.example.fasttravel.screen.main.ExploreSection
import com.example.fasttravel.screen.main.ExploreViewModel
import com.example.fasttravel.ui.theme.FastTravelTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val routeRepository = RouteRepository(RouteLocalDataSource())
        val routes = routeRepository.routes
        setContent {
            FastTravelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),

                    ) {
                    ExploreSection(viewModel = ExploreViewModel(routeRepository))

                }
            }
        }
    }
}


