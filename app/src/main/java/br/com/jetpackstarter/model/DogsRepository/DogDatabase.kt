package br.com.jetpackstarter.model.DogsRepository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.jetpackstarter.model.DogsRepository.Dao.DogDao

@Database(entities = arrayOf(DogBreed::class), version = 1)
abstract class DogDatabase : RoomDatabase(){
    abstract val dogDao: DogDao
}