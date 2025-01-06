package com.example.projectapp.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.projectapp.R;
import com.example.projectapp.activities.ShowAllActivity;
import com.example.projectapp.adapter.CategoryAdapter;
import com.example.projectapp.adapter.NewProductsAdapter;
import com.example.projectapp.adapter.PopularProductsAdapter;
import com.example.projectapp.models.CategoryModel;
import com.example.projectapp.models.NewProductsModel;
import com.example.projectapp.models.PopularProductsModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    TextView catShowAll,popularShowAll;



    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    RecyclerView catRecyclerview, newProductRecyclerview,popularRecyclerview;

    CategoryAdapter categoryAdapter;

    NewProductsAdapter newProductsAdapter;
    List<CategoryModel> categoryModelList;

    List<NewProductsModel> newProductsModelList;

    //popular products

    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;
    FirebaseFirestore db;

    public HomeFragment() {
        // Boş constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(getActivity());
        catRecyclerview = root.findViewById(R.id.rec_category);
        newProductRecyclerview = root.findViewById(R.id.new_product_rec);
        popularRecyclerview = root.findViewById(R.id.popular_rec);

        catShowAll = root.findViewById(R.id.category_see_all);
        popularShowAll = root.findViewById(R.id.popular_see_all);


        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent  intent = new Intent(getContext(), ShowAllActivity.class);
                intent.putExtra("type", "category"); // Kategori türünü belirtmek için
                startActivity(intent);


            }
        });
        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                intent.putExtra("type", "popular"); // Popular ürünler türünü belirtmek için
                startActivity(intent);
            }
        });






        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);
        // Image Slider
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.banner1, "Discount On Shoes Items", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2, "Discount On Perfume", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3, "70% OFF", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);

        progressDialog.setTitle("Welcome To My ECommerce App");
        progressDialog.setMessage("please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        // Category RecyclerView
        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModelList);
        catRecyclerview.setAdapter(categoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            CategoryModel categoryModel = document.toObject(CategoryModel.class);
                            categoryModelList.add(categoryModel);
                            linearLayout.setVisibility(View.VISIBLE);

                            progressDialog.dismiss();
                        }
                        categoryAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        Log.e("Firebase", "Category Error: " + task.getException());
                    }
                });


        //New Products RecyclerView
        newProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(), newProductsModelList);
        newProductRecyclerview.setAdapter(newProductsAdapter);


// New Products RecyclerView
        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        newProductsModelList.clear();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            try {
                                NewProductsModel model = new NewProductsModel();
                                model.setDescription(document.getString("description"));
                                model.setName(document.getString("name"));
                                model.setRating(document.getString("rating"));
                                model.setImg_url(document.getString("img_url"));

                                // Firebase'den string olarak gelen price'ı int'e çeviriyoruz
                                String priceStr = document.getString("price");
                                if (priceStr != null) {
                                    model.setPrice(Integer.parseInt(priceStr));
                                }

                                newProductsModelList.add(model);
                                Log.d("FirebaseData", "New Product added: " + model.getName());

                            } catch (Exception e) {
                                Log.e("FirebaseData", "Error parsing product: " + e.getMessage());
                            }
                        }
                        newProductsAdapter.notifyDataSetChanged();

                    } else {
                        Log.e("FirebaseData", "Error getting documents: ", task.getException());
                        if (getActivity() != null) {
                            Toast.makeText(getActivity(), "Error loading products", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            //Popular Products
        popularRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularProductsModelList = new ArrayList<>();
        popularProductsAdapter = new PopularProductsAdapter(getContext(), popularProductsModelList);
        popularRecyclerview.setAdapter(popularProductsAdapter);

        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            try {
                                PopularProductsModel model = document.toObject(PopularProductsModel.class);
                                popularProductsModelList.add(model);
                                Log.d("FirebaseData", "Product added: " + model.getName());
                            } catch (Exception e) {
                                Log.e("FirebaseData", "Error parsing product: " + e.getMessage());
                            }
                        }
                        popularProductsAdapter.notifyDataSetChanged();
                    } else {
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Toast.makeText(getActivity(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseData", "Error loading products: " + errorMessage);
                    }
                });


        return root;
    }
}





