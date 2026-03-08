package com.gw.photoshare.domain.connection.fake

import com.gw.photoshare.domain.connection.Device
import com.gw.photoshare.domain.connection.DeviceConnection
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeDeviceConnection @Inject constructor() : DeviceConnection {
    override val isReadyToScan: Flow<Boolean>
        get() = flow {
            delay(1000L)
            emit(true)
        }

    override suspend fun scan(): List<Device> =
        mutableListOf(
            Device("Fake_1", "Fake_address")
        )

    override suspend fun connect(device: Device): Boolean {
        delay(1000L)
        return true
    }

    override fun disconnect() {

    }
}