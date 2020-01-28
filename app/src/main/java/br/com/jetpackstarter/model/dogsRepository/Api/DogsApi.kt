package br.com.jetpackstarter.model.dogsRepository.Api

import br.com.jetpackstarter.model.dogsRepository.DogBreed
import io.reactivex.Single
import retrofit2.http.GET

interface DogsApi {
    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogs(): Single<List<DogBreed>>
}