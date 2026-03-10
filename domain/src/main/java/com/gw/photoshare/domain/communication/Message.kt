package com.gw.photoshare.domain.communication

import kotlinx.serialization.Serializable

@Serializable
sealed class Message {
    @Serializable
    data class FileListShare(val fromTimeMillis: Long, val toTimeMillis: Long) : Message()
}
