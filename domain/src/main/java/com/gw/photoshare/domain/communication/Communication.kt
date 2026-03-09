package com.gw.photoshare.domain.communication

interface Communication {
    suspend fun send(message: Message)
    suspend fun receive(): Message
}
