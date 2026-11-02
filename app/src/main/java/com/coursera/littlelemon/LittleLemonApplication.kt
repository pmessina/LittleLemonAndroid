package com.coursera.littlelemon

import android.app.Application
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder<UserDatabase>(androidContext(), "little_lemon.db")
            .setDriver(AndroidSQLiteDriver())
            .build()
    }
    single { get<UserDatabase>().userDao() }
    single { UserRepository(get()) }
    single { NetworkClient.instance }
    single { MenuRepository(get()) }
    viewModelOf(::MenuViewModel)
    viewModelOf(::UserViewModel)
}

class LittleLemonApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LittleLemonApplication)
            modules(appModule)
        }
    }
}

