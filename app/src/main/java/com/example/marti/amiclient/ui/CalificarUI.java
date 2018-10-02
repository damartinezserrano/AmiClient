package com.example.marti.amiclient.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marti.amiclient.MainActivity;
import com.example.marti.amiclient.R;
import com.example.marti.amiclient.estructura.calificaciones.CalificarServicio;
import com.example.marti.amiclient.interfaces.drawer.DrawerLocker;
import com.example.marti.amiclient.settings.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalificarUI extends Fragment {

    ImageView cora1, cora2, cora3, cora4, cora5;
    List<Integer> coras = new ArrayList<Integer>();
    View view2;
    int calificacionServicio;
    EditText editTextObservaciones;
    Button enviar;
    String observaciones;

    GsonBuilder gsonBuilder;
    Gson gson;

    RequestQueue requestQueue;

    CalificarServicio calificarServicio;

    public CalificarUI() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        CalificarUI calificarUI = new CalificarUI();
        return calificarUI;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calificar_ui, container, false);
        view2 = view;

        coras.add(R.id.c1);
        coras.add(R.id.c2);
        coras.add(R.id.c3);
        coras.add(R.id.c4);
        coras.add(R.id.c5);

        cora1=view.findViewById(R.id.c1);
        cora2=view.findViewById(R.id.c2);
        cora3=view.findViewById(R.id.c3);
        cora4=view.findViewById(R.id.c4);
        cora5=view.findViewById(R.id.c5);

        editTextObservaciones=view.findViewById(R.id.observaciones);
        enviar=view.findViewById(R.id.enviar);

        cora1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             fillCoras(R.id.c1,view);
             calificacionServicio=1;
            }
        });

        cora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillCoras(R.id.c2,view2);
                calificacionServicio=2;
            }
        });

        cora3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillCoras(R.id.c3,view2);
                calificacionServicio=3;
            }
        });

        cora4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillCoras(R.id.c4,view2);
                calificacionServicio=4;
            }
        });

        cora5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillCoras(R.id.c5,view2);
                calificacionServicio=5;
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observaciones=editTextObservaciones.getText().toString();
                putCalificarServicio(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.CALIFICAR_SERVICIO,Constant.CONSEC_MOVSERV_ACTUAL,calificacionServicio,observaciones);
            }
        });


        return view;
    }


    public void fillCoras(int rateCora, View view){
        Iterator<Integer> myListIterator = coras.iterator();
       // while (myListIterator.hasNext()) {
        for (Integer number : coras) {

            if(number!=rateCora) {
                view.findViewById(number).setBackground(getResources().getDrawable(R.drawable.corazon_lleno));
            }else{
                view.findViewById(rateCora).setBackground(getResources().getDrawable(R.drawable.corazon_lleno));
                break;
            }
        }
      //  }
    }


    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity());
        }

        return requestQueue;
    }

    public void putCalificarServicio(String URL, String codserv, int calif, String observ) {



        requestQueue = getRequestQueue();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL, putCalificacionBodyJSON(codserv,calif,observ), //hacemos la peticion post
                response -> {

                    Log.i("LogInFragment", "Se ha realizado el put calificar con exito");
                    getActivity().getSupportFragmentManager().popBackStack();
                    ((MainActivity)getActivity()).getCalificacionesPendientes(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.LISTAR_CALIFICACIONES_PENDIENTES + Constant.SLASH + Constant.ID);

                }, error -> {

            // parseLogInError(error);
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError { //autorizamos basic
                Map<String, String> headers = new HashMap<>();
                headers.put("Token", Constant.TOKEN);
                headers.put("Authorization",Constant.AUTH);
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };

        requestQueue.add(request);
    }

    public JSONObject putCalificacionBodyJSON(String codserv, int calif, String observ) { //construimos el json
        //primero json device
        String loginBody="";
        JSONObject jsonObject=null;

        calificarServicio = new CalificarServicio();
        calificarServicio.setConsec_movserv(codserv); //3282500
        calificarServicio.setCalificacion_servicio(String.valueOf(calif)); //1 si , 2 no
        calificarServicio.setObservacion_calificacion(observ);



        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        loginBody = gson.toJson(calificarServicio);
        Log.i("loginRbody",loginBody);

        try {
            jsonObject = new JSONObject(loginBody);
            Log.i("jsonObject",jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
