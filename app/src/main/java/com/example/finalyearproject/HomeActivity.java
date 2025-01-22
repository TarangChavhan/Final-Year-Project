package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalyearproject.Fragments.HomeFragment;
import com.example.finalyearproject.Fragments.MyProfileFragment;
import com.example.finalyearproject.Fragments.NotificationFragment;
import com.example.finalyearproject.Fragments.TaskFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home Activity");
        bottomNavigationView = findViewById(R.id.BottomNavigationHomeActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.BottomNavigationMenuHome);
    }
    @Override
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
    TaskFragment taskFragment = new TaskFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    MyProfileFragment myProfileFragment = new MyProfileFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.BottomNavigationMenuHome)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,homeFragment).commit();
        } else if (item.getItemId()==R.id.BottomNavigationMenuMyProfile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,myProfileFragment).commit();
        }
        else if (item.getItemId()==R.id.BottomNavigationMenuNotification) {
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,notificationFragment).commit();
        }
        else if (item.getItemId()==R.id.BottomNavigationMenuTask) {
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,taskFragment).commit();
        }
        return true;
    }
}