package com.example.bloodunity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class editPointsAdapter  extends FirebaseRecyclerAdapter<ViewUsersHelperClass,editPointsAdapter.viewHolder> {

    public editPointsAdapter(@NonNull FirebaseRecyclerOptions<ViewUsersHelperClass> options) {
        super(options);
    }

    //onbindviewholder uses getters to bind values from db to the layout
    @Override
    protected void onBindViewHolder(@NonNull editPointsAdapter.viewHolder holder, final int position, @NonNull ViewUsersHelperClass model) {
        //getting values from db to set textviews in the layout
        holder.userFName.setText(model.getFirst_name());
        holder.userLname.setText(model.getLast_name());
        holder.userBType.setText(model.getBloodType());
        holder.userCurrentPoints.setText(model.getPoints());

        //getting image from db to set to the imageview
        Glide.with(holder.profilePic.getContext())
                .load(model.getUser_picture())
                .error(R.drawable.img)
                .into(holder.profilePic);

        //onclick for when add points button is pressed
        holder.btnAddPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.profilePic.getContext())
                        .setContentHolder(new ViewHolder(R.layout.edit_points_container))
                        .setExpanded(true, 1000)
                        .create();

                //creates popup on the same page
                dialogPlus.show();

                View v = dialogPlus.getHolderView();

                Button btnConfirmPoints = v.findViewById(R.id.btnConfirmPoints);
                TextView labelCurrentPoints = v.findViewById(R.id.tvCurrentPoints);
                EditText addPoints = v.findViewById(R.id.etPoints);

                dialogPlus.show();

                //getting current points value from db to set to textview
                labelCurrentPoints.setText(model.getPoints());

                //onclick for when confirm add button is pressed
                btnConfirmPoints.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        
                        //taking string value of entered points and convert into int
                        String newPoints = addPoints.getText().toString();
                        int intNewPoints = Integer.parseInt(newPoints);

                        //taking string value of current points and convert into int
                        String oldPoints = labelCurrentPoints.getText().toString();
                        int intOldPoints = Integer.parseInt(oldPoints);

                        //simple math to add both int values and convert into string
                        int intTotalPoints = intNewPoints + intOldPoints;
                        String totalPoints = String.valueOf(intTotalPoints);

                        //takes the entered value into a map
                        Map<String, Object> map = new HashMap<>();
                        map.put("points", totalPoints);

                        //updates the value entered into db
                        FirebaseDatabase.getInstance().getReference().child("users")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.userFName.getContext(), "Points Added!", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();

                                        Intent intent = new Intent(view.getContext(), ViewUsers.class);
                                        v.getContext().startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.userFName.getContext(), "Error Adding Points!", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });
    }
    //oncreateviewholder setting the layout for the page
    @NonNull
    @Override
    public editPointsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_points_layout, parent, false);
        return new editPointsAdapter.viewHolder(view);
    }

    //initialising all needed widgets
    class viewHolder extends RecyclerView.ViewHolder {

        ImageView profilePic;
        TextView userFName, userLname, userBType, userCurrentPoints;
        Button btnEditPoints, btnAddPoints;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profilePic = (ImageView) itemView.findViewById(R.id.profilePic);
            userFName = (TextView) itemView.findViewById(R.id.userFirstName);
            userLname = (TextView) itemView.findViewById(R.id.userLastName);
            userBType = (TextView) itemView.findViewById(R.id.userBloodType);
            userCurrentPoints = (TextView) itemView.findViewById(R.id.userCurrentPoints);

            btnEditPoints = (Button) itemView.findViewById(R.id.btnConfirmPoints);
            btnAddPoints = (Button) itemView.findViewById(R.id.addPoints);
        }
    }

}
