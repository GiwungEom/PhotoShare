package com.gw.bluetooth.main.ui.connect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gw.photoshare.domain.connection.DeviceConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ConnectUiState(val devices: List<String>) {
    companion object {
        val EMPTY = ConnectUiState(devices = emptyList())
    }
}

sealed interface ConnectIntent {
    object Scan : ConnectIntent
}

@HiltViewModel
class ConnectViewModel @Inject constructor(
    private val deviceConnection: DeviceConnection
) : ViewModel() {
    private val _uiState: MutableStateFlow<ConnectUiState> = MutableStateFlow(ConnectUiState.EMPTY)
    val uiState: StateFlow<ConnectUiState> = _uiState.asStateFlow()

    private val channel: Channel<ConnectIntent> = Channel(capacity = Channel.UNLIMITED)

    init {
        viewModelScope.launch {
            for (intent in channel) {
                when (intent) {
                    is ConnectIntent.Scan -> scanDevices()
                }
            }
        }
    }

    fun onIntent(intent: ConnectIntent) {
        viewModelScope.launch {
            channel.send(intent)
        }
    }

    private fun scanDevices() {
        viewModelScope.launch {
            val devices = deviceConnection.scan()
            _uiState.update { it.copy(devices = devices) }
        }
    }

    private fun onConnectionCalled(deviceId: String) {
        viewModelScope.launch {
            deviceConnection.connect(deviceId)
        }
    }

    private fun onDisconnectionCalled() {
        deviceConnection.disconnect()
    }

    private fun onErrorMessageFetched() {

    }
}