package com.mrswimmer.shiftwatch.presentation.base

import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatFragment
import com.mrswimmer.shiftwatch.R

abstract class BaseMvpFragment : MvpAppCompatFragment(), BaseView {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        injectDependencies()
        //setRetainInstance(true);
        return inflater.inflate(getLayoutID(), container, false)
    }

    protected abstract fun injectDependencies()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)
    }

    override fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("ОК"
                ) { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    override fun showChoiceDialog(title: String?, message: String?, callback: BaseFragment.DialogActionCallback) {
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Да") { dialog, id -> callback.positiveAction(dialog) }
                .setNegativeButton("Нет") { dialog, which -> callback.negativeAction(dialog) }
        val alert = builder.create()
        alert.show()
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    protected abstract fun getLayoutID(): Int

    interface DialogActionCallback {
        fun positiveAction(dialog: DialogInterface)
        fun negativeAction(dialog: DialogInterface)
    }
}