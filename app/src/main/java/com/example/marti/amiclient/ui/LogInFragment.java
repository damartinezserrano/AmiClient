package com.example.marti.amiclient.ui;


import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marti.amiclient.R;
import com.example.marti.amiclient.estructura.EstructuraLogin;
import com.example.marti.amiclient.estructura.persona.Identificacion;
import com.example.marti.amiclient.interfaces.drawer.DrawerLocker;
import com.example.marti.amiclient.settings.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {

    TextView conditionsTView;
    TextInputLayout inputLayoutIden,inputLayoutCel;
    TextInputEditText idenTView,celTView;
    Button loginBtn;
    AppCompatCheckBox checkBox;
    LinearLayout layoutBotonesAyuda;
    String campoIden,campoCel;

    Identificacion identificacion;

    GsonBuilder gsonBuilder;
    Gson gson;

    RequestQueue requestQueue;

    public LogInFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        LogInFragment logInFragment = new LogInFragment();
        return logInFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        checkBox = view.findViewById(R.id.checkbox);
        inputLayoutIden = view.findViewById(R.id.identificacionWrapper);
        inputLayoutCel = view.findViewById(R.id.celularWrapper);
        idenTView = view.findViewById(R.id.identificacion);
        celTView = view.findViewById(R.id.celular);
        layoutBotonesAyuda = view.findViewById(R.id.botonesayuda);

        campoIden=idenTView.getText().toString();
        campoCel=celTView.getText().toString();

        conditionsTView = view.findViewById(R.id.cond);
        conditionsTView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getContext())
                        .setTitle(getResources().getString(R.string.popupTitle))
                        .setPositiveButton(getResources().getString(R.string.popupPositivo), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                              checkBox.setChecked(true);
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.popupNegativo),null)
                        .setMessage(getResources().getString(R.string.popupMsg))
                        .show();


            }
        });


        loginBtn=view.findViewById(R.id.ingresar);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campoIden=idenTView.getText().toString();
                campoCel=celTView.getText().toString();
             if(checkBox.isChecked()) {
                 if (!campoIden.equals("") && !campoCel.equals("")) {
                     Log.i("campos", "Escritos");

                     postLogin(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.VALIDAR_USUARIO, campoIden);

                     //Fragment fg = SendCodeUI.newInstance();
                     //getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                 } else {
                     Log.i("campos", "vacios");
                     if (campoIden.equals("")) {
                         idenTView.requestFocus();
                         Drawable drError = getResources().getDrawable(R.drawable.error);
                         drError.setBounds(new Rect(0, 0, idenTView.getHeight() / 2, idenTView.getHeight() / 2));
                         idenTView.setError("Este campo no puede estar vacío", drError);
                     }

                     if (campoCel.equals("")) {
                         celTView.requestFocus();
                         Drawable drError = getResources().getDrawable(R.drawable.error);
                         drError.setBounds(new Rect(0, 0, celTView.getHeight() / 2, celTView.getHeight() / 2));
                         celTView.setError("Este campo no puede estar vacío", drError);
                     }


                     layoutBotonesAyuda.setVisibility(View.VISIBLE);
                 }
              }else{
                 popupTerminosCondiciones();
             }
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((DrawerLocker)getActivity()).setDrawerEnabled(false);
        ((DrawerLocker)getActivity()).hideToolbar();
        super.onViewCreated(view, savedInstanceState);
    }

    public JSONObject loginBodyJSON(String id) { //construimos el json
        //primero json device
        String loginBody="";
        JsonElement jsonElement;
        JSONObject jsonObject=null;

        identificacion = new Identificacion();
        identificacion.setPersona_cc(id);

        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        loginBody = gson.toJson(identificacion);
        Log.i("loginRbody",loginBody);

        try {
            jsonObject = new JSONObject(loginBody);
            Log.i("jsonObject",jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public void postLogin(String URL, String id) {



        requestQueue = getRequestQueue();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, loginBodyJSON(id), //hacemos la peticion post
                response -> {

                    Log.i("LogInFragment", "Se ha realizado el user post con exito");
                    parseLogInResponse2(response);

                }, error -> {

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

    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity());
        }

        return requestQueue;
    }

    public void parseLogInError(VolleyError error) {

        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            boolean estado = data.getBoolean("estado");
            String mensaje = data.getString("mensaje");
            idenTView.requestFocus();
            Drawable drError = getResources().getDrawable(R.drawable.error);
            drError.setBounds(new Rect(0, 0, idenTView.getHeight() / 2, idenTView.getHeight() / 2));
            idenTView.setError(mensaje, drError);
            Log.i("LogInFragment", "Ha ocurrido un error en el Login : "+estado+" , "+mensaje);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void parseLogInResponse(JSONObject response) {

        try {
            JSONObject dataUsuario = response.getJSONObject("usuario");
            String morosidad = dataUsuario.getString("estado");
            if(morosidad.equals("2")){
                Fragment fg = MoraUI.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();

            }else{
                if(morosidad.equals("3")){
                    Fragment fg = SendCodeUI.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parseLogInResponse2(JSONObject response) {

        Gson gson3 = new Gson();
        EstructuraLogin estructuraLogin = gson3.fromJson(response.toString(),EstructuraLogin.class);

        if(estructuraLogin.getUsuario().getEstado().equals("2")){
            Fragment fg = MoraUI.newInstance();
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();

        }else{
            if(estructuraLogin.getUsuario().getEstado().equals("3")){
                Fragment fg = SendCodeUI.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
            }
        }


    }

        public void popupTerminosCondiciones(){

       new AlertDialog.Builder(getContext())
               .setTitle(getResources().getString(R.string.popupTitle))
               .setPositiveButton(getResources().getString(R.string.popupPositivo), new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       checkBox.setChecked(true);
                       loginBtn.performClick();
                   }
               })
               .setNegativeButton(getResources().getString(R.string.popupNegativo),null)
               .setMessage(getResources().getString(R.string.popupMsg))
               .show();
    }

}
