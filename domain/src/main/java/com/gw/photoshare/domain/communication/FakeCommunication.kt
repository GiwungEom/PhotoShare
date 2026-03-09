package com.gw.photoshare.domain.communication

import java.io.InputStream
import java.io.OutputStream

class FakeCommunication(
    private val inputStream: InputStream,
    private val outputStream: OutputStream
) : Communication {
    override suspend fun send(message: Message) {
        TODO("Not yet implemented")
    }

    override suspend fun receive(): Message {
        TODO("Not yet implemented")
    }
}