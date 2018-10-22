package com.example.marti.amiclient.ui;


import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.net.URL;
import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marti.amiclient.MainActivity;
import com.example.marti.amiclient.R;
import com.example.marti.amiclient.estructura.ubicacion.UbicacionMedico;
import com.example.marti.amiclient.interfaces.drawer.DrawerLocker;
import com.example.marti.amiclient.settings.Constant;
import com.example.marti.amiclient.settings.map.DownloadDirectionsMapData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapViewUI extends Fragment implements OnMapReadyCallback {

    GoogleMap map=null;
    RequestQueue requestQueue;

    double longitudeOrigen = 0, latitudeOrigen = 0; // pos medico
    static boolean drawPolines = false;

    Marker marker = null;
    Timer timer;

    ArrayList<LatLng> points;
    PolylineOptions polylineOptions = null;

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

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                getUbicacionMedico(Constant.HTTP_DOMAIN_DVD+Constant.END_POINT_POSITION+Constant.SLASH+Constant.CONSEC_MOVSERV_ASIGNADO_A_MEDICO);

            }


        }, 0, 10000);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map=googleMap;
        polylineOptions = new PolylineOptions();
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


        if(drawPolines) {

            if(latitudeOrigen!=0&&longitudeOrigen!=0&&MainActivity.latitude!=0&& MainActivity.longitude!=0) {

                LatLng origen = new LatLng(latitudeOrigen, longitudeOrigen);
                LatLng destino = new LatLng(MainActivity.latitude, MainActivity.longitude);
           /* map.moveCamera(CameraUpdateFactory.newLatLng(origen));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(origen, 15));
            map.addMarker(new MarkerOptions().position(origen))
                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icono_corazon_verde));*/


                String url = getUrl(origen, destino);
                Log.d("URLis", url);

                DownloadDirectionsMapData downloadDirectionsMapData = new DownloadDirectionsMapData(map);
                downloadDirectionsMapData.execute(url);

            }
        }


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
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getResources().getString(R.string.google_play_services_key);


        return url;
    }

    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity());
        }

        return requestQueue;
    }

    public void getUbicacionMedico(String UrlQuest){

        requestQueue = getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlQuest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(map!=null) {
                            Log.i("mapUbi :", "success");
                            parseUbicacionMedResponse(response);
                        }else{
                            Log.i("mapUbi :", "map null");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { //errores de peticion
                Log.i("mapUbi :", "error");

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError { //autorizamos basic
                Map<String, String> headers = new HashMap<>();
                headers.put("Token", Constant.TOKEN);
                headers.put("Authorization",Constant.AUTH);
                Log.i("auth",Constant.TOKEN+" "+Constant.AUTH);
                return headers;
            }

        };
        requestQueue.add(stringRequest);
    }

    public void parseUbicacionMedResponse(String response) {

        Gson gson3 = new Gson();

        UbicacionMedico ubicacionMedico = new UbicacionMedico();

        ubicacionMedico = gson3.fromJson(response,UbicacionMedico.class);

        String latStr = ubicacionMedico.getMessage().getLat();
        String lngStr = ubicacionMedico.getMessage().getLng();

        if(latStr!=null&&lngStr!=null&&!latStr.equals("")&&!lngStr.equals("")){

            if(!latStr.equals("0")&&!lngStr.equals("0")&&MainActivity.latitude!=0&& MainActivity.longitude!=0){

                latitudeOrigen = Double.valueOf(latStr);
                longitudeOrigen = Double.valueOf(lngStr);

                    LatLng origen = new LatLng(latitudeOrigen, longitudeOrigen);
                    LatLng destino = new LatLng(MainActivity.latitude, MainActivity.longitude);

                    if(marker==null) {
                        map.moveCamera(CameraUpdateFactory.newLatLng(origen));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(origen, 15));
                        marker = map.addMarker(new MarkerOptions().position(origen));
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icono_ambulancia));
                        String url = getUrl(origen, destino);
                        Log.d("URLis", url);

                        DownloadDirectionsMapData downloadDirectionsMapData = new DownloadDirectionsMapData(map);
                        downloadDirectionsMapData.execute(url);
                    }else{
                        marker.setPosition(origen);
                        //points.add(origen);
                        //
                        polylineOptions.add(origen);
                        polylineOptions.width(10);
                        polylineOptions.color(Color.RED);
                        map.addPolyline(polylineOptions);
                    }

            }

        }

    }

}
