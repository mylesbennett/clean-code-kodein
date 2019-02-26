package com.aimicor.latinpost.view

import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import com.aimicor.latinpost.viewmodel.main.mainViewModelModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

@Suppress("unused")
class ViewApplication  : Application(), KodeinAware {

    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    @VisibleForTesting
    var bindingsOverride: Kodein.MainBuilder.() -> Unit = {}

    override val kodein = Kodein.lazy {
        import(mainViewModelModule)
        bindingsOverride()
    }
}