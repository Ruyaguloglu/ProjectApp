package com.example.projectapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.projectapp.R;
import com.example.projectapp.fragments.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    Fragment homeFragment;
    FirebaseAuth  auth;
    Toolbar toolbar;// Menüyü üst çubuğa eklemek için Toolbar


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        //menü özellikleri ekleniyor
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Geri düğmesi etkin
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        //Toolbar bileşenini uygulamanın üst çubuğu olarak ayarlar.


        homeFragment = new HomeFragment();
        loadFragment(homeFragment); //fragment yükleme

    }

    // Fragment'ı yükleyen yardımcı metod
    private void loadFragment(Fragment homeFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, homeFragment);// Fragment, belirtilen container'a yerleştiriliyor
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Menü ögelerine tıklama olayları
        //Derste switch case yapısıyla öğrendik.
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            auth.signOut();
            startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            finish();

        } else if (id == R.id.menu_my_cart) {
            //Kullanıcı alışveriş sepetine yönlendiriliyor
            startActivity(new Intent(MainActivity.this, CartActivity.class));

        }
        return true;

    }
}

