package com.example.projectapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.projectapp.models.NewProductsModel;

import java.util.List;

public class NewProductsAdapter  extends RecyclerView.Adapter<NewProductsAdapter.ViewHolder> {

    private Context context;
    private List<NewProductsModel> list;


    public NewProductsAdapter(Context context, List<NewProductsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_products,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Glide ile görselleri yükle
        Glide.with(context)
                .load(list.get(position).getImg_url())
                .placeholder(R.drawable.loading_image) // Yükleniyor görseli
                .error(R.drawable.default_image) // Varsayılan hata görseli
                .into(holder.newImg);

        // Ürün bilgilerini göster
        holder.newName.setText(list.get(position).getName());
        holder.newPrice.setText(String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed",list.get(position));
                context.startActivity(intent);

            }
        });


        // Log ile kontrol et
        Log.d("AdapterData", "Ürün Adı: " + list.get(position).getName());
        Log.d("AdapterData", "Görsel URL: " + list.get(position).getImg_url());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView newImg;
        TextView newName, newPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            newImg = itemView.findViewById(R.id.new_img);
            newName = itemView.findViewById(R.id.new_product_name);
            newPrice = itemView.findViewById(R.id.new_price);





        }


    }


}
