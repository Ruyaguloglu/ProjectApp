package com.example.projectapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectapp.R;
import com.example.projectapp.activities.DetailedActivity;
import com.example.projectapp.models.PopularProductsModel;

import java.util.List;

public class PopularProductsAdapter extends RecyclerView.Adapter<PopularProductsAdapter.ViewHolder> {

    private Context context;//Adaptörün çalıştığı bağlam
    private List<PopularProductsModel> popularProductsModelList;

    //RecyclerView için bir adaptör sınıfı
    public PopularProductsAdapter(Context context, List<PopularProductsModel> popularProductsModelList) {
        this.context = context; //parametreler sınıf içindeki değişkenlere atanır.
        this.popularProductsModelList = popularProductsModelList;
    }

    @NonNull
    @Override
    // RecyclerView her bir öğesi için bir görünüm (View) oluşturur
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater kullanımı doğru şekilde yapılıyor
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items, parent, false);
        return new ViewHolder(view);
    }
//Her bir liste ögesini göstermek için
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Güncel pozisyondaki model verisini al
        PopularProductsModel currentItem = popularProductsModelList.get(position);

        // Glide ile resmi yükle
        Glide.with(context)
                .load(currentItem.getImg_url())
                .into(holder.imageView);

        // ürün adını ve fiyatını kullanıcıya gösterir
        holder.name.setText(currentItem.getName());
        holder.price.setText(String.valueOf(currentItem.getPrice()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tıklama anındaki güncel pozisyonu al
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, DetailedActivity.class);
                    intent.putExtra("detailed", popularProductsModelList.get(currentPosition));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularProductsModelList.size();
    }

    //performansı arttırmak için görünüm referansları önbelleğe alınır
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,price;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            imageView = itemView.findViewById(R.id.all_img);
            name = itemView.findViewById(R.id.all_product_name);
            price = itemView.findViewById(R.id.all_price);

        }
    }
}

