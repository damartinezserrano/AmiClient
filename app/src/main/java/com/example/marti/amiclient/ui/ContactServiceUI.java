package com.example.marti.amiclient.ui;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marti.amiclient.R;
import com.example.marti.amiclient.estructura.EstructuraSolicitarLlamada;
import com.example.marti.amiclient.estructura.ciudad.Ciudad;
import com.example.marti.amiclient.estructura.lineas.Lineas;
import com.example.marti.amiclient.estructura.persona.Identificacion;
import com.example.marti.amiclient.settings.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactServiceUI extends Fragment {

    ImageView callButton;
    Spinner spinnerC;
    TextView numero;
    TextView nombreC;
    CardView cardView;

    RequestQueue requestQueue;


    String[] nombreL;
    String[] telefonoL;

    GsonBuilder gsonBuilder;
    Gson gson;

    public ContactServiceUI() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        ContactServiceUI contactServiceUI = new ContactServiceUI();
        return contactServiceUI;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_service_ui, container, false);

        numero = view.findViewById(R.id.numero);
        nombreC = view.findViewById(R.id.ciudadfield);
        callButton = view.findViewById(R.id.call);
        cardView = view.findViewById(R.id.linecard);

        spinnerC = view.findViewById(R.id.selCiudadSpinner);
        getLineasAtencion(Constant.HTTP_DOMAIN_DVD+Constant.END_POINT_LINEAS,spinnerC);



        callButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {

               if(ServiceOptionsUI.llamarDirecto) {

                   Intent intent = new Intent(Intent.ACTION_CALL);

                   intent.setData(Uri.parse("tel:" + numero.getText().toString()));
                   try {
                       getActivity().startActivity(intent);
                   } catch (Exception e) {
                       e.printStackTrace();
                   }

               }else{

                   if(ServiceOptionsUI.solLlamada) {

                       postSolicitarLlamada(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.SOLICITAR_LLAMADA,Constant.ID,numero.getText().toString());
                   }
               }

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

                if(position!=0) {
                    cardView.setVisibility(View.VISIBLE);
                    nombreC.setText(nombreL[position]);
                    numero.setText(telefonoL[position]);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity());
        }

        return requestQueue;
    }

    public void getLineasAtencion(String UrlQuest, Spinner spinnerC) {

        requestQueue = getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlQuest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        parseLineasAtResponse(response,spinnerC);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { //errores de peticion
                Log.i("ServiceInfoUI :", "error");
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError { //autorizamos basic
                Map<String, String> headers = new HashMap<>();

                headers.put("Content-Type", "application/json");

                return headers;
            }

        };
        requestQueue.add(stringRequest);
    }

    public void parseLineasAtResponse(String response, Spinner spinnerCiud) {

        Gson gson3 = new Gson();
        Lineas[] lineas;
        lineas = gson3.fromJson(response,Lineas[].class);


        nombreL = new String[lineas.length+1];
        nombreL[0]="Seleccionar Linea de Atención";
        telefonoL = new String[lineas.length+1];
        telefonoL[0]="telefonos atencion";

        for (int i = 1 ; i<=lineas.length ; i++){
            nombreL[i]=lineas[i-1].getNombre();
            telefonoL[i]=lineas[i-1].getTelefono();
        }

        final List<String> ciudadesList = new ArrayList<>(Arrays.asList(nombreL));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.custom_spinner,
                ciudadesList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCiud.setAdapter(adapter);

        spinnerCiud.setSelection(Constant.ciudad_pos);
    }


    public void postSolicitarLlamada(String URL, String id, String tel) {



        requestQueue = getRequestQueue();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, solicitarLlamadaBodyJSON(id,tel), //hacemos la peticion post
                response -> {

                    Log.i("ContactServiceUI", "Se ha realizado el user post con exito");

                    new AlertDialog.Builder(getContext())
                            .setTitle("Llamada Solicitada")
                            .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setMessage("Su solicitud ha sido realizada exitosamente.")
                            .show();

                   // parseLogInResponse2(response);

                }, error -> {
                    Log.i("ContactServiceUI", "Error");
                    parseLogInError(error);
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError { //autorizamos basic
                Map<String, String> headers = new HashMap<>();
                String auth = "Basic QW1pQXBwQWRtaW5pc3RyYWRvcjoqQW1pQWRtaW5BcHAyMDE4Kg==";
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;
            }

        };

        requestQueue.add(request);
    }

    public JSONObject solicitarLlamadaBodyJSON(String id, String telefono) { //construimos el json
        //primero json device
        String solicitarLlamadaBody="";
        JSONObject jsonObject=null;

        EstructuraSolicitarLlamada estructuraSolicitarLlamada = new EstructuraSolicitarLlamada();
        estructuraSolicitarLlamada.setPersona_cc(id);
        estructuraSolicitarLlamada.setTelefono(telefono);

        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        solicitarLlamadaBody = gson.toJson(estructuraSolicitarLlamada);
        Log.i("body",solicitarLlamadaBody);

        try {
            jsonObject = new JSONObject(solicitarLlamadaBody);
            Log.i("jsonObject",jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public void parseLogInError(VolleyError error) {

        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            boolean estado = data.getBoolean("estado");
            String mensaje = data.getString("mensaje");
            new AlertDialog.Builder(getContext())
                    .setTitle("Llamada Solicitada")
                    .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setMessage(mensaje)
                    .show();
            Log.i("LogInFragment", "Ha ocurrido un error en el Login : "+estado+" , "+mensaje);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

}
