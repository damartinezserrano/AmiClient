package com.example.marti.amiclient.ui;


import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marti.amiclient.MainActivity;
import com.example.marti.amiclient.R;
import com.example.marti.amiclient.estructura.EstructuraSolicitarLlamada;
import com.example.marti.amiclient.interfaces.drawer.DrawerLocker;
import com.example.marti.amiclient.settings.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceOptionsUI extends Fragment {

    LinearLayout ingresarinfo, solicitarLlamada, llamarDir;
    TextView textViewInfo,textViewSol,textViewDir;
    public static boolean solLlamada=false, llamarDirecto=false;
    GsonBuilder gsonBuilder;
    Gson gson;
    RequestQueue requestQueue;


    public ServiceOptionsUI() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        ServiceOptionsUI serviceOptionsUI = new ServiceOptionsUI();
        return serviceOptionsUI;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_options_ui, container, false);

        ((DrawerLocker)getActivity()).showToolbar();
        ((DrawerLocker)getActivity()).toolbarBackground(R.color.white);
        ((DrawerLocker)getActivity()).hamburgerColor(R.color.colorPrimary);




        ingresarinfo = view.findViewById(R.id.ingresarinfo);
        ingresarinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Constant.formularioOtros) {
                    Fragment fg = ServiceInfoUI.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                }else{
                    Fragment fg = ServiceRequestUI.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                }

            }
        });

        solicitarLlamada = view.findViewById(R.id.solicitarllamada);
        solicitarLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solLlamada=true;
                llamarDirecto=false;
                popupSolicitarLlamada();
                //Fragment fg = ContactServiceUI.newInstance();
                //getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();

            }
        });

        llamarDir = view.findViewById(R.id.llamardirecto);
        llamarDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamarDirecto=true;
                solLlamada=false;
                Fragment fg = ContactServiceUI.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();

            }
        });

        textViewInfo  = view.findViewById(R.id.infoservicio);
        Bitmap drawable =BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.icono_hoja_verde);
        int size = (textViewInfo.getLineHeight()*2);
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(drawable, size, size, true));
        textViewInfo.setCompoundDrawablesRelativeWithIntrinsicBounds(d, null, null, null);

        textViewSol  = view.findViewById(R.id.llamadasol);
          drawable =BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.icono_auriculares_verde);
          size = (textViewSol.getLineHeight()*2);
          d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(drawable, size, size, true));
        textViewSol.setCompoundDrawablesRelativeWithIntrinsicBounds(d, null, null, null);

        textViewDir  = view.findViewById(R.id.llamadadir);
         drawable =BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.icono_telefono_verde);
          size = (textViewDir.getLineHeight()*2);
          d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(drawable, size, size, true));
        textViewDir.setCompoundDrawablesRelativeWithIntrinsicBounds(d, null, null, null);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).getCalificacionesPendientes(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.LISTAR_CALIFICACIONES_PENDIENTES + Constant.SLASH + Constant.ID);

    }

    public void popupSolicitarLlamada(){

        new AlertDialog.Builder(getContext())
                .setTitle("Solicitar llamada")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        postSolicitarLlamada(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.SOLICITAR_LLAMADA,Constant.ID,Constant.CELULAR);

                    }
                })
                .setNegativeButton("No",null)
                .setMessage("¿Desea solicitar una llamada?")
                .show();
    }

    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity());
        }

        return requestQueue;
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
                            .setMessage("La solicitud de llamada se ha realizado correctamente. En breve el servicio de AMI lo contactará")
                            .show();

                    // parseLogInResponse2(response);

                }, error -> {
            Log.i("ContactServiceUI", "Error");
            parseLogInError(error);
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError { //autorizamos basic
                Map<String, String> headers = new HashMap<>();
                headers.put("Token",Constant.TOKEN);
                headers.put("Authorization",Constant.AUTH);
                headers.put("Content-Type", "application/json");
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
