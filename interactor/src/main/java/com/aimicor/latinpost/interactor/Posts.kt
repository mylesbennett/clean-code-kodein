package com.aimicor.latinpost.interactor

import com.aimicor.latinpost.PostSummary
import io.reactivex.Single

class Posts : ApiServiceInteractor() {

    fun fetch(): Single<List<PostSummary>> =
        apiService.getPosts()
            .subscribeOn(schedulers.io())
            .toObservable()
            .flatMapIterable { postList -> postList }
            .map { post -> PostSummary(post.id, post.title) }
            .toList()
}