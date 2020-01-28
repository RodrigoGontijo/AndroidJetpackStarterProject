package br.com.jetpackstarter.model.dogsRepository.Service

import br.com.jetpackstarter.model.dogsRepository.DogBreed
import br.com.jetpackstarter.model.dogsRepository.Api.DogsApi
import io.reactivex.Single

class DogsApiService(private val api: DogsApi) {

    fun getDogs(): Single<List<DogBreed>>{
        return api.getDogs()
    }
}