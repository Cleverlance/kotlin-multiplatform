package jh.shared.arch.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import jh.shared.listeners.infrastructure.Subscription
import javax.inject.Inject

abstract class RxActivity<M : Any> : AppCompatActivity() {

    @Inject protected lateinit var viewModel: M

    protected abstract val layoutResId: Int
    private val subscriptions = mutableListOf<Subscription>()
    private var isInitializingView = false

    protected abstract fun inject()

    protected open fun bindViewModelToUi() = listOf<Subscription>()

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

        subscriptions.clear()
        subscriptions.addAll(bindViewModelToUi())
    }

    @CallSuper
    override fun onStop() {
        super.onStop()

        subscriptions.clear()
    }
}