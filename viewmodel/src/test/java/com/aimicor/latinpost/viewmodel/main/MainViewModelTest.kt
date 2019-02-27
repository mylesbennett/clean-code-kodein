package com.aimicor.latinpost.viewmodel.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.aimicor.latinpost.PostSummary
import com.aimicor.latinpost.interactor.Posts
import com.aimicor.latinpost.viewmodel.ViewModelSchedulers
import com.aimicor.latinpost.viewmodel.detail.DetailViewModel
import com.aimicor.latinpost.viewmodel.detail.DetailViewModelFactory
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner.Silent::class)
class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mainAdapter: MainAdapter

    @Mock
    private lateinit var schedulers: ViewModelSchedulers

    @Mock
    private lateinit var posts: Posts

    @Mock
    private lateinit var detailViewModelFactory: DetailViewModelFactory

    @Mock
    private lateinit var showProgress: Observer<Boolean>

    private val mainViewModel get() = MainViewModel(mainAdapter, posts, schedulers, detailViewModelFactory)
    private val clickSubject = PublishSubject.create<Int>()
    private val listSubject = Single.create<List<PostSummary>> {  }
    private val observer = TestObserver<DetailViewModel>()

    companion object {
        private val post1 = PostSummary(1, "post1")
        val postList1 = listOf(post1)
    }

    @Before
    fun `set up`() {
        `when`(schedulers.mainThread()).thenReturn(Schedulers.trampoline())
        `when`(mainAdapter.clickEvent).thenReturn(clickSubject)
    }

    @Test
    fun `can get a list`() {
        `when`(posts.fetch()).thenReturn(Single.just(postList1))

        mainViewModel.showProgress.observeForever(showProgress)

        verify(mainAdapter).submitList(postList1)
        verify(showProgress).onChanged(false)
    }

    @Test
    fun `can click on item`() {
        val detailViewModel = mock(DetailViewModel::class.java)
        val detailsReceived = PublishSubject.create<DetailViewModel>()
        `when`(detailViewModel.detailViewModelEvent).thenReturn(detailsReceived)
        `when`(detailViewModelFactory.create(1)).thenReturn(detailViewModel)
        `when`(posts.fetch()).thenReturn(listSubject)

        mainViewModel.showProgress.observeForever(showProgress)
        mainViewModel.listItemClickEvent.subscribe(observer)
        clickSubject.onNext(1)

        verify(showProgress).onChanged(true)

        detailsReceived.onNext(detailViewModel)
        observer.assertValue(detailViewModel)
        verify(showProgress).onChanged(false)
    }
}