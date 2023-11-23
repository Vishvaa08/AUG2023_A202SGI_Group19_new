package com.example.bloodunity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

public class deleteBloodAdapter extends FirebaseRecyclerAdapter<viewBloodHelperClass,deleteBloodAdapter.viewHolder>{


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public deleteBloodAdapter(@NonNull FirebaseRecyclerOptions<viewBloodHelperClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, final int position, @NonNull viewBloodHelperClass model) {
        holder.tvDonorAge.setText(model.getDonorAge());
        holder.tvDonorName.setText(model.getDonorName());
        holder.tvHospital.setText(model.getHospital());

        Glide.with(holder.bloodPic.getContext())
                .load(model.getBloodGroup())
                .error(R.drawable.img)
                .into(holder.bloodPic);

        holder.btnDeleteBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.tvDonorName.getContext());
                builder.setTitle("Confirm Delete ?");
                builder.setMessage("Action can't be undone!");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Blood")
                                .child(getRef(position).getKey()).removeValue();

                        Intent intent = new Intent(view.getContext(), viewBlood.class);
                        view.getContext().startActivity(intent);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.tvDonorName.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_blood_layout, parent, false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder{

        ImageView bloodPic;
        TextView tvDonorName, tvDonorAge, tvHospital;
        Button btnDeleteBlood;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            bloodPic = (ImageView) itemView.findViewById(R.id.bloodPic);
            tvDonorName = (TextView) itemView.findViewById(R.id.tvDonorName);
            tvDonorAge = (TextView) itemView.findViewById(R.id.tvDonorAge);
            tvHospital = (TextView) itemView.findViewById(R.id.tvHospital);
            btnDeleteBlood = (Button) itemView.findViewById(R.id.btnDeleteBlood);
        }
    }

}
