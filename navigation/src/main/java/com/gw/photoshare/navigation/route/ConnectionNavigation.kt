package com.gw.photoshare.navigation.route

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gw.bluetooth.main.ui.connect.ConnectionScreen
import kotlinx.serialization.Serializable

@Serializable
object ConnectionRoute

fun NavGraphBuilder.connectionScreen() {
    composable<ConnectionRoute> {
        ConnectionScreen()
    }
}
