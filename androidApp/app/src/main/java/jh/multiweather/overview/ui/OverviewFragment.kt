package jh.multiweather.overview.ui

import jh.multiplatform.R
import jh.multiweather.main.ui.MainApplication
import jh.multiweather.overview.platform.OverviewViewModel
import jh.shared.arch.ui.RxFragment

class OverviewFragment : RxFragment<OverviewViewModel>() {

    override val layoutResId = R.layout.overview__overview_fragment

    override fun inject() {
        MainApplication.getInjector(context).inject(this)
    }
}