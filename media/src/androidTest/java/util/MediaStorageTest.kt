package util

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MediaStorageTest {

    private val underTheTest: MediaStorage = MediaStorage()

    @Test
    fun when_call_saveFiles_then_file_should_be_exists() {
        underTheTest.saveMediaFiles()
        assertTrue(underTheTest.isMediaExists())
    }

    @Test
    fun when_call_deleteFiles_then_file_should_not_be_exists() {
        underTheTest.saveMediaFiles()
        underTheTest.deleteAllMediaFile()
        assertFalse(underTheTest.isMediaExists())
    }
}