import com.crosemont.booklique.Présentation.Accueil.Modèle
import com.crosemont.booklique.Présentation.Accueil.Présentateur
import com.crosemont.booklique.Présentation.Accueil.Vue
import com.crosemont.booklique.domaine.mork_data.Data
import io.mockk.*
import kotlinx.coroutines.test.*
import kotlin.test.*
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import java.util.Date

class PrésentateurTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

}
