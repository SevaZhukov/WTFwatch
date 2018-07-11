package com.mrswimmer.shiftwatch.presentation.fragment.menu

import android.os.Bundle
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.mrswimmer.shiftwatch.App
import com.mrswimmer.shiftwatch.R
import com.mrswimmer.shiftwatch.presentation.base.BaseMvpFragment

class MenuFragment : BaseMvpFragment(), MenuFragmentView {
    override fun showErrorMessage() {
        showToast("Ошибка загрузки")
    }

    override fun showEmptyTasksMessage() {
        showToast("Для вас пока нет задач")
    }

    override fun goToTask() {
        navController.navigate(R.id.taskFragment)
    }

    @InjectPresenter
    lateinit var presenter: MenuFragmentPresenter

    @ProvidePresenter
    fun presenter(): MenuFragmentPresenter {
        return MenuFragmentPresenter()
    }

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
        activity!!.finish()
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
        presenter.setDataListener()
    }
}
