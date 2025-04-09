package com.example.projectapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.R;
import com.example.projectapp.adapter.MyCartAdapter;
import com.example.projectapp.models.MyCartModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    int overAllTotalAmount;
    TextView overAllAmount;
    Toolbar toolbar;// Üst menü çubuğu
    RecyclerView recyclerView;//Kullanıcı sepeti için
    List<MyCartModel> cartModelList;
    MyCartAdapter cartAdapter;// RecyclerView için adaptör
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // tam ekran veya kenardan kenara arayüz
        setContentView(R.layout.activity_cart);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.my_cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// araç çubuğuna geri dönme butonu eklenir.


        // BroadcastReceiver ile toplam miktar değişikliklerini dinlemek için kayıt yapılıyor
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver ,new IntentFilter("MyTotalAmount"));


        overAllAmount = findViewById(R.id.textView3);
        recyclerView = findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartModelList = new ArrayList<>(); // Sepet verileri için liste oluşturuluyor
        cartAdapter = new MyCartAdapter(this,cartModelList);
        recyclerView.setAdapter(cartAdapter);

        loadCartItems(); // Sepet ögelerini yüklemek için metot çağrılıyor
    }

    private void loadCartItems() {
        if (auth.getCurrentUser() == null) {
            Log.e("CartActivity", "Kullanıcı giriş yapmamış!");
            return;
        }

        String userId = auth.getCurrentUser().getUid();
        Log.d("CartActivity", "Kullanıcı ID: " + userId);


        // Firestore'dan "addtoCart" koleksiyonundaki kullanıcıya özel veriler yükleniyor

        firestore.collection("addtoCart")
                .document(userId)
                .collection("User")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    cartModelList.clear();
                    
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Log.d("CartActivity", "Döküman ID: " + document.getId());
                        Log.d("CartActivity", "Döküman verisi: " + document.getData());
                        
                        if (document.exists() && document.getData() != null) {
                            // Her belge bir ürün modeline dönüştürülüyor ve listeye ekleniyor
                            MyCartModel cartItem = document.toObject(MyCartModel.class);//Belgedeki verileri, MyCartModel sınıfına dönüştürür.
                            if (cartItem != null) {
                                cartModelList.add(cartItem);//Dönüştürülen nesne listeye eklenir.
                                Log.d("CartActivity", "Ürün eklendi: " + cartItem.getProductName());
                            }
                        }
                    }
                    
                    cartAdapter.notifyDataSetChanged();
                    calculateTotalAmount();
                    
                    Log.d("CartActivity", "Toplam " + cartModelList.size() + " ürün yüklendi");
                })
                .addOnFailureListener(e -> {
                    Log.e("CartActivity", "Veri yükleme hatası: ", e);
                });
    }

    private void calculateTotalAmount() {
        int total = 0;
        for (MyCartModel item : cartModelList) {
            total += item.getTotalPrice();
        }
        overAllAmount.setText("Total Price : " + total + "$");
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Broadcast ile gelen toplam miktarı günceller
//Bu BroadcastReceiver, uygulama içinde "MyTotalAmount" adlı bir yayını dinliyor.
            //Intent'ten totalAmount verisi çıkarılır ve overAllAmount adlı TextView üzerinde gösterilir.
            int totalBill = intent.getIntExtra("totalAmount", 0);
            overAllAmount.setText("Total Amount: " + totalBill + "$");
        }
    };

}
