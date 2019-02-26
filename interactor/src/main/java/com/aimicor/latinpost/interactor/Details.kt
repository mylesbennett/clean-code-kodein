package com.aimicor.latinpost.interactor

import com.aimicor.latinpost.PostDetails
import io.reactivex.Single
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers

class Details : ApiServiceInteractor() {

    fun fetch(postId: Int): Single<PostDetails> =
        apiService().getPosts(postId)
            .subscribeOn(schedulers().io())
            .map { posts -> posts[0] }
            .flatMap { post ->
                Single.zip<String, String, String, String, PostDetails>(
                    Single.just(post.title),
                    Single.just(post.body),
                    apiService().getUsers(post.userId).subscribeOn(Schedulers.io()).map { it[0].username },
                    apiService().getComments(post.id).subscribeOn(Schedulers.io()).map { it.size.toString() },
                    Function4 { title, body, name, comments -> PostDetails(title, body, name, comments) }
                )
            }
}