package com.aimicor.latinpost.viewmodel.detail

class DetailViewModelFactory {
    internal fun create(postId: Int) = DetailViewModel(postId)
}