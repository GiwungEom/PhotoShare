package com.gw.photoshare.domain.media


interface MediaRepository {
    suspend fun getMedia(): List<Media>
}