package com.gw.photoshare.di.connection

import com.gw.photoshare.domain.connection.DeviceConnection
import com.gw.photoshare.domain.connection.fake.FakeDeviceConnection
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectionModule {
    @Binds
    abstract fun bindConnection(connection: FakeDeviceConnection): DeviceConnection
}