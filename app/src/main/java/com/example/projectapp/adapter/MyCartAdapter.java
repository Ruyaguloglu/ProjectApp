package com.example.projectapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.R;
import com.example.projectapp.models.MyCartModel;

import java.util.List;

import javax.annotation.Nonnull;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder>{
   Context context;//activity veya fragment'a erişim
   List<MyCartModel> list;
    int totalAmount = 0;

   public MyCartAdapter(Context context, List<MyCartModel> list) {
        this.context = context;
        this.list = list;

   }
    // my_cart_item.xml layout dosyasını kullanarak yeni bir görünüm oluşturur
    // LayoutInflater ile XML dosyasını Java View nesnesine dönüştürür
    @NonNull
    @Override
   //her öge için view oluşturulur
    public ViewHolder onCreateViewHolder(@Nonnull ViewGroup parent , int viewType){
       return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));
   }

    @Override
    //  Her bir öğenin verilerini görünüme bağlar
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {
        MyCartModel model = list.get(position);
        
        try {
            holder.date.setText(model.getCurrentDate());
            holder.time.setText(model.getCurrentTime());
            holder.name.setText(model.getProductName());
            holder.price.setText(model.getProductPrice() + "$");
            holder.totalQuantity.setText(model.getTotalQuantity());
            holder.totalPrice.setText(String.valueOf(model.getTotalPrice()) + "$");
            
            Log.d("MyCartAdapter", "Item bound: " + model.getProductName() + 
                  " Price: " + model.getTotalPrice());
        } catch (Exception e) {
            Log.e("MyCartAdapter", "Error binding item at position " + position, e);
        }

        calculateTotalAmount();
    }

    private void calculateTotalAmount() {
        totalAmount = 0;
        // Listedeki her ürün için döngü
        for (MyCartModel item : list) {
            try {
                totalAmount += item.getTotalPrice();
                Log.d("MyCartAdapter", "Added to total: " + item.getTotalPrice());
            } catch (Exception e) {
                Log.e("MyCartAdapter", "Error calculating total", e);
            }
        }

        Log.d("MyCartAdapter", "Final total: " + totalAmount);
        // Toplam tutarı diğer bileşenlere broadcast ile gönder
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalAmount);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent); //toplam tutarı başka bileşene iletmek içindir
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //Her bir sepet öğesinin görünüm elemanlarını tutar
    public class ViewHolder extends RecyclerView.ViewHolder{
       TextView name,price,date,time,totalQuantity,totalPrice;

       public ViewHolder(@NonNull View itemView){
           super(itemView);
           name = itemView.findViewById(R.id.product_name);
           price = itemView.findViewById(R.id.product_price);
           date = itemView.findViewById(R.id.current_date);
           time = itemView.findViewById(R.id.current_time);
           totalQuantity = itemView.findViewById(R.id.total_quantity);
           totalPrice = itemView.findViewById(R.id.total_price);




       }
    }
}

