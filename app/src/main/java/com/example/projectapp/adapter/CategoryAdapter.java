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
import com.example.projectapp.activities.ShowAllActivity;
import com.example.projectapp.models.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;//activity veya fragment'a erişim
    List<CategoryModel> list;

    public CategoryAdapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list, parent, false));
        //category_list xml dosyasını şişirir ve liste elemanının görünümünü oluşturur.
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try {
            CategoryModel model = list.get(position);
            
            // URL'yi logla
            Log.d("CategoryAdapter", "Loading image from URL: " + model.getImg_url());

            // kategoriye ait resmi yükler
            Glide.with(context)
                    .load(model.getImg_url())
                    .placeholder(R.drawable.placeholder) // resim yüklenirken gösterilecek placeholder
                    .error(R.drawable.error_image)      // error resmi
                    .into(holder.catImg);


            //kategori adını textView'e ayarlar
            holder.catName.setText(model.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowAllActivity.class);
                    intent.putExtra("type", list.get(holder.getAdapterPosition()).getType());
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            Log.e("CategoryAdapter", "Error loading image: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
// ürün elemanlarının görünümüne  erişim
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView catImg;
        TextView catName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImg = itemView.findViewById(R.id.cat_img);
            catName = itemView.findViewById(R.id.cat_name);
        }
    }
}

