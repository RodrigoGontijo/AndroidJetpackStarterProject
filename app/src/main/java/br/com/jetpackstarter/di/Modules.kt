package br.com.jetpackstarter.di


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import br.com.jetpackstarter.model.dogsRepository.Api.DogsApi
import br.com.jetpackstarter.model.dogsRepository.Dao.DogDao
import br.com.jetpackstarter.model.dogsRepository.DogDatabase
import br.com.jetpackstarter.DogsConstants.Companion.BASE_URL
import br.com.jetpackstarter.model.dogsRepository.Service.DogsApiService
import br.com.jetpackstarter.viewmodel.DetailDogViewModel
import br.com.jetpackstarter.viewmodel.DogsListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Modules{

    private const val PREF_TIME = "PrefsTime"

    private fun provideTimePreferences(app: Application): SharedPreferences =
        app.getSharedPreferences(PREF_TIME, Context.MODE_PRIVATE)

    private val viewModelModule = module {
        viewModel { DogsListViewModel(get(), get() as DogDao, get(named("timePrefs"))) }
        viewModel { DetailDogViewModel(get() as DogDao) }
    }

    private val DogsApiModule = module {
        single {
            val retrofit: Retrofit = get()
            retrofit.create(DogsApi::class.java)
        }

        single {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }

    private val repositoryModule = module {
        single { DogsApiService(get()) }
    }

    private val dbModule = module {

        single {
            Room.databaseBuilder(androidContext(), DogDatabase::class.java,
                "dogdatabase").build()
        }

        single { get<DogDatabase>().dogDao }

    }

    val preferencesModule = module {
        single(named("timePrefs")) { provideTimePreferences(androidApplication()) }
    }



    val all: List<Module> = listOf(
        viewModelModule,
        DogsApiModule,
        repositoryModule,
        dbModule,
        preferencesModule
    )

}