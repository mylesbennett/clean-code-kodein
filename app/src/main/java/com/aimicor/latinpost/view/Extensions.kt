package com.aimicor.latinpost.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.instance

inline fun <reified VM : ViewModel, T> T.lazyViewModel(): Lazy<VM> where T : KodeinAware, T : Fragment {
    return lazy { ViewModelProviders.of(this, ViewModelFactory(this.kodein)).get(VM::class.java) }
}

fun <T> Observable<T>.observe(lifecycle: Lifecycle, onNext: (it: T) -> Unit) {
    LiveObserver(lifecycle, subscribe(onNext))
}

private class LiveObserver(
    private val lifecycle: Lifecycle,
    private val disposable: Disposable
) : LifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        disposable.dispose()
        lifecycle.removeObserver(this)
    }
}

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val injector: Kodein) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return injector.direct.instance<ViewModel>(tag = modelClass.simpleName) as T?
            ?: modelClass.newInstance()
    }
}
