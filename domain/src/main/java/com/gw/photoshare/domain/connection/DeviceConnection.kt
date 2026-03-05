package com.gw.photoshare.domain.connection

import kotlinx.coroutines.flow.Flow

interface DeviceConnection {
    val isAvailable: Flow<Boolean>
    suspend fun scan(): List<Device>
    suspend fun connect(device: Device)
    fun disconnect()
}