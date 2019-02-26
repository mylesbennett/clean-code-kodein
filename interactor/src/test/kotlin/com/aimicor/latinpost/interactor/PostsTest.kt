package com.aimicor.latinpost.interactor

import com.aimicor.latinpost.PostSummary
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.Mockito.`when`

class PostsTest : ApiServiceTest<Posts>(Posts::class.java) {

    private val result = TestObserver.create<List<PostSummary>>()

    @Test
    fun `can get post`() {
        `when`(apiService.getPosts()).thenReturn(Single.just(post))
        subject.fetch().subscribe(result)
        result.assertValue(listOf(PostSummary(post[0].id, post[0].title)))
    }

    @Test
    fun `can get post list`() {
        `when`(apiService.getPosts()).thenReturn(Single.just(postList))
        subject.fetch().subscribe(result)
        result.assertValue(
            listOf(
                PostSummary(postList[0].id, postList[0].title),
                PostSummary(postList[1].id, postList[1].title)
            )
        )
    }
}