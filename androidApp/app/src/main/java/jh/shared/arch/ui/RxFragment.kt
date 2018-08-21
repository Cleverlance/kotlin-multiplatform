package jh.shared.arch.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jh.shared.listeners.infrastructure.Subscription
import jh.shared.listeners.infrastructure.unsubscribeAllAndClear
import javax.inject.Inject

abstract class RxFragment<M : Any> : Fragment() {

    @Inject protected lateinit var viewModel: M

    protected abstract val layoutResId: Int
    private val subscriptions = mutableListOf<Subscription>()

    protected abstract fun inject()

    protected open fun bindViewModelToUi() = listOf<Subscription>()

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

        subscriptions.unsubscribeAllAndClear()
        subscriptions.addAll(bindViewModelToUi())
    }

    @CallSuper
    override fun onStop() {
        super.onStop()

        subscriptions.unsubscribeAllAndClear()
    }
}