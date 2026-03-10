package com.gw.photoshare.domain.connection.fake

import com.gw.photoshare.domain.communication.Communication
import com.gw.photoshare.domain.connection.Device
import com.gw.photoshare.domain.connection.DeviceConnection
import com.gw.photoshare.domain.connection.CommunicationFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class FakeDeviceConnection @Inject constructor(
    private val communicationFactory: CommunicationFactory
) : DeviceConnection {
    private var isConnected = false
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

    override fun createCommunication(): Communication {
        check(isConnected)
        return communicationFactory.create(
            ByteArrayInputStream(byteArrayOf(1, 2, 3)),
            ByteArrayOutputStream()
        )
    }
}