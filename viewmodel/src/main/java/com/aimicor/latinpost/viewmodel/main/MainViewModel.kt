package com.aimicor.latinpost.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aimicor.latinpost.PostSummary
import com.aimicor.latinpost.interactor.Posts
import com.aimicor.latinpost.viewmodel.ViewModelSchedulers
import com.aimicor.latinpost.viewmodel.detail.DetailViewModel
import com.aimicor.latinpost.viewmodel.detail.DetailViewModelFactory
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.io.Serializable

class MainViewModel(
    val adapter: MainAdapter,
    posts: Posts,
    schedulers: ViewModelSchedulers,
    private val detailViewModelFactory: DetailViewModelFactory
) : ViewModel(), Serializable {

    private val listItemClickSubject = PublishSubject.create<DetailViewModel>()

    val showProgress = MutableLiveData<Boolean>()
    val listItemClickEvent: Observable<DetailViewModel> = listItemClickSubject

    private val disposables = CompositeDisposable(
        posts.fetch()
            .observeOn(schedulers.mainThread())
            .subscribe { list -> onListFetch(list) },
        adapter.clickEvent
            .doOnNext { showProgress.value = true }
            .switchMap { onItemClick(it) }
            .observeOn(schedulers.mainThread())
            .subscribe { listItemClickSubject.onNext(it) }
    )

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    private fun onListFetch(list: List<PostSummary>) {
        adapter.submitList(list)
        showProgress.value = false
    }

    private fun onItemClick(postId: Int) =
        detailViewModelFactory.create(postId).detailViewModelEvent
}
