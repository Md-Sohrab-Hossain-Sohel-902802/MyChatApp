package com.example.mychatapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mychatapp.R;
import com.example.mychatapp.Users;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<Users> usersList;
    private static OnItemclickListener listener;

    public MyAdapter(Context context, List<Users> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.users_single_layout,viewGroup,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Users muser=usersList.get(position);
        holder.singleuserName.setText(muser.getName());
        holder.singleUserStatus.setText(muser.getStatus());
        Picasso.with(context).load(muser.getThumb_image()).placeholder(R.drawable.avatarimage).into(holder.singleUserImage);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
/*
    public void filterList(ArrayList<StudentDetails> filteredList){
            studentDetailsList=filteredList;
            notifyDataSetChanged();
    }*/
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView singleuserName, singleUserStatus;
        ImageView singleUserImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            singleUserImage = itemView.findViewById(R.id.user_single_image);
            singleuserName = itemView.findViewById(R.id.user_single_name);
            singleUserStatus = itemView.findViewById(R.id.user_single_status);


            itemView.setOnClickListener(this);

        }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            int position=getAdapterPosition();
            if(position!=RecyclerView.NO_POSITION){
                listener.onItemClick(position);
            }
        }
    }
}


    public interface  OnItemclickListener{
        void onItemClick(int position);



    }
    public void setOnitemclickListener(OnItemclickListener listener){
        this.listener=listener;

    }

}
