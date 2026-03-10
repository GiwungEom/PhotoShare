package com.gw.photoshare.domain.connection

import com.gw.photoshare.domain.communication.Communication
import java.io.InputStream
import java.io.OutputStream

interface CommunicationFactory {
    fun create(inputStream: InputStream, outputSet: OutputStream): Communication
}