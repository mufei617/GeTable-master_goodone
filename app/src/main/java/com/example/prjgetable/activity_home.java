package com.example.prjgetable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.navigation.findNavController;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
//import net.onefivefour.android.ebtimetracker.R;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Model.restaurant;

public class activity_home extends AppCompatActivity  implements ValueEventListener, ChildEventListener {


    ListView lvRestaurant;
    ArrayList<restaurant> listRestaurant;
    ArrayAdapter<restaurant> lvAdapter;
    restaurant ares;
    DatabaseReference restaurantDB,restaurantChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        initialize();
    }

    private void initialize() {

        ares = new restaurant();
        restaurantDB = FirebaseDatabase.getInstance().getReference("restaurants");
        restaurantChild = restaurantDB.child("200");
        restaurantChild.addChildEventListener(this);
        lvRestaurant  = findViewById(R.id.tvRestaurantList);
        listRestaurant = new ArrayList<>();
        lvAdapter = new ArrayAdapter<restaurant>(this, android.R.layout.simple_list_item_1,listRestaurant);
        lvRestaurant.setAdapter(lvAdapter);




    }



    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        if(snapshot.exists()){
            try {
                int restaurantId = Integer.valueOf(snapshot.child("id").getValue().toString());
                String restaurantName = snapshot.child("name").getValue().toString();
                String restaurantAddrress = snapshot.child("address").getValue().toString();
                String restauranttype = snapshot.child("type").getValue().toString();
                String restaurantoffer = snapshot.child("offer").getValue().toString();
                String restaurantPhone = snapshot.child("phone").getValue().toString();
                String restaurantDescription = snapshot.child("description").getValue().toString();
                restaurant Restaurant = new restaurant( Integer.valueOf(restaurantId),restaurantAddrress,restaurantoffer,restaurantName,restauranttype,restaurantPhone,restaurantDescription);
                listRestaurant.add(Restaurant);
                lvAdapter.notifyDataSetChanged();

            }catch (Exception e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }

        }
        else {
            Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}