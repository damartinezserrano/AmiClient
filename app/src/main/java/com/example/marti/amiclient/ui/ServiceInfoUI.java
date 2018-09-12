package com.example.marti.amiclient.ui;


import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.marti.amiclient.estructura.EstructuraLogin;
import com.example.marti.amiclient.estructura.EstructuraSolicitarServicio;
import com.example.marti.amiclient.estructura.ciudad.Ciudad;
import com.example.marti.amiclient.estructura.motivo.ListaMotivos;
import com.example.marti.amiclient.estructura.motivo.Motivo;
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
public class ServiceInfoUI extends Fragment {

    Button buttonContinue;
    Spinner spinnerM,spinnerC;
    TextInputEditText textInputEditTextTelefono, textInputEditTextSintomas;
    TextInputLayout telWrap;
    TextInputEditText editTextDireccion;
    TextView textViewSpinner;
    Boolean camposErroneos=false;

    RequestQueue requestQueue;


    String[] motivos;
    String[] cod_motivo;

    String[] ciudades;
    String[] cod_ciud;

    GsonBuilder gsonBuilder;
    Gson gson;

    public ServiceInfoUI() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        ServiceInfoUI serviceInfoUI = new ServiceInfoUI();
        return serviceInfoUI;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_info_ui, container, false);

        textInputEditTextTelefono = view.findViewById(R.id.telefono);
        telWrap = view.findViewById(R.id.telefonoWrapper);
        textInputEditTextSintomas = view.findViewById(R.id.sintomas);
        editTextDireccion = view.findViewById(R.id.midireccion);



        spinnerM = view.findViewById(R.id.motivoSpinner);
        getListaMotivos(Constant.HTTP_DOMAIN_DVD+Constant.END_POINT_MOTIV,spinnerM);




        spinnerC = view.findViewById(R.id.ciudadSpinner);
        getListaCiudades(Constant.HTTP_DOMAIN_DVD+Constant.END_POINT_CIUDAD,spinnerC);


        buttonContinue = view.findViewById(R.id.continua);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camposErroneos=false;
                String motivoData = spinnerM.getSelectedItem().toString();
                String codMotivoData = getCodigoMotivoSeleccionado(spinnerM.getSelectedItemPosition());
                String ciudadData = spinnerC.getSelectedItem().toString();
                String codCiudadData = getCodigoCiudadSeleccionado(spinnerC.getSelectedItemPosition());
                String telefono = textInputEditTextTelefono.getText().toString();
                String sintomas = textInputEditTextSintomas.getText().toString();
                String direccion = editTextDireccion.getText().toString();
                String camposFaltantes = "Los siguientes campos necesitan ser completados :\n";

                if(motivoData.equals(getResources().getString(R.string.motivo))){camposFaltantes=camposFaltantes+"Motivo\n"; camposErroneos=true;}
                if(ciudadData.equals(getResources().getString(R.string.selciudad))){camposFaltantes=camposFaltantes+"Ciudad\n";camposErroneos=true;}
                if(telefono.equals("")||telefono.length()<4){

                    if(telefono.equals("")) {
                        camposFaltantes = camposFaltantes + "Telefono\n";
                        camposErroneos = true;
                        Drawable drError = getResources().getDrawable(R.drawable.cancel);
                        drError.setBounds(new Rect(0, 0, textInputEditTextTelefono.getHeight() / 2, textInputEditTextTelefono.getHeight() / 2));
                        textInputEditTextTelefono.setError("Este campo no puede estar vacio", drError);
                    }else{
                        if(telefono.length()<4){
                            camposFaltantes=camposFaltantes+"Telefono\n";camposErroneos=true;
                            Drawable drError = getResources().getDrawable(R.drawable.cancel);
                            drError.setBounds(new Rect(0, 0, textInputEditTextTelefono.getHeight()/2, textInputEditTextTelefono.getHeight()/2));
                            textInputEditTextTelefono.setError("Este campo no puede tener menos de 4 caracteres",drError);
                        }
                    }
                }
                if(sintomas.equals("")){
                    camposFaltantes=camposFaltantes+"Sintomas\n";camposErroneos=true;
                    Drawable drError = getResources().getDrawable(R.drawable.cancel);
                    drError.setBounds(new Rect(0, 0, textInputEditTextSintomas.getHeight()/2, textInputEditTextSintomas.getHeight()/2));
                    textInputEditTextSintomas.setError("Este campo no puede estar vacio",drError);
                }
                if(direccion.equals("")){
                    camposFaltantes=camposFaltantes+"Dirección\n";camposErroneos=true;
                    Drawable drError = getResources().getDrawable(R.drawable.cancel);
                    drError.setBounds(new Rect(0, 0, editTextDireccion.getHeight()/2, editTextDireccion.getHeight()/2));
                    editTextDireccion.setError("Este campo no puede estar vacio",drError);
                }

               if(camposErroneos) {

                   new AlertDialog.Builder(getContext())
                           .setTitle("Algunos campos obligatorios no fueron completados.")
                           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {

                               }
                           })
                           .setMessage(camposFaltantes)
                           .show();

               }else{
                    try {
                        postSolicitarServicio(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.SOLICITAR_SERVICIO, "123988", Constant.ID, codMotivoData, direccion, codCiudadData, telefono);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        resetValores();
                    }


               }


            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        textInputEditTextTelefono.setText(Constant.telefono);
        textInputEditTextSintomas.setText(Constant.sintomas);
        editTextDireccion.setText(Constant.direccion);
        spinnerC.setSelection(Constant.ciudad_pos);
        spinnerM.setSelection(Constant.consulta_motivo_pos);

        retenerValoresTextos(textInputEditTextTelefono);
        retenerValoresTextos(textInputEditTextSintomas);
        retenerValoresTextos(editTextDireccion);
        retenerValoresSpinner(spinnerC);
        retenerValoresSpinner(spinnerM);

    }

    public void retenerValoresTextos(final TextInputEditText textInputEditText){

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                switch (textInputEditText.getId()){

                    case R.id.beneficiarios :
                        Constant.beneficiarios=textInputEditText.getText().toString();
                        break;

                    case R.id.telefono :
                        Constant.telefono=textInputEditText.getText().toString();
                        break;

                    case R.id.sintomas :
                        Constant.sintomas=textInputEditText.getText().toString();
                        break;

                    case R.id.midireccion :
                        Constant.direccion=textInputEditText.getText().toString();
                        break;


                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void retenerValoresSpinner(final Spinner spinner) {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                switch (spinner.getId()){

                    case R.id.motivoSpinner :
                        Constant.consulta_motivo_pos=position;
                        break;

                    case R.id.ciudadSpinner :
                        Constant.ciudad_pos=position;
                        break;



                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public void resetValores(){
        Constant.beneficiarios="";

        Constant.telefono="";

        Constant.sintomas="";

        Constant.direccion="";

        Constant.consulta_motivo_pos=0;

        Constant.ciudad_pos=0;

        textInputEditTextTelefono.setText(Constant.telefono);
        textInputEditTextSintomas.setText(Constant.sintomas);
        editTextDireccion.setText(Constant.direccion);
        spinnerC.setSelection(Constant.ciudad_pos);
        spinnerM.setSelection(Constant.consulta_motivo_pos);
    }


    public void getListaMotivos(String UrlQuest, Spinner spinnerMotiv) {

        requestQueue = getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlQuest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        parseMotivosResponse(response,spinnerMotiv);
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

    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity());
        }

        return requestQueue;
    }

    public void parseMotivosResponse(String response, Spinner spinnerMotiv) {

        Gson gson3 = new Gson();
        ListaMotivos listaMotivos = new ListaMotivos();
        Motivo[] motivo;
        motivo = gson3.fromJson(response,Motivo[].class);
        listaMotivos.setLista(motivo);

        motivos = new String[listaMotivos.getLista().length+1];
        cod_motivo = new String[listaMotivos.getLista().length+1];
        motivos[0]=getResources().getString(R.string.motivo);
        cod_motivo[0]="codigo_motivo";


        for (int i = 1 ; i<=listaMotivos.getLista().length ; i++){
            motivos[i]=listaMotivos.getLista()[i-1].getNombre();
            cod_motivo[i]=listaMotivos.getLista()[i-1].getCodigo();
        }

        final List<String> motivosList = new ArrayList<>(Arrays.asList(motivos));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.custom_spinner,
                motivosList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMotiv.setAdapter(adapter);

        spinnerMotiv.setSelection(Constant.consulta_motivo_pos);
    }

    public void getListaCiudades(String UrlQuest, Spinner spinnerCiud) {

        requestQueue = getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlQuest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        parseCiudadResponse(response,spinnerCiud);
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

    public void parseCiudadResponse(String response, Spinner spinnerCiud) {

        Gson gson3 = new Gson();
        ListaMotivos listaMotivos = new ListaMotivos();
        Ciudad[] ciudad;
        ciudad = gson3.fromJson(response,Ciudad[].class);


        ciudades = new String[ciudad.length+1];
        ciudades[0]=getResources().getString(R.string.selciudad);
        cod_ciud = new String[ciudad.length+1];
        cod_ciud[0]=" codigo_ciudades";

        for (int i = 1 ; i<=ciudad.length ; i++){
            ciudades[i]=ciudad[i-1].getNombre();
            cod_ciud[i]=ciudad[i-1].getCodigo();
        }

        final List<String> ciudadesList = new ArrayList<>(Arrays.asList(ciudades));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.custom_spinner,
                ciudadesList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCiud.setAdapter(adapter);

        spinnerCiud.setSelection(Constant.ciudad_pos);
    }

    public void postSolicitarServicio(String URL, String noContrato, String personaCC, String motivoConsulta, String direccionServicio, String codCiudad, String telefonoServicio) {



        requestQueue = getRequestQueue();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, solicitarServicioBodyJSON(noContrato,personaCC,motivoConsulta,direccionServicio,codCiudad,telefonoServicio), //hacemos la peticion post
                response -> {

                    Log.i("ServiceInfoUI", "Se ha realizado el servicio post con exito");

                    Fragment fg = TriageUI.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();



                }, error -> {
                   Log.i("ServiceInfoUI", "Ha ocurrido un error en el post servicio");
                   parseSolicitarServicioError(error);
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

    public JSONObject solicitarServicioBodyJSON(String noContrato, String personaCC, String motivoConsulta, String direccionServicio, String codCiudad, String telefonoServicio) { //construimos el json
        //primero json device
        String solicitarServicioBody="";
        JSONObject jsonObject=null;

        EstructuraSolicitarServicio  estructuraSolicitarServicio= new EstructuraSolicitarServicio();
        estructuraSolicitarServicio.setContrato_nro_contrato(noContrato);
        estructuraSolicitarServicio.setPersona_cc(personaCC);
        estructuraSolicitarServicio.setMotivo_consulta(motivoConsulta);
        estructuraSolicitarServicio.setDireccion_servicio(direccionServicio);
        estructuraSolicitarServicio.setCiudad_cod_ciudad(codCiudad);
        estructuraSolicitarServicio.setTelefono_servicio(telefonoServicio);

        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        solicitarServicioBody = gson.toJson(estructuraSolicitarServicio);
        Log.i("loginRbody",solicitarServicioBody);

        try {
            jsonObject = new JSONObject(solicitarServicioBody);
            Log.i("jsonObject",jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public String getCodigoMotivoSeleccionado(int posicion){
        return cod_motivo[posicion];
    }

    public String getCodigoCiudadSeleccionado(int posicion){
        return cod_ciud[posicion];
    }

    public void parseSolicitarServicioError(VolleyError error) {

        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            boolean estado = data.getBoolean("estado");
            String mensaje = data.getString("mensaje");
            new AlertDialog.Builder(getContext())
                    .setTitle("INFORMACIÓN")
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
        }

    }
}
