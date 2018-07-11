package com.mrswimmer.shiftwatch.presentation.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.messaging.FirebaseMessaging
import com.mrswimmer.shiftwatch.R


class MainActivity : AppCompatActivity() {
    private val TOPIC = "JavaSampleApproach"
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }
}
