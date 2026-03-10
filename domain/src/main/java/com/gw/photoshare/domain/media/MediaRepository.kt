package com.gw.photoshare.domain.media

interface MediaRepository {
    fun getMedia(): List<String>
}