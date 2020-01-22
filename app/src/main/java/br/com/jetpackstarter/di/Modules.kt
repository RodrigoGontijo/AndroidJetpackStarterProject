package br.com.jetpackstarter.di


import br.com.jetpackstarter.model.DogsRepository.Api.DogsApi
import br.com.jetpackstarter.model.DogsRepository.DogsConstants.Companion.BASE_URL
import br.com.jetpackstarter.model.DogsRepository.Service.DogsApiService
import br.com.jetpackstarter.viewmodel.DetailDogViewModel
import br.com.jetpackstarter.viewmodel.DogsListViewModel
import org.koin.android.ext.koin.androidApplication

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Modules{

    private val viewModelModule = module {
        viewModel { DogsListViewModel(get(), androidApplication()) }
        viewModel { DetailDogViewModel() }
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

    val all: List<Module> = listOf(
        viewModelModule,
        DogsApiModule,
        repositoryModule
    )

}