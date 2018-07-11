package com.mrswimmer.shiftwatch.presentation.fragment.settings

import android.content.Intent
import android.net.Uri
import butterknife.OnClick
import com.mrswimmer.shift.domain.interactor.FireService
import com.mrswimmer.shiftwatch.App
import com.mrswimmer.shiftwatch.R
import com.mrswimmer.shiftwatch.presentation.base.BaseFragment
import javax.inject.Inject

class SettingsFragment : BaseFragment() {

    @Inject
    lateinit var fireService: FireService

    override fun injectDependencies() {
        App.getComponent().inject(this)
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_settings
    }

    @OnClick(R.id.settings_back)
    fun onBackClick() {
        navController.popBackStack()
    }

    @OnClick(R.id.settings_play)
    fun onPlayClick() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("market://details?id=com.mrswimmer.shiftwatch")
        activity!!.startActivity(intent)
    }

    @OnClick(R.id.settings_sign_out)
    fun onSignOutClick() {
        fireService.signOut()
        navController.popBackStack(R.id.signInFragment, false)
    }

    @OnClick(R.id.settings_info)
    fun onInfoClick() {
        showDialog("Инфо", "Какая-то инфа")
    }
}
