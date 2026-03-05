package com.gw.photoshare.bluetooth

import android.Manifest.permission
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.RequiresPermission
import com.gw.photoshare.domain.connection.Device
import com.gw.photoshare.domain.connection.DeviceConnection
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeout
import java.util.UUID
import javax.inject.Inject

class BluetoothConnection @Inject constructor(
    @ApplicationContext private val context: Context
) : DeviceConnection {
    private val bluetoothManager: BluetoothManager =
        context.getSystemService(BluetoothManager::class.java)
    private val adapter = bluetoothManager.adapter
    private var socket: BluetoothSocket? = null

    private val _isReadyToScan: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isReadyToScan: Flow<Boolean>
        get() = _isReadyToScan.asStateFlow()

    private companion object {
        val APP_UUID: UUID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000")
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                val state = intent.getIntExtra(
                    BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR
                )
                _isReadyToScan.value == (state == BluetoothAdapter.STATE_ON)
            }
        }
    }

    @RequiresPermission(permission.BLUETOOTH_CONNECT)
    override suspend fun scan(): List<Device> {
        return coroutineScope {
            withTimeout(5_000L) {
                _isReadyToScan.first { it }
                adapter.bondedDevices?.map {
                    Device(
                        name = it.name,
                        address = it.address
                    )
                }?.toMutableList() ?: emptyList()
            }
        }
    }

    @RequiresPermission(permission.BLUETOOTH_SCAN)
    override suspend fun connect(device: Device) {
        context.registerReceiver(receiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
        val remoteDevice = adapter.getRemoteDevice(device.address)
        socket = remoteDevice.createRfcommSocketToServiceRecord(APP_UUID)
        adapter.cancelDiscovery()
        socket?.connect()
    }

    override fun disconnect() {
        socket?.close()
        socket = null
        context.unregisterReceiver(receiver)
    }
}