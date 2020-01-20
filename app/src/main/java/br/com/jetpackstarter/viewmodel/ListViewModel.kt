package br.com.jetpackstarter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jetpackstarter.model.DogBreed

class ListViewModel : ViewModel() {

    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        val dogOne = DogBreed("1", "teste", "7 years", "breedGroup", "bredFor", "temperamente","")
        val dogtwo = DogBreed("2", "dsds", "11 years", "breedGroup", "bredFor", "temperamente","")
        val dogthree = DogBreed("3", "eweew", "6 years", "breedGroup", "bredFor", "temperamente","")
        val dogList = arrayListOf<DogBreed>(dogOne,dogtwo, dogthree)

        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }
}