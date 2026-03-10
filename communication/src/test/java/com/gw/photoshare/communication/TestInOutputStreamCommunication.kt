package com.gw.photoshare.communication

import com.gw.photoshare.domain.communication.Communication
import com.gw.photoshare.domain.communication.Message
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.PipedInputStream
import java.io.PipedOutputStream

class TestInOutputStreamCommunication {

    private lateinit var underTheTest: Communication
    private lateinit var outputStream: PipedOutputStream
    private lateinit var inputStream: PipedInputStream

    @Before
    fun setUp() {
        outputStream = PipedOutputStream()
        inputStream = PipedInputStream(outputStream)

        underTheTest = InOutputStreamCommunication(
            inputStream = inputStream,
            outputStream = outputStream
        )
    }

    @Test
    fun `when send message then should receive the same`() = runTest {
        val currentTimeInMillis = System.currentTimeMillis()
        val expected: Message = Message.FileListShare(currentTimeInMillis, currentTimeInMillis)

        underTheTest.send(expected)
        val actual = underTheTest.receive()

        assertEquals(expected, actual)
    }
}