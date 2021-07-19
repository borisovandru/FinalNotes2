package com.android.FinalNotes.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.FinalNotes.R;
import com.android.FinalNotes.RouterHolder;
import com.android.FinalNotes.ui.auth.AuthFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements RouterHolder {

    private MainRouter router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        router = new MainRouter(getSupportFragmentManager());

        if (savedInstanceState == null) {
            router.showAuth();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_notes) {
                router.showAuth();
            }

            if (item.getItemId() == R.id.action_info) {
                router.showInfo();
            }
            return true;
        });

        getSupportFragmentManager().setFragmentResultListener(AuthFragment.AUTH_RESULT, this, (requestKey, result) -> router.showNotes());
    }

    @Override
    public MainRouter getMainRouter() {
        return router;
    }
}