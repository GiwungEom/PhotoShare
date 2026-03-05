package com.gw.bluetooth.main.ui.connect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gw.bluetooth.main.ui.connect.model.ConnectIntent
import com.gw.bluetooth.main.ui.connect.model.ConnectUiState
import com.gw.bluetooth.main.ui.connect.model.Device
import com.gw.bluetooth.main.ui.connect.model.toDomain
import com.gw.photoshare.domain.connection.DeviceConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectViewModel @Inject constructor(
    private val deviceConnection: DeviceConnection
) : ViewModel() {
    private val _uiState: MutableStateFlow<ConnectUiState> = MutableStateFlow(ConnectUiState.EMPTY)
    val uiState: StateFlow<ConnectUiState> = _uiState.asStateFlow()

    private val channel: Channel<ConnectIntent> = Channel(capacity = Channel.UNLIMITED)

    init {
        receiveIntent()
        observeConnectionState()
    }

    fun onIntent(intent: ConnectIntent) {
        viewModelScope.launch {
            channel.send(intent)
        }
    }

    private fun receiveIntent() {
        viewModelScope.launch {
            for (intent in channel) {
                when (intent) {
                    is ConnectIntent.Scan -> scanDevices()
                }
            }
        }
    }


    private fun observeConnectionState() {
        viewModelScope.launch {
            deviceConnection.isReadyToScan
                .onEach { isReady -> _uiState.update { it.copy(isScanReady = isReady) } }
                .collect()
        }
    }

    private fun scanDevices() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    devices = deviceConnection.scan().map(Device::toUi)
                )
            }
        }
    }

    private fun onConnectionCalled(device: Device) {
        viewModelScope.launch {
            deviceConnection.connect(device.toDomain())
        }
    }

    private fun onDisconnectionCalled() {
        deviceConnection.disconnect()
    }

    private fun onErrorMessageFetched() {

    }
}