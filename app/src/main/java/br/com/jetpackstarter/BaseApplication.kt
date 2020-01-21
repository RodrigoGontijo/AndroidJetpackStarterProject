package br.com.jetpackstarter

import android.app.Application
import br.com.jetpackstarter.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.MESSAGE

class BaseApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(Modules.all)
        }
    }
}