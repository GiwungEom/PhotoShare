package com.gw.photoshare.communication

import com.gw.photoshare.domain.communication.Communication
import com.gw.photoshare.domain.communication.Message
import kotlinx.serialization.json.Json
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class InOutputStreamCommunication @Inject constructor(
    private val inputStream: InputStream,
    private val outputStream: OutputStream
) : Communication {

    override suspend fun send(message: Message) {
        val encodedMessage = Json.encodeToString(Message.serializer(), message)
        val output = DataOutputStream(outputStream)
        output.writeInt(encodedMessage.length)
        output.write(encodedMessage.toByteArray())
        output.flush()
    }

    override suspend fun receive(): Message {
        val input = DataInputStream(inputStream)
        val length = input.readInt()
        val message = ByteArray(length)
        input.readFully(message)
        return Json.decodeFromString(
            Message.serializer(),
            String(message)
        )
    }
}