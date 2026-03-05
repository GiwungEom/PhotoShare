package com.gw.bluetooth.main.ui.connect.model

import com.gw.photoshare.domain.connection.Device as DomainDevice

data class Device(val id: String, val name: String) {
    companion object {
        internal fun toUi(device: DomainDevice): Device =
            Device(
                id = device.address,
                name = device.name
            )
    }
}

internal fun Device.toDomain(): DomainDevice =
    DomainDevice(
        name = name,
        address = id
    )