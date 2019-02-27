package com.aimicor.latinpost.interactor

import com.aimicor.latinpost.ApiService
import com.aimicor.latinpost.InteractorSchedulers

abstract class ApiServiceInteractor() {

    private companion object {
        private val _apiService = ApiService.create()
        private val _schedulers = InteractorSchedulers()
    }

    internal val apiService = _apiService
    internal val schedulers = _schedulers
}