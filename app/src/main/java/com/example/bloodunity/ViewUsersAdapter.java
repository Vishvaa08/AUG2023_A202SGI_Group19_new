package com.example.bloodunity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ViewUsersAdapter extends FirebaseRecyclerAdapter<ViewUsersHelperClass,ViewUsersAdapter.viewHolder>{

    public ViewUsersAdapter(@NonNull FirebaseRecyclerOptions<ViewUsersHelperClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull ViewUsersHelperClass model) {
        holder.userFirstName.setText(model.getFirst_name());
        holder.userLastName.setText(model.getLast_name());
        holder.userBloodType.setText(model.getBloodType());
        holder.userPoints.setText(model.getPoints());

        Glide.with(holder.profilePic.getContext())
                .load(model.getUser_picture())
                .error(R.drawable.img)
                .into(holder.profilePic);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_users_view, parent, false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder{

        ImageView profilePic;
        TextView userFirstName, userLastName, userBloodType, userPoints;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profilePic = (ImageView) itemView.findViewById(R.id.profilePic);
            userFirstName = (TextView) itemView.findViewById(R.id.userFirstName);
            userLastName = (TextView) itemView.findViewById(R.id.userLastName);
            userBloodType = (TextView) itemView.findViewById(R.id.userBloodType);
            userPoints = (TextView) itemView.findViewById(R.id.userCurrentPoints);
        }
    }

}
