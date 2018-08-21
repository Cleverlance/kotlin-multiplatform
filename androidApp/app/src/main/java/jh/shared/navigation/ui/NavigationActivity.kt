package jh.shared.navigation.ui

import android.support.annotation.CallSuper
import jh.shared.arch.ui.RxActivity
import jh.shared.listeners.infrastructure.MutableObservable
import jh.shared.navigation.presentation.NavigationViewModel

abstract class NavigationActivity<M : NavigationViewModel<S>, S : Any> : RxActivity<M>() {

    private val backRelay = MutableObservable<Unit>()

    @CallSuper
    override fun bindUiToViewModel() {
        backRelay.subscribe { viewModel.back() }
    }

    @CallSuper
    override fun bindViewModelToUi() = listOf(
            viewModel.backs.subscribe { super.onBackPressed() }
    )

    final override fun onBackPressed() {
        backRelay.post(Unit)
    }
}