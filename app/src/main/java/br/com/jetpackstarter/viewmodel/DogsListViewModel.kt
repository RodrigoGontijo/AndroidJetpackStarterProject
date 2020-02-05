package br.com.jetpackstarter.viewmodel

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.jetpackstarter.DogsConstants.Companion.PREFS_TIME
import br.com.jetpackstarter.R
import br.com.jetpackstarter.model.dogsRepository.Dao.DogDao
import br.com.jetpackstarter.model.dogsRepository.DogBreed
import br.com.jetpackstarter.model.dogsRepository.Service.DogsApiService
import br.com.jetpackstarter.notification.NotificationsHelper
import br.com.jetpackstarter.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class DogsListViewModel(private val dogsService: DogsApiService,
                        private val dogDao: DogDao,
                        private val sharedPreferencesHelper: SharedPreferencesHelper,
                        private val notificationsHelper: NotificationsHelper
) : BaseViewModel(){

    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    fun refresh() {
        sharedPreferencesHelper.initTimeSharedPreferences()
        checkCacheDuration()
        val updatedTime = sharedPreferencesHelper.getUpdateTimeDuration()
        if(updatedTime != null && updatedTime !=0L && System.nanoTime() - updatedTime < refreshTime){
            fetchFromDatabase()
        }else{
            fetchFromRemote()
        }
    }

    fun refreshBypassCache(){
        fetchFromRemote()
    }

    private fun fetchFromDatabase(){
        viewModelScope.launch {
            val dogs = dogDao.getAllDogs()
            dogsRetrieved(dogs)
        }
    }

    fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            dogsService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<DogBreed>>(){
                    override fun onSuccess(dogsList: List<DogBreed>) {
                        storeDogsLocal(dogsList)
                        notificationsHelper.createNotification()
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    suspend fun dogsRetrieved(dogsList: List<DogBreed>) {
        viewModelScope.launch {
            dogDao.getAllDogs()
            val result = dogDao.getAllDogs()
            dogs.value = dogsList
            dogsLoadError.value = false
            loading.value = false
        }
    }

    private fun storeDogsLocal(list: List<DogBreed>){
        viewModelScope.launch {
            dogDao.deleteAllDogs()
            val result = dogDao.insertAll(*list.toTypedArray())
            var i = 0
            while(i<list.size){
                list[i].uuid = result[i].toInt()
                i++
            }
            dogsRetrieved(list)
        }

        sharedPreferencesHelper.setUpdateTime()
    }

    private fun checkCacheDuration(){
        val cacheTime  = sharedPreferencesHelper.getCacheTimeDuration()

        try{
            val cachePreferencesInt = cacheTime?.toInt() ?: 5 * 60
            refreshTime = cachePreferencesInt.times(1000 * 1000 * 1000L)
        }
        catch (e: NumberFormatException){
            e.printStackTrace()
        }
    }
}