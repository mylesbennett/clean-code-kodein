package com.aimicor.latinpost.viewmodel

import io.reactivex.android.schedulers.AndroidSchedulers

class ViewModelSchedulers {

    fun mainThread() = AndroidSchedulers.mainThread()
}