package com.gw.photoshare.communication

import com.gw.photoshare.domain.communication.Communication
import com.gw.photoshare.domain.connection.CommunicationFactory
import java.io.InputStream
import java.io.OutputStream

class CommunicationFactory : CommunicationFactory {
    override fun create(
        inputStream: InputStream,
        outputStream: OutputStream
    ): Communication = InOutputStreamCommunication(
        inputStream = inputStream,
        outputStream = outputStream
    )
}