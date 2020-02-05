package br.com.jetpackstarter.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import br.com.jetpackstarter.DogsConstants

class SharedPreferencesHelper(val context: Context) {

    private var  CACHE_DURATION = "PrefsCacheDuration"
    var timeSharedPreferences: SharedPreferences? = null

    fun initTimeSharedPreferences() {
        timeSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun getCacheTimeDuration(): String? {
         timeSharedPreferences.apply {
             return timeSharedPreferences?.getString(CACHE_DURATION, "")
        }
    }

    fun getUpdateTimeDuration(): Long {
        if(timeSharedPreferences != null)
            timeSharedPreferences.apply {
                return timeSharedPreferences!!.getLong(DogsConstants.PREFS_TIME,0)
            }
        else
            return 0
    }

    fun setUpdateTime(){
        timeSharedPreferences.apply {
            timeSharedPreferences?.edit(commit = true) {
                putLong(
                    DogsConstants.PREFS_TIME,
                    System.nanoTime()
                )
            }
        }
    }
}