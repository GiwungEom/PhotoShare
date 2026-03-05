package com.gw.bluetooth.main.ui.connect.model

data class ConnectUiState(
    val isScanReady: Boolean,
    val devices: List<Device>
) {
    companion object {
        val EMPTY = ConnectUiState(
            isScanReady = false,
            devices = emptyList()
        )
    }
}
