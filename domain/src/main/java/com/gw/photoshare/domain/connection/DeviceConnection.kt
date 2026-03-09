package com.gw.photoshare.domain.connection

import com.gw.photoshare.domain.communication.Communication
import kotlinx.coroutines.flow.Flow

interface DeviceConnection {
    val isReadyToScan: Flow<Boolean>
    suspend fun scan(): List<Device>
    suspend fun connect(device: Device): Boolean
    fun disconnect()
    fun createCommunication(): Communication
}