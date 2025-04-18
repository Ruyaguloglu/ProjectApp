package com.example.projectapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.projectapp.R;


public class SliderAdapter extends PagerAdapter {

    Context context;

    LayoutInflater layoutInflater ;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int imagesArray[] ={
            R.drawable.onboardscreen1,
            R.drawable.onboardscreen2,
            R.drawable.onboardscreen3
    };


    int headingArray[] = {
            R.string.first_slide,
            R.string.second_slide,
            R.string.third_slide,

    };

    int descriptionArray[] = {
            R.string.description,
            R.string.description,
            R.string.description,

    };


//slayt sayısı döner
    @Override
    public int getCount(){
        return headingArray.length;
    }


    // view ve object referanslarının aynı görünüme işaret ettiğini konrol ettim
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object){
        return view == (ConstraintLayout) object; //constraint layout türüne çevrilip doğrulama yapılır
    }

    @NonNull
    @Override
    //slayttaki her bir sayfayı oluşturur
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
         //sayfa düzeninin oluşturulması
        layoutInflater = (LayoutInflater)  context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view  = layoutInflater.inflate(R.layout.sliding_layout,container,false);

        ImageView   imageView = view.findViewById(R.id.slider_img);
        TextView   heading = view.findViewById(R.id.heading);
        TextView description = view.findViewById(R.id.description);

        //içeriğin alınması
        imageView.setImageResource(imagesArray[position]);
        heading.setText(headingArray[position]);
        description.setText(descriptionArray[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ConstraintLayout)object);
    }
}

