package com.example.bloodunity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.MyViewHolder> {

    String data1[], data2[], data3[];
    int images[];
    Context context;

    //constructor
    public recyclerViewAdapter(Context ct, String itemName[], String itemDesc[], String itemPrice[], int itemImages[]){
        context = ct;
        data1 = itemName;
        data2 = itemDesc;
        data3 = itemPrice;
        images = itemImages;
    }

    //oncreateviewholder sets xml layout page
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.shop_items_view, parent, false);
        return new MyViewHolder(view);
    }

    //binds the values to the widgets in the xml page
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemName.setText(data1[position]);
        holder.itemDescription.setText(data2[position]);
        holder.itemPrice.setText(data3[position]);
        holder.itemImage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    //initialising all values needed for widgets
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView itemName, itemDescription, itemPrice;
        ImageView itemImage;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }
}
