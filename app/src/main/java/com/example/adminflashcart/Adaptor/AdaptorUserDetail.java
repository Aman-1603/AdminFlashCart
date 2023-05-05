package com.example.adminflashcart.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminflashcart.Model.ModelUserDetail;
import com.example.adminflashcart.R;

import java.util.ArrayList;

public class AdaptorUserDetail extends RecyclerView.Adapter<AdaptorUserDetail.HolderCartItem> {

    private Context context;
    private ArrayList<ModelUserDetail> ModelUserDetailArrayList;

    public AdaptorUserDetail(Context context, ArrayList<ModelUserDetail> ModelUserDetailArrayList1) {
        this.context = context;
        this.ModelUserDetailArrayList = ModelUserDetailArrayList1;
    }

    @NonNull
    @Override
    public HolderCartItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_all_user,parent,false);

        return new HolderCartItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCartItem holder, int position) {

        //get data


        ModelUserDetail modelUserHomePromoCode =ModelUserDetailArrayList.get(position);

        String name = modelUserHomePromoCode.getName();
        String email = modelUserHomePromoCode.getEmail();
        String phone = modelUserHomePromoCode.getPhone();
        String city = modelUserHomePromoCode.getCity();
        String online = modelUserHomePromoCode.getOnline();



        holder.UserNameTv.setText(name);
        holder.UserEmailTv.setText(email);
        holder.UserPhoneTv.setText(phone);
        holder.UserCityTv.setText(city);
        holder.UserOnlineTv.setText(online);


    }



    @Override
    public int getItemCount() {
        return ModelUserDetailArrayList.size();
    }

    //view holder class

    class HolderCartItem extends RecyclerView.ViewHolder{



        ImageView promoImage;

        //init view
        TextView UserNameTv,UserEmailTv,UserPhoneTv,UserCityTv,UserOnlineTv;

        public HolderCartItem(@NonNull View itemView) {
            super(itemView);

            UserNameTv = itemView.findViewById(R.id.UserNameTv);
            UserEmailTv = itemView.findViewById(R.id.UserEmailTv);
            UserPhoneTv = itemView.findViewById(R.id.UserPhoneTv);
            UserCityTv = itemView.findViewById(R.id.UserCityTv);
            UserOnlineTv = itemView.findViewById(R.id.UserOnlineTv);



        }
    }

}





