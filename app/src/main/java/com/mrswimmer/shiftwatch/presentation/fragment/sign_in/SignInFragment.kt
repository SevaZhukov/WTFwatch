package com.mrswimmer.shiftwatch.presentation.fragment.sign_in

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mrswimmer.shift.domain.interactor.FireService
import com.mrswimmer.shiftwatch.App
import com.mrswimmer.shiftwatch.R
import javax.inject.Inject

class SignInFragment : Fragment() {
    lateinit var navController: NavController

    @Inject
    lateinit var fireService: FireService

    private val TAG = "code"
    lateinit var mGoogleSignInClient: GoogleSignInClient
    var auth = FirebaseAuth.getInstance()
    private val RC_SIGN_IN = 9001

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().inject(this)
        navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
        if (fireService.isSignedIn())
            navController.navigate(R.id.menuFragment)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(resources.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)
    }

    @OnClick(R.id.sign_in_button_google)
    internal fun onGoogleClick() {
        val signInIntent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(activity, "Ощибка авторизации", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.i(TAG, "firebaseAuthWithGoogle:" + acct.getId()!!)
        val credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        Log.i(TAG, "signInWithCredential:success")
                        initUserAfterGoogleSignIn()
                    } else {
                        Log.i(TAG, "signInWithCredential:failure" + task.exception)
                        Toast.makeText(activity, "Ощибка авторизации", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun initUserAfterGoogleSignIn() {
        fireService.getUserId()
        navController.navigate(R.id.menuFragment)
    }

}
