package com.example.marti.amiclient;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.marti.amiclient.interfaces.drawer.DrawerLocker;
import com.example.marti.amiclient.settings.Constant;
import com.example.marti.amiclient.settings.LocationAddress;
import com.example.marti.amiclient.settings.address.DownloadAddressData;
import com.example.marti.amiclient.ui.LogInFragment;
import com.example.marti.amiclient.ui.MapViewUI;
import com.example.marti.amiclient.ui.PerfilUI;
import com.example.marti.amiclient.ui.TriageACUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements DrawerLocker {

    Context context;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Toolbar toolbar;

    LocationManager lm;
    LocationListener ll;

    double longitude = 0, latitude = 0;
    String direccionActual="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MainAppThemeWithNoBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.layout);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationView = findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.movil:
                        if (!Constant.servicioAltaPrioridad) {
                            Fragment fg = MapViewUI.newInstance();
                            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                        } else {
                            Fragment fg = TriageACUI.newInstance();
                            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();

                        }
                        closeDrawer();
                        return true;

                    case R.id.perfil:
                        Fragment fg = PerfilUI.newInstance();
                        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                        closeDrawer();
                        return true;

                    default:
                        return true;

                }
            }
        });

        addDynamicFragment();

    }

    @Override
    protected void onStart() {
        super.onStart();

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        ll = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
              ubicarDispositivo(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestSingleUpdate(lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ? LocationManager.GPS_PROVIDER : LocationManager.NETWORK_PROVIDER, ll, null);

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
    public void editHeaderName(String name) {
        View header = navigationView.getHeaderView(0);
        TextView nombreHeader = (TextView) header.findViewById(R.id.nombreusuario);
        nombreHeader.setText(name);
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



    public void ubicarDispositivo(Location location) {

        if (location != null) {

            latitude = location.getLatitude();
            longitude = location.getLongitude(); //obtenemos ubicacion actual


            Log.i("Localizacion", "localizado");
            if (latitude != 0 && longitude != 0) { //verifica si el dispositivo esta registrado y procede a obtener su ubicacion
                direccionActual = new LocationAddress(context, latitude, longitude).getLocationAddress();
                //Log.i("Localizacion", direccionActual);

            if(direccionActual.equals("AddressNotFound") || direccionActual.equals("")) {

                String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + location.getLatitude() + ","
                        + location.getLongitude() + "&sensor=false&language=fr";

                DownloadAddressData downloadAddressData = new DownloadAddressData();
                downloadAddressData.execute(googleMapUrl);

            }else{
                Constant.DIRECCION_ACTUAL_GOOGLE = direccionActual;
            }




            }

        }
    }

}
