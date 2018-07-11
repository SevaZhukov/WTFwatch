package com.mrswimmer.shiftwatch.presentation.fragment.profile


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.google.firebase.database.DatabaseError
import com.mrswimmer.shift.domain.interactor.FireService
import com.mrswimmer.shiftwatch.App
import com.mrswimmer.shiftwatch.R
import com.mrswimmer.shiftwatch.data.firebase.Acc
import com.mrswimmer.shiftwatch.presentation.base.BaseFragment
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    @Inject
    lateinit var fireService: FireService

    @BindView(R.id.profile_tasks)
    lateinit var tasks: TextView
    @BindView(R.id.profile_wins)
    lateinit var wins: TextView

    override fun injectDependencies() {
        App.getComponent().inject(this)
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fireService.getUser(object : FireService.UserCallback {
            override fun onSuccess(acc: Acc) {
                wins.text = acc.wins.toString()
                tasks.text = acc.amountOfTasks.toString()
            }

            override fun onError(databaseError: DatabaseError) {
                showToast("Ошибка загрузки данных")
                Log.i("code", "${databaseError.message}")
            }

        })
    }

    @OnClick(R.id.profile_back)
    fun onBackClick() {
        navController.popBackStack()
    }
}
