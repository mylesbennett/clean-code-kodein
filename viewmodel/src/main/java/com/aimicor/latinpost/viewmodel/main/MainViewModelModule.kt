package com.aimicor.latinpost.viewmodel.main

import com.aimicor.latinpost.interactor.Posts
import com.aimicor.latinpost.viewmodel.ViewModelSchedulers
import com.aimicor.latinpost.viewmodel.bindViewModel
import com.aimicor.latinpost.viewmodel.detail.DetailViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

val mainViewModelModule = Kodein.Module(name = "mainViewModelModule") {
    bind<MainAdapter>() with provider { MainAdapter() }
    bind<ViewModelSchedulers>() with singleton { ViewModelSchedulers() }
    bind<Posts>() with singleton { Posts() }
    bind<DetailViewModelFactory>() with singleton { DetailViewModelFactory() }
    bindViewModel<MainViewModel>() with provider { MainViewModel(instance(), instance(), instance(), instance()) }
}