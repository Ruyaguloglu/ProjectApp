package com.example.projectapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager.widget.ViewPager;

import com.example.projectapp.R;
import com.example.projectapp.adapter.SliderAdapter;

public class OnBoardingActivity extends AppCompatActivity {
        //Tanıtım ekranları için gerekli degişkenler
        ViewPager viewPager;
        LinearLayout dotsLayout;
        Button btn;

        SliderAdapter sliderAdapter;

        TextView[] dots;
        Animation animation;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //Tam ekran modu
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_on_boarding);
            //getSupportActionBar().hide();

            viewPager = findViewById(R.id.slider);
            dotsLayout = findViewById(R.id.dots);
            btn = findViewById(R.id.get_started_btn);
            // ilk sayfa göstergelerini ekler
            addDots(0);

            viewPager.addOnPageChangeListener(changeListener);


            // ViewPager için adaptör ayarlanıyor
            sliderAdapter = new SliderAdapter(this);
            viewPager.setAdapter(sliderAdapter);

            //"Başla" butonuna tıklanınca RegistrationActivity'ye yönlendirme
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    startActivity(new Intent(OnBoardingActivity.this,RegistrationActivity.class));
                    finish();
                }


            });
        }
    // Sayfa göstergelerini ekleyen metod
        private void addDots(int position) {

            dots = new TextView[3];
            dotsLayout.removeAllViews();
            for ( int i = 0; i < dots.length; i++) {
                // Her sayfa için gösterge oluştur
                dots[i] = new TextView( this );
                dots[i].setText(Html.fromHtml("&#8226;")); //nokta sembolü
                dots[i].setTextSize(35);
                dotsLayout.addView(dots[i]);

            }// Aktif sayfanın göstergesini renklendir
            if (dots.length > 0) {
                dots[position].setTextColor(getResources().getColor(R.color.pink));

            }
        }// Sayfa değişim olaylarını dinleyen listener
        ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){

            }

            @Override
            public void onPageSelected(int position){
                // Sayfa göstergelerini güncelle
                addDots(position);

                if ( position == 0){
                    btn.setVisibility(View.INVISIBLE);
                }else if (position == 1 ){
                    btn.setVisibility(View.INVISIBLE); //ilk 2 sayfada buton görünmesin
                }else {
                    // Son sayfada animasyonla birlikte buton görünür
                    animation = AnimationUtils.loadAnimation(OnBoardingActivity.this,R.anim.slide_animation);
                    btn.setAnimation(animation);
                    btn.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        };


    }