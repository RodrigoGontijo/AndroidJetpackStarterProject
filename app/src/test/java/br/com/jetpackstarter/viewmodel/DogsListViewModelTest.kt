package br.com.jetpackstarter.viewmodel
import android.app.Application
import android.content.Context
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import android.view.SearchEvent
import androidx.lifecycle.Observer
import br.com.jetpackstarter.di.Modules
import br.com.jetpackstarter.model.dogsRepository.DogBreed
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.stopKoin
import org.koin.test.inject
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import br.com.jetpackstarter.model.dogsRepository.Dao.DogDao
import br.com.jetpackstarter.model.dogsRepository.Service.DogsApiService
import br.com.jetpackstarter.notification.NotificationsHelper
import br.com.jetpackstarter.util.SharedPreferencesHelper
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.jetpackstarter.CoroutineTestRule
import br.com.jetpackstarter.model.dogsRepository.DogDatabase
import br.com.jetpackstarter.runBlocking
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.mockito.Mockito
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class DogsListViewModelTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    val dogsListViewModel: DogsListViewModel by inject()

    @Mock lateinit var loadingObserver: Observer<Boolean>
    @Mock lateinit var loadingErrorObserver: Observer<Boolean>
    @Mock lateinit var dogsObserver: Observer<List<DogBreed>>


    @Mock private lateinit var context: Context

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            androidContext(context)
            modules(Modules.all)
        }


    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun testFetchFromRemote() {
        dogsListViewModel.loading.observeForever(loadingObserver)
        dogsListViewModel.dogs.observeForever(dogsObserver)

        dogsListViewModel.fetchFromRemote()

        verify(loadingObserver).onChanged(true)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test() =
        runBlocking {
                val item1 = DogBreed("oi", "oi", "oi", "oi", "oi", "oi", "oi")
                val item2 = DogBreed("oi", "oi", "oi", "oi", "oi", "oi", "oi")
                val list = arrayListOf(item1, item2)

                val listLong = arrayListOf(1L, 2L)

                dogsListViewModel.dogs.observeForever(dogsObserver)
                dogsListViewModel.storeDogsLocal(list)

                verify(dogsObserver).onChanged(list)

        }
}