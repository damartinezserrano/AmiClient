package com.example.marti.amiclient.ui;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.net.URL;
import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.marti.amiclient.R;
import com.example.marti.amiclient.interfaces.drawer.DrawerLocker;
import com.example.marti.amiclient.settings.map.DownloadDirectionsMapData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapViewUI extends Fragment implements OnMapReadyCallback {

    GoogleMap map=null;

    public MapViewUI() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        MapViewUI mapViewUI = new MapViewUI();
        return mapViewUI;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_view_ui, container, false);

        if (map == null) {
            SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);
        }


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       // ((DrawerLocker)getActivity()).toolbarBackground(R.color.transparent);
       // ((DrawerLocker)getActivity()).removeToolbar();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map=googleMap;

      /*  map.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-35.016, 143.321),
                        new LatLng(-34.747, 145.592),
                        new LatLng(-34.364, 147.891),
                        new LatLng(-33.501, 150.217),
                        new LatLng(-32.306, 149.248),
                        new LatLng(-32.491, 147.309)));

        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-35.016, 143.321)));
        map.animateCamera(CameraUpdateFactory.zoomTo(11));*/


        LatLng origen = new LatLng(10.9997609,-74.79688529999999);
        LatLng destino = new LatLng(10.9891167,-74.79982380000001);
        map.moveCamera(CameraUpdateFactory.newLatLng(origen));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(origen,15));
        map.addMarker(new MarkerOptions().position(origen))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icono_corazon_verde));


        String url = getUrl(origen,destino);
        Log.d("URLis", url);

        DownloadDirectionsMapData downloadDirectionsMapData = new DownloadDirectionsMapData(map);
        downloadDirectionsMapData.execute(url);


     /*   GoogleDirection.withServerKey(getResources().getString(R.string.google_play_services_key))
                .from(new LatLng(37.7681994, -122.444538))
                .to(new LatLng(37.7749003,-122.4034934))
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if(direction.isOK()) {
                            // Do something
                            Route route = direction.getRouteList().get(0);
                            map.addMarker(new MarkerOptions().position(new LatLng(37.7681994, -122.444538)));
                            map.addMarker(new MarkerOptions().position(new LatLng(37.7749003,-122.4034934)));

                            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
                            map.addPolyline(DirectionConverter.createPolyline(getActivity(), directionPositionList, 5, Color.RED));
                            setCameraWithCoordinationBounds(route);
                        } else {
                            // Do something
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                    }
                });*/

    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }
}
