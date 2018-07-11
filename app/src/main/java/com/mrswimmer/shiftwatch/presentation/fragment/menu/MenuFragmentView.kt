package com.mrswimmer.shiftwatch.presentation.fragment.menu

import com.arellomobile.mvp.MvpView
import com.mrswimmer.shiftwatch.presentation.base.BaseView

interface MenuFragmentView : BaseView {
    fun goToTask()
    fun showEmptyTasksMessage()
    fun showErrorMessage()
}