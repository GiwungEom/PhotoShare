import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gw.photoshare.domain.media.MediaRepository
import com.gw.photoshare.media.repository.LocalStorageMediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import util.MediaStorage

@RunWith(AndroidJUnit4::class)
class LocalStorageMediaRepositoryTest {

    private lateinit var mediaStorage: MediaStorage
    private lateinit var underTheTest: MediaRepository

    @Before
    fun setUp() {
        prepareMedia()

        underTheTest = LocalStorageMediaRepository(
            ApplicationProvider.getApplicationContext(),
            Dispatchers.IO
        )
    }

    @After
    fun cleanUp() {
        mediaStorage.deleteAllMediaFile()
    }

    @Test
    fun when_getMedia() = runTest {
        assertTrue(underTheTest.getMedia().isNotEmpty())
    }

    private fun prepareMedia() {
        mediaStorage = MediaStorage()
        mediaStorage.saveMediaFiles()
    }
}