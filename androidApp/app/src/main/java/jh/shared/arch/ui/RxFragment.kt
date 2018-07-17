package jh.shared.arch.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class RxFragment<M : Any> : Fragment() {

    @Inject protected lateinit var viewModel: M

    protected abstract val layoutResId: Int
    private val disposables = CompositeDisposable()
    private var isInitializingView = true

    protected abstract fun inject()

    protected open fun bindViewModelToUi() = listOf<Disposable>()

    protected open fun bindUiToViewModel() {}

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inject()
    }

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutResId, container, false)

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindUiToViewModel()
    }

    @CallSuper
    override fun onStart() {
        super.onStart()

        isInitializingView = false

        disposables.clear()
        disposables.addAll(*bindViewModelToUi().toTypedArray())
    }

    @CallSuper
    override fun onStop() {
        super.onStop()

        disposables.clear()

        isInitializingView = true
    }

    protected fun <T> Observable<T>.filterNotInitializingView(): Observable<T> = filter { !isInitializingView }
}