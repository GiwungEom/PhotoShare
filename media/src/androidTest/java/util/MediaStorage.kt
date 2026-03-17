package util

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import androidx.test.core.app.ApplicationProvider

class MediaStorage {
    companion object {
        private const val PATH: String = "DCIM/Test/"
    }

    fun saveMediaFiles() {
        runInContext {
            val value = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "images.jpeg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, PATH)
            }
            val uri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value
            ) ?: throw IllegalStateException("not found uri")

            contentResolver.openOutputStream(uri)?.use { outputStream ->
                assets.open("images.jpeg").copyTo(outputStream)
            }
        }
    }

    fun isMediaExists(): Boolean {
        return runInContext {
            val cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DISPLAY_NAME
                ),
                "${MediaStore.Images.Media.RELATIVE_PATH} = ?",
                arrayOf(PATH),
                "${MediaStore.Images.Media.DATE_ADDED} DESC"
            )

            cursor?.let {
                CursorPrinter().print(it)
                cursor.count > 0
            } ?: false
        }
    }

    fun deleteAllMediaFile() {
        runInContext {
            contentResolver.delete(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "${MediaStore.Images.Media.RELATIVE_PATH} = ?",
                arrayOf(PATH)
            )
        }
    }

    private fun <T> runInContext(function: Context.() -> T): T {
        return ApplicationProvider.getApplicationContext<Context>().run {
            function()
        }
    }
}

private class CursorPrinter {
    fun print(cursor: Cursor) {
        val id = cursor.getColumnIndex(MediaStore.Images.Media._ID)
        val displayName = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

        println(
            buildString {
                while (cursor.moveToNext()) {
                    appendLine("ID : ${cursor.getLong(id)}")
                    appendLine("DisplayNAme : ${cursor.getString(displayName)}")
                }
            }
        )
    }
}
