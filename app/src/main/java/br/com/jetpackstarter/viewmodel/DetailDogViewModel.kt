package br.com.jetpackstarter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jetpackstarter.model.DogsRepository.DogBreed

class DetailDogViewModel : ViewModel() {
    val dogLiveData = MutableLiveData<DogBreed>()


    fun fetch() {
        val dogOne = DogBreed(
            "1",
            "teste",
            "7 years",
            "breedGroup",
            "bredFor",
            "temperamente",
            ""
        )
        dogLiveData.value = dogOne

    }

}