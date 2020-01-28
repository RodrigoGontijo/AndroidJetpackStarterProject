package br.com.jetpackstarter.model.dogsRepository

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.jetpackstarter.model.dogsRepository.Dao.DogDao

@Database(entities = arrayOf(DogBreed::class), version = 1)
abstract class DogDatabase : RoomDatabase(){
    abstract val dogDao: DogDao
}