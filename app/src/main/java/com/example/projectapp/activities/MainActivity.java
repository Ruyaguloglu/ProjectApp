package com.example.projectapp.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectapp.R;
import com.example.projectapp.fragments.HomeFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private Fragment homeFragment;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Google Play Services kontrolü
        if (checkGooglePlayServices()) {
            // Google Play Services mevcutsa fragment'ı yükle
            initializeFragment();
        }
    }

    private boolean checkGooglePlayServices() {
        GoogleApiAvailability googleApi = GoogleApiAvailability.getInstance();
        int result = googleApi.isGooglePlayServicesAvailable(this);

        if (result != ConnectionResult.SUCCESS) {
            if (googleApi.isUserResolvableError(result)) {
                googleApi.getErrorDialog(this, result, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .setOnDismissListener(dialog -> {
                            // Dialog kapatıldığında tekrar kontrol et
                            if (googleApi.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
                                initializeFragment();
                            } else {
                                Toast.makeText(this, "Google Play Services gerekli!",
                                        Toast.LENGTH_LONG).show();
                            }
                        });

            } else {
                Toast.makeText(this, "Bu cihaz Google Play Services desteklemiyor.",
                        Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }

    private void initializeFragment() {
        homeFragment = new HomeFragment();
        loadFragment(homeFragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Uygulama tekrar açıldığında Google Play Services kontrolü
        checkGooglePlayServices();
    }
}

