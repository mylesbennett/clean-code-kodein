package com.aimicor.latinpost.interactor

import com.aimicor.latinpost.ApiService
import com.aimicor.latinpost.InteractorSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
abstract class ApiServiceTest<T : ApiServiceInteractor>(
    private val classToken: Class<T>
) {
    @Mock
    private lateinit var schedulers: InteractorSchedulers

    @Mock
    protected lateinit var apiService: ApiService

    protected val subject: T by lazy { spy(classToken) }

    @Before
    fun `set up`() {
        Mockito.`when`(subject.apiService).thenReturn(apiService)
        Mockito.`when`(subject.schedulers).thenReturn(schedulers)
        Mockito.`when`(schedulers.io()).thenReturn(Schedulers.trampoline())
    }
}