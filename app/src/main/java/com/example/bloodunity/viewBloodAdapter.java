package com.example.bloodunity;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class viewBloodAdapter extends FirebaseRecyclerAdapter<viewBloodHelperClass,viewBloodAdapter.viewHolder>{

    public viewBloodAdapter(@NonNull FirebaseRecyclerOptions<viewBloodHelperClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull viewBloodHelperClass model) {
        holder.tvDonorAge.setText(model.getDonorAge());
        holder.tvDonorName.setText(model.getDonorName());
        holder.tvHospital.setText(model.getHospital());

        Glide.with(holder.bloodPic.getContext())
                .load(model.getBloodGroup())
                .error(R.drawable.img)
                .into(holder.bloodPic);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_blood_layout, parent, false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder{

        ImageView bloodPic;
        TextView tvDonorName, tvDonorAge, tvHospital;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            bloodPic = (ImageView) itemView.findViewById(R.id.bloodPic);
            tvDonorName = (TextView) itemView.findViewById(R.id.tvDonorName);
            tvDonorAge = (TextView) itemView.findViewById(R.id.tvDonorAge);
            tvHospital = (TextView) itemView.findViewById(R.id.tvHospital);
        }
    }

}
