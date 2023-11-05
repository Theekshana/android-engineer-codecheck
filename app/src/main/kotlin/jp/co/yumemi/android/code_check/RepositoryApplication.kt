package jp.co.yumemi.android.code_check

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * The custom application class for the repository application.
 */
@HiltAndroidApp
class RepositoryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}