package com.mrswimmer.shiftwatch.presentation.fragment.menu

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.database.DatabaseError
import com.mrswimmer.shift.domain.interactor.FireService
import com.mrswimmer.shiftwatch.App
import com.mrswimmer.shiftwatch.data.firebase.Task
import javax.inject.Inject

@InjectViewState
class MenuFragmentPresenter : MvpPresenter<MenuFragmentView>() {
    @Inject
    lateinit var fireService: FireService

    companion object {
        var curTasks: MutableList<Task> = mutableListOf()
    }

    fun setDataListener() {
        fireService.getTasks(object : FireService.TasksCallBack {
            override fun onSuccess(tasks: MutableList<Task>) {
                if (tasks.size == 0)
                    viewState.showEmptyTasksMessage()
                else {
                    curTasks = tasks
                    viewState.goToTask()
                }
            }

            override fun onError(e: DatabaseError) {
                viewState.showErrorMessage()
            }
        })
    }

    init {
        App.getComponent().inject(this)
    }

}