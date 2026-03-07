package com.gw.bluetooth.main.ui.connect

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gw.bluetooth.main.ui.connect.model.ConnectIntent
import com.gw.bluetooth.main.ui.connect.model.ConnectUiState

@Composable
fun ConnectionScreen(
    modifier: Modifier = Modifier,
    viewModel: ConnectViewModel = hiltViewModel()
) {
    val uiState: ConnectUiState by viewModel.uiState.collectAsStateWithLifecycle()

    ConnectionScreen(
        uiState = uiState,
        onScanClicked = { viewModel.onIntent(ConnectIntent.Scan) },
        modifier = modifier
    )
}

@Composable
private fun ConnectionScreen(
    uiState: ConnectUiState,
    modifier: Modifier = Modifier,
    onScanClicked: () -> Unit = {}
) {
    Column(modifier = modifier) {
        BluetoothInfo(
            isScanReady = uiState.isScanReady,
            onScanClicked = onScanClicked
        )
        LazyColumn {
            items(items = uiState.devices) {
                Text(text = "Device Id $it")
            }
        }
    }
}

@Composable
private fun BluetoothInfo(
    isScanReady: Boolean,
    onScanClicked: () -> Unit = {}
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            onScanClicked()
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        val readyText = if (isScanReady) "Ready" else "Not Ready"
        Text("Bluetooth is $readyText")
        Spacer(modifier = Modifier.weight(1f))
        Button(
            enabled = isScanReady,
            onClick = { launcher.launch(bluetoothPermissions) }
        ) {
            Text("Search Device")
        }
    }
}

private val bluetoothPermissions: Array<String>
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN
        )
    } else {
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }
