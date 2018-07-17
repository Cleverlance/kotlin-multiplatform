package jh.shared.navigation.ui

import android.support.annotation.CallSuper
import com.jakewharton.rxrelay2.PublishRelay
import jh.shared.arch.ui.RxActivity
import jh.shared.navigation.presentation.NavigationViewModel

abstract class NavigationActivity<M : NavigationViewModel<S>, S : Any> : RxActivity<M>() {

    private val backRelay = PublishRelay.create<Unit>()

    @CallSuper
    override fun bindUiToViewModel() {
        backRelay.subscribe { viewModel.back() }
    }

    @CallSuper
    override fun bindViewModelToUi() = listOf(
            viewModel.backs.subscribe { super.onBackPressed() }
    )

    final override fun onBackPressed() {
        backRelay.accept(Unit)
    }
}