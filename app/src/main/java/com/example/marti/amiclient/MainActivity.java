package com.example.marti.amiclient;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.example.marti.amiclient.interfaces.drawer.DrawerLocker;
import com.example.marti.amiclient.ui.LogInFragment;
import com.example.marti.amiclient.ui.MapViewUI;

public class MainActivity extends AppCompatActivity implements DrawerLocker {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MainAppThemeWithNoBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_PHONE_STATE},1);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.layout);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationView = findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.movil:
                        Fragment fg = MapViewUI.newInstance();
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                    default:
                        return true;

                }
            }
        });

        addDynamicFragment();

    }

    private void addDynamicFragment() {
        // TODO Auto-generated method stub
        // creating instance of the HelloWorldFragment.
        Fragment fg = LogInFragment.newInstance();
        //Fragment fg = LogInFragment.newInstance();
        // adding fragment to relative layout by using layout id
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).commit();
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawerLayout.setDrawerLockMode(lockMode);
    }

    @Override
    public void hideToolbar() {
        getSupportActionBar().hide();
    }

    @Override
    public void showToolbar() {
        getSupportActionBar().show();

    }

    @Override
    public void toolbarBackground(int color) {
        toolbar.setTitle("");
        toolbar.setBackgroundColor(getResources().getColor(color));

    }

    @Override
    public void hamburgerColor(int color) {
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(color));

    }

    @Override
    public void removeToolbar() {
        toolbar.setVisibility(View.GONE);

    }

    @Override
    public void openDrawer() {

        drawerLayout.openDrawer((int) Gravity.LEFT);
    }

    @Override
    public void closeDrawer() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer((int) Gravity.LEFT);
        }else{
            drawerLayout.openDrawer((int) Gravity.LEFT);
        }
    }

    @Override
    public void onBackPressed() {
            getSupportFragmentManager().popBackStack();
    }
}
