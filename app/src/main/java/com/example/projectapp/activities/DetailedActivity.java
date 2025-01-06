package com.example.projectapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.projectapp.R;
import com.example.projectapp.models.NewProductsModel;
import com.example.projectapp.models.PopularProductsModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating,name,description,price;
    Button addToCart,buyNow;
    ImageView addItems,removeItems;
    // Popular Products
    private FirebaseFirestore firestore ;
    PopularProductsModel popularProductsModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed);

        firestore = FirebaseFirestore.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");


    if(obj instanceof PopularProductsModel){
        popularProductsModel = (PopularProductsModel) obj;
    }else if (obj instanceof PopularProductsModel){
        popularProductsModel = (PopularProductsModel) obj;


    }


        detailedImg = findViewById(R.id.detailed_img);
        name = findViewById(R.id.detailed_name);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);

        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);

        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);

    if (popularProductsModel != null){
        Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
        name.setText(popularProductsModel.getName());
        rating.setText(popularProductsModel.getRating());
        description.setText(popularProductsModel.getDescription());
        price.setText(String.valueOf(popularProductsModel.getPrice()));
        name.setText(popularProductsModel.getName());
    }

    }
}