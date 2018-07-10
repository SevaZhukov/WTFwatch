package com.mrswimmer.shift.domain.interactor

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kelvinapps.rxfirebase.RxFirebaseAuth
import com.mrswimmer.shiftwatch.App
import com.mrswimmer.shiftwatch.data.firebase.Acc
import com.mrswimmer.shiftwatch.data.firebase.Task
import com.mrswimmer.shiftwatch.domain.interactor.SettingsService
import java.util.*
import javax.inject.Inject


class FireService {
    private var auth = FirebaseAuth.getInstance()
    private var db = FirebaseDatabase.getInstance().reference

    var lastId: String? = null

    @Inject
    lateinit var settingsService: SettingsService

    init {
        App.getComponent().inject(this)
    }

    fun signIn(email: String, password: String, callBack: AuthCallBack) {
        RxFirebaseAuth.signInWithEmailAndPassword(auth, email, password)
                .map({ authResult -> authResult.user != null })
                .take(1)
                .subscribe({
                    getUserId()
                    callBack.onSuccess(it)
                }, { callBack.onError(it) })
    }

    fun signUp(email: String, password: String, callBack: AuthCallBack) {
        RxFirebaseAuth.createUserWithEmailAndPassword(auth, email, password)
                .map({ authResult -> authResult.user != null })
                .take(1)
                .subscribe({
                    createUser("Russia")
                    callBack.onSuccess(it)
                }, { callBack.onError(it) })
    }

    fun isSignedIn(): Boolean {
        return auth.currentUser != null
    }

    fun signOut() {
        auth.signOut()
    }

    fun getEmail(): String {
        return auth.currentUser!!.email!!
    }

    fun getTasks(callback: TasksCallBack) {
        db.child("tasks").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("code", "cancel fire")
                callback.onError(databaseError)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i("code", "data change fir")
                var tasks: MutableList<Task> = mutableListOf()
                dataSnapshot.children.forEach {
                    val task = it.getValue(Task::class.java)
                    if (task!!.accs != null) {
                        if (!task.accs.containsKey(settingsService.userId))
                            tasks.add(task)
                    } else
                        tasks.add(task)
                    lastId = task.id
                }
                callback.onSuccess(tasks)
            }
        })
    }

    interface AuthCallBack {
        fun onSuccess(success: Boolean)

        fun onError(e: Throwable)
    }

    interface TasksCallBack {
        fun onSuccess(tasks: MutableList<Task>)

        fun onError(e: DatabaseError)
    }

    fun createUser(country: String) {
        val accId = db.child("accs").push()
        settingsService.userId = accId.push().key
        val acc = Acc(getEmail(), country, accId.key)
        accId.setValue(acc)
    }


    fun addNotes() {
        val taskId = db.child("tasks").push()
        val task = Task(taskId.key, 0, "first", "second", "third", "Russia", HashMap<String, Int>())
        taskId.setValue(task)
    }

    fun sendResult(id: String, result: Int) {
        Log.i("code", "send res $id $result ${settingsService.userId}")
        val taskId = db.child("tasks").child(id).child("accs").child(settingsService.userId)
        taskId.setValue(result)
    }

    fun getUserId() {
        Log.i("code", "email ${auth.currentUser!!.email}")
        db.child("accs").orderByChild("email").equalTo(auth.currentUser!!.email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.hasChildren()) {
                    Log.i("code", "has note id")
                    createUser("Russia")
                } else {
                    dataSnapshot.children.forEach {
                        val id = it.getValue(Acc::class.java)
                        Log.i("code", "userid ${id!!.id}")
                        settingsService.userId = id.id
                    }
                }
            }

        })
    }

    fun getUser(callback: UserCallback) {
        db.child("accs").child(settingsService.userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                callback.onError(databaseError)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val acc = dataSnapshot.getValue(Acc::class.java)
                Log.i("code", "error snap $dataSnapshot")
                callback.onSuccess(acc!!)
            }

        })
    }

    interface UserCallback {
        fun onSuccess(acc: Acc)
        fun onError(databaseError: DatabaseError)
    }
}