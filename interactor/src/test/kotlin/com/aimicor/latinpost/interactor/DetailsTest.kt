package com.aimicor.latinpost.interactor

import com.aimicor.latinpost.PostDetails
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.Mockito.`when`

class DetailsTest : ApiServiceTest<Details>(Details::class.java) {

    private val result = TestObserver.create<PostDetails>()

    @Test
    fun `expected info returned given non-null results`() {
        `when`(apiService.getPosts(12345)).thenReturn(Single.just(post))
        `when`(apiService.getUsers(91)).thenReturn(Single.just(userDataNoNulls))
        `when`(apiService.getComments(23)).thenReturn(Single.just(commentList))
        val expected = PostDetails(
            post[0].title,
            post[0].body,
            userDataNoNulls[0].username,
            commentList.size.toString()
        )

        subject.fetch(12345).subscribe(result)

        result.assertValue { actual -> actual.equals(expected) }
    }
}