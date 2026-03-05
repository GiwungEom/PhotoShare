package com.gw.photoshare.connection

import com.gw.photoshare.domain.connection.Device
import com.gw.photoshare.domain.connection.DeviceConnection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class FakeConnection @Inject constructor() : DeviceConnection {
    override suspend fun connect(device: Device) {
        println("start connect")
    }

    override fun disconnect() {
        println("disconnected")
    }

    override suspend fun scan(): List<Device> {
        return listOf(
            Device("Device-1", "00000"),
            Device("Device-2", "00000")
        )
    }

    override val isReadyToScan: Flow<Boolean>
        get() = emptyFlow()
}