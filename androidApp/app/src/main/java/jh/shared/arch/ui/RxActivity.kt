package jh.shared.arch.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class RxActivity<M : Any> : AppCompatActivity() {

    @Inject protected lateinit var viewModel: M

    protected abstract val layoutResId: Int
    private val disposables = CompositeDisposable()
    private var isInitializingView = false

    protected abstract fun inject()

    protected open fun bindViewModelToUi() = listOf<Disposable>()

    protected open fun bindUiToViewModel() {}

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate()

        super.onCreate(savedInstanceState)

        setContentView(layoutResId)

        inject()

        isInitializingView = true
        bindUiToViewModel()
        isInitializingView = false
    }

    protected open fun beforeOnCreate() {}

    @CallSuper
    override fun onStart() {
        super.onStart()

        disposables.clear()
        disposables.addAll(*bindViewModelToUi().toTypedArray())
    }

    @CallSuper
    override fun onStop() {
        super.onStop()

        disposables.clear()
    }

    protected fun <T> Observable<T>.filterNotInitializingView(): Observable<T> = filter { !isInitializingView }
}