package com.aimicor.latinpost.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind

fun <T : ViewDataBinding> LayoutInflater.inflateBinding(@LayoutRes layoutId: Int, container: ViewGroup?): T =
    DataBindingUtil.inflate(this, layoutId, container, false)

inline fun <reified T : ViewModel> Kodein.Builder.bindViewModel(overrides: Boolean? = null): Kodein.Builder.TypeBinder<T> {
    return bind<T>(T::class.java.simpleName, overrides)
}