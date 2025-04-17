package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject.Fragments.HomeFragment;
import com.example.finalyearproject.Fragments.MyProfileFragment;
import com.example.finalyearproject.Fragments.AddEventFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BottomNavigationView bottomNavigationView;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setTitle("Home");
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.BottomNavigationHomeActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.BottomNavigationMenuHome);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.home_page_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.HomePageMenuLogout)
        {
            Intent intent = new Intent(HomeActivity.this, Login.class);
            startActivity(intent);
        } else if (item.getItemId()==R.id.HomePageMenuMyOffer) {

        } else if (item.getItemId()==R.id.HomePageMenuMyProfile) {
            Intent intent= new Intent(HomeActivity.this,MyProfileActivity.class);
            startActivity(intent);
        } else if (item.getItemId()==R.id.HomePageMenuScanner) {
            Intent intent = new Intent(HomeActivity.this, QrCodeActivity.class);
            startActivity(intent);
        }
        return true;
    }
    HomeFragment homeFragment = new HomeFragment();
    AddEventFragment addEventFragment = new AddEventFragment();
    MyProfileFragment myProfileFragment = new MyProfileFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.BottomNavigationMenuHome)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,homeFragment).commit();
        } else if (item.getItemId()==R.id.BottomNavigationMenuMyProfile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,myProfileFragment).commit();
        }

        return true;
    }
}