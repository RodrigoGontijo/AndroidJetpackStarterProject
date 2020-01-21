package br.com.jetpackstarter.model.DogsRepository.Service

import br.com.jetpackstarter.model.DogsRepository.DogBreed
import br.com.jetpackstarter.model.DogsRepository.Api.DogsApi
import io.reactivex.Single

class DogsApiService(private val api: DogsApi) {

    fun getDogs(): Single<List<DogBreed>>{
        return api.getDogs()
    }
}