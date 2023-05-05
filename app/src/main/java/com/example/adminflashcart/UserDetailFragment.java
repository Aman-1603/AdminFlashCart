package com.example.adminflashcart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adminflashcart.Adaptor.AdaptorUserDetail;
import com.example.adminflashcart.Model.ModelUserDetail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserDetailFragment extends Fragment {

    RecyclerView recyclerView;


    FirebaseAuth firebaseAuth;

    private ArrayList<ModelUserDetail> ModelUserDetailArrayList;
    private AdaptorUserDetail adaptorUserDetail;


    public UserDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_detail, container, false);


        recyclerView = view.findViewById(R.id.promoRv);

        firebaseAuth = FirebaseAuth.getInstance();
        loadAllUserList();

        return view;
    }

    private void loadAllUserList() {

        ModelUserDetailArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        ModelUserDetailArrayList.clear();

                        for (DataSnapshot ds : snapshot.getChildren()){

                            ModelUserDetail modelUser = ds.getValue(ModelUserDetail.class);
                            ModelUserDetailArrayList.add(modelUser);



                        }

                        adaptorUserDetail = new AdaptorUserDetail(getContext(),ModelUserDetailArrayList);
                        recyclerView.setAdapter(adaptorUserDetail);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


    }

