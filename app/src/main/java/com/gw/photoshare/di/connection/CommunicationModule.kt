package com.gw.photoshare.di.connection

import com.gw.photoshare.communication.CommunicationFactory
import com.gw.photoshare.domain.connection.CommunicationFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CommunicationModule {
    @Binds
    abstract fun bindCommunicationFactory(factory: CommunicationFactory): com.gw.photoshare.domain.connection.CommunicationFactory
}