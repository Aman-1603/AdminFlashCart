package com.example.adminflashcart;

import static android.content.ContentValues.TAG;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class AdminHome_Fragment extends Fragment {


    CardView addbannercard,addadscard,cardview1,promotioncode,UserDataCard;

    TextView totalcustomerTv,TotalshopTv;


    public AdminHome_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_home_, container, false);


        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addbannercard = view.findViewById(R.id.addbannercard);
        addadscard = view.findViewById(R.id.addadscard);
        cardview1 = view.findViewById(R.id.cardview1);
        promotioncode = view.findViewById(R.id.promotioncode);
        totalcustomerTv = view.findViewById(R.id.totalcustomerTv);
        TotalshopTv = view.findViewById(R.id.TotalshopTv);
        UserDataCard = view.findViewById(R.id.UserDataCard);


        loadTotalCustomer();
        loadTotalShop();
//        LoadTotalProducts();






        addbannercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Add_Banner_Fragment newFragment = new Add_Banner_Fragment();


                // Get the fragment manager
                FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                // Begin a new transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the previous fragment with the new fragment
                fragmentTransaction.replace(R.id.fragment_container, newFragment);

                // Add the transaction to the back stack so the user can navigate back to the previous fragment
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();

            }
        });



        addadscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Add_Ads_Fragment newFragment = new Add_Ads_Fragment();


                // Get the fragment manager
                FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                // Begin a new transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the previous fragment with the new fragment
                fragmentTransaction.replace(R.id.fragment_container, newFragment);

                // Add the transaction to the back stack so the user can navigate back to the previous fragment
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();

            }
        });


        cardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GraphData_Fragment newFragment = new GraphData_Fragment();


                // Get the fragment manager
                FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                // Begin a new transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the previous fragment with the new fragment
                fragmentTransaction.replace(R.id.fragment_container, newFragment);

                // Add the transaction to the back stack so the user can navigate back to the previous fragment
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();

            }
        });



        promotioncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Promotion_Code_Fragment newFragment = new Promotion_Code_Fragment();


                // Get the fragment manager
                FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                // Begin a new transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the previous fragment with the new fragment
                fragmentTransaction.replace(R.id.fragment_container, newFragment);

                // Add the transaction to the back stack so the user can navigate back to the previous fragment
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();

            }
        });





        UserDataCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDetailFragment newFragment = new UserDetailFragment();


                // Get the fragment manager
                FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

                // Begin a new transaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the previous fragment with the new fragment
                fragmentTransaction.replace(R.id.fragment_container, newFragment);

                // Add the transaction to the back stack so the user can navigate back to the previous fragment
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();


            }
        });


    }



    private void loadTotalCustomer() {

        // Get a reference to the "users" node in your Firebase Realtime Database
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

// Attach a ValueEventListener to the "users" node
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Get the total number of child nodes under the "users" node
                long totalUsers = snapshot.getChildrenCount();
                // Do something with the total number of registered users
//                Log.d(TAG, "Total registered users: " + totalUsers);

                totalcustomerTv.setText(""+totalUsers);
            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors here
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });






    }

    private void loadTotalShop() {

// Get a reference to the "users" node in your Firebase Realtime Database
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

// Attach a ValueEventListener to the "users" node
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long totalCustomers = 0;

//                // Loop through all child nodes under the "users" node
//                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
//                    // Check if the current child node is a "normal" customer (not a seller)
//                    if (childSnapshot.child("accountType").getValue(String.class).equals("seller")) {
//                        // Increment the total count of "normal" customers
//                        totalCustomers++;
//                    }
//                }

                // Do something with the total number of "normal" customers
//                Log.d(TAG, "Total normal customers: " + totalCustomers);
//                TotalshopTv.setText(""+totalCustomers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors here
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });


    }

}