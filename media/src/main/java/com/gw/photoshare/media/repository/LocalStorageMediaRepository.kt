package com.gw.photoshare.media.repository

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.gw.photoshare.domain.di.IoDispatcher
import com.gw.photoshare.domain.media.Media
import com.gw.photoshare.domain.media.MediaRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalStorageMediaRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MediaRepository {

    private val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    private val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.SIZE,
        MediaStore.Images.Media.DATE_ADDED
    )
    private val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

    override suspend fun getMedia(): List<Media> =
        withContext(dispatcher) {
            val mediaList = mutableListOf<Media>()
            val contentResolver = context.contentResolver

            contentResolver.query(uri, projection, null, null, sortOrder)?.use { cursor ->
                with(cursor) {
                    val idColumn = getIdColumnIndex()
                    val nameColumn = getNameColumnIndex()
                    val sizeColumn = getSizeColumnIndex()

                    while (moveToNext()) {
                        val id = getLong(idColumn)
                        val name = getString(nameColumn)
                        val size = getLong(sizeColumn)

                        val contentUri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
                        )
                        mediaList.add(Media(id, name, size, contentUri.toString()))
                    }
                }
            }
            mediaList
        }

    private fun Cursor.getIdColumnIndex(): Int =
        getColumnIndexOrThrow(MediaStore.Images.Media._ID)

    private fun Cursor.getNameColumnIndex(): Int =
        getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

    private fun Cursor.getSizeColumnIndex(): Int =
        getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
}
