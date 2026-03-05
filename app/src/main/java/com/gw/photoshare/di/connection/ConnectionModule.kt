package com.gw.photoshare.di.connection

import com.gw.photoshare.bluetooth.BluetoothConnection
import com.gw.photoshare.domain.connection.DeviceConnection
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectionModule {
    @Binds
    abstract fun bindConnection(connection: BluetoothConnection): DeviceConnection
}