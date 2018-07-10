package com.mrswimmer.shiftwatch.presentation.fragment.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import butterknife.BindView
import com.mrswimmer.shiftwatch.App
import com.mrswimmer.shiftwatch.R

class MenuFragment : Fragment() {
    lateinit var navController: NavController

    @BindView(R.id.)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().inject(this)
        navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
    }
}
