package com.aimicor.latinpost.interactor

import com.aimicor.latinpost.ApiService
import com.aimicor.latinpost.InteractorSchedulers

abstract class ApiServiceInteractor() {

    private companion object {
        private val apiService = ApiService.create()
        private val schedulers = InteractorSchedulers()
    }

    internal fun apiService() = apiService
    internal fun schedulers() = schedulers
}