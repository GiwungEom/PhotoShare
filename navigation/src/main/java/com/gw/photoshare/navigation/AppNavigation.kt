package com.gw.photoshare.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.gw.photoshare.navigation.route.ConnectionRoute
import com.gw.photoshare.navigation.route.connectionScreen

@Composable
fun AppNavigation(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ConnectionRoute
    ) {
        connectionScreen(onDeviceClick = { })
    }
}
