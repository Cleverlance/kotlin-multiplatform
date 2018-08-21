package jh.multiweather.overview.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import jh.multiplatform.R
import jh.multiweather.main.ui.MainApplication
import jh.multiweather.overview.platform.OverviewViewModel
import jh.shared.arch.ui.RxFragment
import kotlinx.android.synthetic.main.overview__overview_fragment.*

class OverviewFragment : RxFragment<OverviewViewModel>() {

    override val layoutResId = R.layout.overview__overview_fragment

    override fun inject() {
        MainApplication.getInjector(context).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.menu.add(R.string.current__refresh).apply {
            setIcon(R.drawable.ic_search)
            setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            setOnMenuItemClickListener {
                viewModel.refresh()
                true
            }
        }

        input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_SEARCH) viewModel.refresh()
            true
        }
    }

    override fun bindUiToViewModel() {
        input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
            }

            override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(sequence: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.setCity(sequence.toString())
            }
        })
    }
}