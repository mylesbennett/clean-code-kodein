package com.aimicor.latinpost.viewmodel.detail

import com.aimicor.latinpost.PostDetails
import com.aimicor.latinpost.interactor.Details
import com.aimicor.latinpost.viewmodel.ViewModelSchedulers
import io.reactivex.Observable
import java.io.Serializable

// Conceptually a view-model but no need to be a ViewModel derivative
class DetailViewModel internal constructor(
    postId: Int,
    details: Details = Details(),
    schedulers: ViewModelSchedulers = ViewModelSchedulers()
) : Serializable {

    lateinit var postDetails: PostDetails
        private set

    val detailViewModelEvent = details.fetch(postId)
        .observeOn(schedulers.mainThread())
        .doOnSuccess { postDetails = it }
        .toObservable()
        .flatMap { Observable.just(this) }
}