package com.gw.photoshare.domain.media

data class Media(
    val id: Long,
    val name: String,
    val size: Long,
    val path: String
)