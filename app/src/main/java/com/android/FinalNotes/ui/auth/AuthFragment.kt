package com.android.FinalNotes.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.FinalNotes.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class AuthFragment : Fragment() {
    private var googleSignInClient: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), options)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.signIn).setOnClickListener { v: View? ->
            val intent = googleSignInClient!!.signInIntent
            startActivityForResult(intent,
                    AUTH_REQUEST_ID)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode,
                resultCode,
                data)
        if (requestCode == AUTH_REQUEST_ID) {
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                accountTask.result
                parentFragmentManager.setFragmentResult(AUTH_RESULT, Bundle())
            } catch (exception: Exception) {
                Toast.makeText(requireContext(), "Auth Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val TAG = "AuthFragment"
        const val AUTH_RESULT = "AUTH_RESULT"
        private const val AUTH_REQUEST_ID = 1
        @JvmStatic
        fun newInstance(): AuthFragment {
            return AuthFragment()
        }
    }
}