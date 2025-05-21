package edu.ucne.composedemo

import android.app.Application
import edu.ucne.composedemo.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class DemoAp2App: Application()  {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DemoAp2App)
            modules(listOf(databaseModule, repositoryModule, viewModelModule))
        }
    }
}