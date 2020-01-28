package br.com.jetpackstarter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.jetpackstarter.model.dogsRepository.Dao.DogDao
import br.com.jetpackstarter.model.dogsRepository.DogBreed
import kotlinx.coroutines.launch

class DetailDogViewModel(
    private val dogDao: DogDao
    ) : BaseViewModel() {

    val dogLiveData = MutableLiveData<DogBreed>()


    fun fetch(dogId: Int) {
        viewModelScope.launch {
            val dog = dogDao.getDog(dogId)
            dogLiveData.value = dog
        }
    }

}