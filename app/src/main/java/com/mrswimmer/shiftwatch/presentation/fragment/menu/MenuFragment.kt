package com.mrswimmer.shiftwatch.presentation.fragment.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import butterknife.BindView
import butterknife.OnClick
import com.mrswimmer.shiftwatch.App
import com.mrswimmer.shiftwatch.R
import com.mrswimmer.shiftwatch.presentation.base.BaseFragment

class MenuFragment : BaseFragment() {

    override fun injectDependencies() {
        App.getComponent().inject(this)
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @OnClick(R.id.menu_exit)
    fun onExitClick() {
        activity.finish()
    }

    @OnClick(R.id.menu_settings)
    fun onSettingsClick() {
        navController.navigate(R.id.settingsFragment)
    }

    @OnClick(R.id.menu_profile)
    fun onProfileClick() {
        navController.navigate(R.id.profileFragment)
    }

    @OnClick(R.id.menu_task)
    fun onTaskClick() {
        navController.navigate(R.id.taskFragment)
    }
}
