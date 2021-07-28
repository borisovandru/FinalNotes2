package com.android.FinalNotes.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android.FinalNotes.R
import com.android.FinalNotes.RouterHolder
import com.android.FinalNotes.ui.auth.AuthFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), RouterHolder {
    override var mainRouter: MainRouter? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainRouter = MainRouter(supportFragmentManager)
        if (savedInstanceState == null) {
            mainRouter!!.showAuth()
        }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            if (item.itemId == R.id.action_notes) {
                mainRouter!!.showAuth()
            }
            if (item.itemId == R.id.action_info) {
                mainRouter!!.showInfo()
            }
            true
        }
        supportFragmentManager.setFragmentResultListener(AuthFragment.AUTH_RESULT, this, { requestKey: String?, result: Bundle? -> mainRouter!!.showNotes() })
    }
}