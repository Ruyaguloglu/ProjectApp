package com.example.projectapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.projectapp.R;
import com.example.projectapp.models.PopularProductsModel;
import com.example.projectapp.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating,name,description,price,quantity;
    Button addToCart,buyNow;
    ImageView addItems,removeItems;


    Toolbar toolbar;
    int totalQuantity = 1;
    int totalPrice = 0;

    // Popular ProductsProducts
    PopularProductsModel popularProductsModel = null;

    //show all
    ShowAllModel showAllModel = null;

    FirebaseAuth auth;

    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);


        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        final Object obj = getIntent().getSerializableExtra("detailed");


    if (obj instanceof PopularProductsModel){
        popularProductsModel= (PopularProductsModel) obj;
    }
    else if (obj instanceof ShowAllModel){
        showAllModel = (ShowAllModel) obj;
    }


        detailedImg = findViewById(R.id.detailed_img);
        quantity = findViewById(R.id.quantity);
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


        totalPrice = popularProductsModel.getPrice() * totalQuantity;
    }

    //Show All Products kullanıcı arayüzündeki bileşenlere veri atanıyor
    if (showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));


            totalPrice = showAllModel.getPrice() * totalQuantity;

        }

    addToCart.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            addToCart();
        }
    });

    addItems.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            if (totalQuantity < 10){
                totalQuantity++;
                quantity.setText(String.valueOf(totalQuantity));

                if (popularProductsModel != null){
                    totalPrice = popularProductsModel.getPrice() * totalQuantity;
                }if(showAllModel  != null){
                    totalPrice = showAllModel.getPrice() * totalQuantity;
                }
            }
        }
    });

    removeItems.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            if (totalQuantity>1){
                totalQuantity--;
                quantity.setText(String.valueOf(totalQuantity));

            }


        }
   });



}
// ürünün sepete eklenmesi
    private void addToCart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM/dd/yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>(); // ürün bilgilerini tutmak için
        cartMap.put("productName", name.getText().toString());// verilerin hashmap e eklenmesi
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        firestore.collection("addtoCart")
                .document(auth.getCurrentUser().getUid())
                .collection("User")
                .add(cartMap)
                .addOnSuccessListener(documentReference -> {
                    Log.d("DetailedActivity", "Ürün sepete eklendi: " + documentReference.getId());
                    Toast.makeText(DetailedActivity.this, "Sepete Eklendi", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e("DetailedActivity", "Sepete eklenirken hata: ", e);
                    Toast.makeText(DetailedActivity.this, "Hata: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}