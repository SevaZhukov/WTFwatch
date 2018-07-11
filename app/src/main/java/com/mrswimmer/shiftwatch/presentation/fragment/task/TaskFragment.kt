package com.mrswimmer.shiftwatch.presentation.fragment.task


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrswimmer.shiftwatch.App
import com.mrswimmer.shiftwatch.R
import com.mrswimmer.shiftwatch.presentation.base.BaseFragment
import com.mrswimmer.shiftwatch.presentation.fragment.menu.MenuFragmentPresenter


class TaskFragment : BaseFragment() {
    override fun injectDependencies() {
        App.getComponent().inject(this)
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_task
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}
