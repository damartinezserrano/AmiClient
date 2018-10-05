package com.example.marti.amiclient.ui;


import android.content.DialogInterface;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marti.amiclient.MainActivity;
import com.example.marti.amiclient.R;
import com.example.marti.amiclient.estructura.barrio.Barrio;
import com.example.marti.amiclient.estructura.barrio.BarrioPorCodigo;
import com.example.marti.amiclient.estructura.calificaciones.CalificarServicio;
import com.example.marti.amiclient.estructura.ciudad.Ciudad;
import com.example.marti.amiclient.estructura.ciudad.CiudadPorCodigo;
import com.example.marti.amiclient.estructura.contrato.ListaContratos;
import com.example.marti.amiclient.estructura.eps.ListaEPS;
import com.example.marti.amiclient.estructura.persona.DatosPersonales;
import com.example.marti.amiclient.interfaces.drawer.DrawerLocker;
import com.example.marti.amiclient.settings.Constant;
import com.google.android.gms.common.util.IOUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilUI extends Fragment {

    TextView nombre,nombre2,apellido,apellido2,mail,tel,docum,ciudad,barrio,ref,eps,dir, benef;
    ImageView editarNombre, editarSnombre, editarPap, editarSap, editarMail, editarTel,editarDocum,editarCiudad,editarDir,editarBenef,editarBarrio,editarEps;
    String m_Text = "";
    EditText input;
    AutoCompleteTextView autoCompleteTextView;

    String[] nombre_barrio;
    String[] codigo_barrio;
    String codBarrioElegido;
    Map<String, String> barrioandcod = new HashMap<>();


    String[] nombre_eps;
    String[] codigo_eps;
    String codEpsElegido;
    Map<String, String> epsandcod = new HashMap<>();

    String[] nombre_ciudad;
    String[] codigo_ciudad;


    RequestQueue requestQueue;


    public PerfilUI() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        PerfilUI perfilUI = new PerfilUI();
        return perfilUI;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil_ui, container, false);

        nombre = view.findViewById(R.id.nombre1);
        nombre2 = view.findViewById(R.id.snom);
        apellido = view.findViewById(R.id.pap);
        apellido2 = view.findViewById(R.id.sap);
        mail = view.findViewById(R.id.mail);
        tel = view.findViewById(R.id.tel);
        docum = view.findViewById(R.id.docum);
        ciudad = view.findViewById(R.id.ciudad);
        barrio = view.findViewById(R.id.barrio);
        ref = view.findViewById(R.id.ref);
        eps = view.findViewById(R.id.eps);
        dir = view.findViewById(R.id.dir);
        //benef = view.findViewById(R.id.benef);

        editarNombre = view.findViewById(R.id.editarnombre);
        editarTel = view.findViewById(R.id.editartel);
        editarDocum = view.findViewById(R.id.editardocum);
        editarCiudad = view.findViewById(R.id.editarciud);
        editarDir = view.findViewById(R.id.editardir);
        editarBarrio = view.findViewById(R.id.editarbarrio);
        editarEps = view.findViewById(R.id.editareps);
        editarSnombre = view.findViewById(R.id.editsnom);
        editarPap = view.findViewById(R.id.editpap);
        editarSap = view.findViewById(R.id.editsap);
        editarMail = view.findViewById(R.id.editmail);
        //editarBenef = view.findViewById(R.id.editarbenef);



        editarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editDialog(getResources().getString(R.string.editarnombre),nombre,InputType.TYPE_CLASS_TEXT);

            }
        });

        editarTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog(getResources().getString(R.string.editartel),tel,InputType.TYPE_CLASS_NUMBER);

            }
        });

        editarDocum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editDialog(getResources().getString(R.string.editardocum),docum,InputType.TYPE_CLASS_NUMBER);
            }
        });

        editarCiudad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog(getResources().getString(R.string.editarciud),ciudad,InputType.TYPE_CLASS_TEXT);

            }
        });

        editarDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog(getResources().getString(R.string.editardir),dir,InputType.TYPE_CLASS_TEXT);

            }
        });

        editarBarrio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAutocompleteDialog("Ingresar barrio",barrio,InputType.TYPE_CLASS_TEXT);

            }
        });

        editarEps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAutocompleteDialog("Ingresar EPS",eps,InputType.TYPE_CLASS_TEXT);

            }
        });

        editarSnombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog("Ingresar segundo nombre",nombre2,InputType.TYPE_CLASS_TEXT);

            }
        });

        editarPap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog("Ingresar primer apellido",apellido,InputType.TYPE_CLASS_TEXT);

            }
        });

        editarSap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog("Ingresar segundo apellido",apellido2,InputType.TYPE_CLASS_TEXT);

            }
        });

        editarMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog("Ingresar e-mail",mail,InputType.TYPE_CLASS_TEXT);

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       getDatosPerfil(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.CONSULTAR_DATOS_PERSONALES + Constant.SLASH + Constant.ID + Constant.SLASH + Constant.NRO_CONTRATO_SELECCIONADO);
        ((MainActivity)getActivity()).getCalificacionesPendientes(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.LISTAR_CALIFICACIONES_PENDIENTES + Constant.SLASH + Constant.ID);
        ((MainActivity)getActivity()).getTripulacionPendientes(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.VER_TRIPULACION + Constant.SLASH + Constant.ID);

       // new ConnTest().execute(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.CONSULTAR_DATOS_PERSONALES + Constant.SLASH + Constant.ID + Constant.SLASH + Constant.NRO_CONTRATO_SELECCIONADO + Constant.SLASH);
    }

    public void editDialog(String title, final TextView textView, int inputType){

        input = new EditText(getActivity());
        input.setInputType(inputType);

        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setView(input)
                .setPositiveButton(getResources().getString(R.string.guardar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        m_Text = input.getText().toString();
                        textView.setText(m_Text);

                        if(textView.getId()==R.id.nombre1){
                            ((DrawerLocker)getActivity()).editHeaderName(m_Text);
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancelar),null)
                .show();
    }

    public void editAutocompleteDialog(String title, final TextView textView, int inputType){

        autoCompleteTextView = new AutoCompleteTextView(getActivity());
        autoCompleteTextView.setInputType(inputType);
    try {
        if (title.equals("Ingresar barrio")) {
            final List<String> motivosList = new ArrayList<>(Arrays.asList(nombre_barrio));

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    motivosList
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            autoCompleteTextView.setAdapter(adapter);
        } else {

            if (title.equals("Ingresar EPS")) {
                final List<String> motivosList = new ArrayList<>(Arrays.asList(nombre_eps));

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        motivosList
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                autoCompleteTextView.setAdapter(adapter);
            }
        }

    }catch (Exception e){e.printStackTrace();}

        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setView(autoCompleteTextView)
                .setPositiveButton(getResources().getString(R.string.guardar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            m_Text = autoCompleteTextView.getText().toString();
                            //textView.setText(m_Text);

                            if (title.equals("Ingresar barrio")) {
                                codBarrioElegido = barrioandcod.get(m_Text);
                                textView.setText(m_Text);
                            } else {

                                if (title.equals("Ingresar EPS")) {
                                    codEpsElegido = epsandcod.get(m_Text);
                                    textView.setText(m_Text);

                                }
                            }
                        }catch (Exception e){e.printStackTrace();}

                        if(textView.getId()==R.id.nombre1){
                            ((DrawerLocker)getActivity()).editHeaderName(m_Text);
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancelar),null)
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

    public void getDatosPerfil(String UrlQuest) {

        requestQueue = getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlQuest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("PerfilUI :", "success");
                        parseDatosPerfil(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { //errores de peticion
                Log.i("PerfilUI :", "error");
                parseLogInError(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError { //autorizamos basic
                Map<String, String> headers = new HashMap<>();
                headers.put("Token",Constant.TOKEN);
                headers.put("Authorization",Constant.AUTH);
                Log.i("PerfilUIToken ", headers.toString());
                return headers;
            }

        };
        requestQueue.add(stringRequest);
    }

    public void parseDatosPerfil(String response) {

        Gson gson3 = new Gson();
        DatosPersonales datosPersonales = new DatosPersonales();
        datosPersonales = gson3.fromJson(response,DatosPersonales.class);

        String setNombre = datosPersonales.getPrimer_nombre();
        nombre.setText(setNombre);

        String setSnombre = datosPersonales.getSegundo_nombre();
        nombre2.setText(setSnombre);

        String setPap = datosPersonales.getPrimer_apellido();
        apellido.setText(setPap);

        String setSap = datosPersonales.getSegundo_apellido();
        apellido2.setText(setSap);

        String setMail = datosPersonales.getEmail();
        mail.setText(setMail);

        String setTel = datosPersonales.getTelefono();
        tel.setText(setTel);

        docum.setText(Constant.ID);

        String setDir = datosPersonales.getDir1()+" "+datosPersonales.getDir2()+" # "+datosPersonales.getDir3()+" - "+datosPersonales.getDir4();
        dir.setText(setDir);

        String setRef = datosPersonales.getPunto_referencia();
        ref.setText(setRef);

        getCiudadPerfil(Constant.HTTP_DOMAIN_DVD+Constant.END_POINT_CIUDAD+Constant.END_POINT_CODIGO+Constant.SLASH+datosPersonales.getCod_ciudad());

        getBarrioPerfil(Constant.HTTP_DOMAIN_DVD+Constant.END_POINT_BARRIO+Constant.END_POINT_CODE+Constant.SLASH+datosPersonales.getBarrio_cod_barrio());

        getEpsPerfil(Constant.HTTP_DOMAIN+Constant.APP_PATH+Constant.ENDPOINT_USUARIO+Constant.LISTAR_EPS+Constant.SLASH+Constant.ID,datosPersonales.getEps_cod_eps());

        getListBarrioPorCiudCod(Constant.HTTP_DOMAIN_DVD+Constant.END_POINT_BARRIO+Constant.SLASH+datosPersonales.getCod_ciudad());
    }


    public void getCiudadPerfil(String UrlQuest) {

        requestQueue = getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlQuest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("PerfilUI :", "success");
                        parseCiudadPerfil(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { //errores de peticion
                Log.i("PerfilUI :", "error");
                parseLogInError(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError { //autorizamos basic
                Map<String, String> headers = new HashMap<>();
                headers.put("Token",Constant.TOKEN);
                headers.put("Authorization",Constant.AUTH);
                Log.i("PerfilUIToken ", headers.toString());
                return headers;
            }

        };
        requestQueue.add(stringRequest);
    }

    public void parseCiudadPerfil(String response) {

        Gson gson3 = new Gson();
        CiudadPorCodigo ciudadPorCodigo = new CiudadPorCodigo();
        ciudadPorCodigo = gson3.fromJson(response,CiudadPorCodigo.class);

        try {

            Ciudad ciudadActual = ciudadPorCodigo.getMessage()[0];
            String setCiudd = ciudadActual.getNombre();
            ciudad.setText(setCiudd);


        }catch (Exception e){e.printStackTrace();}
    }

    public void getBarrioPerfil(String UrlQuest) {

        requestQueue = getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlQuest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("PerfilUI :", "success");
                        parseBarrioPerfil(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { //errores de peticion
                Log.i("PerfilUI :", "error");
                parseLogInError(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError { //autorizamos basic
                Map<String, String> headers = new HashMap<>();
                headers.put("Token",Constant.TOKEN);
                headers.put("Authorization",Constant.AUTH);
                Log.i("PerfilUIToken ", headers.toString());
                return headers;
            }

        };
        requestQueue.add(stringRequest);
    }

    public void parseBarrioPerfil(String response) {

        Gson gson3 = new Gson();
        BarrioPorCodigo barrioPorCodigo = new BarrioPorCodigo();
        barrioPorCodigo = gson3.fromJson(response,BarrioPorCodigo.class);

        //barrio.setText(barrioPorCodigo.getMessage());

        try {

            Barrio barrioActual = barrioPorCodigo.getMessage()[0];
            String setBarrio = barrioActual.getNombre_barrio();
            barrio.setText(setBarrio);

            nombre_barrio = new String[barrioPorCodigo.getMessage().length];
            codigo_barrio = new String[barrioPorCodigo.getMessage().length];

            for (int i = 0 ; i<barrioPorCodigo.getMessage().length ; i++){
                nombre_barrio[i]=barrioPorCodigo.getMessage()[i].getNombre_barrio();
                codigo_barrio[i]=barrioPorCodigo.getMessage()[i].getCod_barrio();
                barrioandcod.put(nombre_barrio[i], codigo_barrio[i]);

            }


        }catch (Exception e){e.printStackTrace();}
    }

    public void getEpsPerfil(String UrlQuest, String epscode) {

        requestQueue = getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlQuest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("PerfilUI :", "success");
                        parseEpsPerfil(response,epscode);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { //errores de peticion
                Log.i("PerfilUI :", "error");
                parseLogInError(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError { //autorizamos basic
                Map<String, String> headers = new HashMap<>();
                headers.put("Token",Constant.TOKEN);
                headers.put("Authorization",Constant.AUTH);
                Log.i("PerfilUIToken ", headers.toString());
                return headers;
            }

        };
        requestQueue.add(stringRequest);
    }

    public void parseEpsPerfil(String response, String epscode) {

        Gson gson3 = new Gson();
        ListaEPS listaEPS = new ListaEPS();
        listaEPS = gson3.fromJson(response,ListaEPS.class);

        try {

            String myEPSName="";


            for (int i = 0 ; i<listaEPS.getLista().length ; i++){
                if(codigo_eps[i].equals(epscode)){
                    myEPSName=nombre_eps[i];
                    eps.setText(myEPSName);
                }
            }


        }catch (Exception e){e.printStackTrace();}
    }

    public void getListBarrioPorCiudCod(String UrlQuest) {

        requestQueue = getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlQuest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("PerfilUI :", "success");
                        parseBarrioPorCiudCod(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { //errores de peticion
                Log.i("PerfilUI :", "error");
                parseLogInError(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError { //autorizamos basic
                Map<String, String> headers = new HashMap<>();
                headers.put("Token",Constant.TOKEN);
                headers.put("Authorization",Constant.AUTH);
                Log.i("PerfilUIToken ", headers.toString());
                return headers;
            }

        };
        requestQueue.add(stringRequest);
    }

    public void parseBarrioPorCiudCod(String response) {

        Gson gson3 = new Gson();
        BarrioPorCodigo barrioPorCodigo = new BarrioPorCodigo();
        barrioPorCodigo = gson3.fromJson(response,BarrioPorCodigo.class);

        try {

            nombre_barrio = new String[barrioPorCodigo.getMessage().length];
            codigo_barrio = new String[barrioPorCodigo.getMessage().length];

            for (int i = 0 ; i<barrioPorCodigo.getMessage().length ; i++){
                nombre_barrio[i]=barrioPorCodigo.getMessage()[i].getNombre_barrio();
                codigo_barrio[i]=barrioPorCodigo.getMessage()[i].getCod_barrio();
                barrioandcod.put(nombre_barrio[i], codigo_barrio[i]);

            }


        }catch (Exception e){e.printStackTrace();}
    }

    public void putActualizarDatosPersonales(String URL, String codserv, int calif, String observ) {



        requestQueue = getRequestQueue();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL, putActualizarBodyJSON(codserv,calif,observ), //hacemos la peticion post
                response -> {

                    Log.i("LogInFragment", "Se ha realizado el put perfil con exito");
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

    public JSONObject putActualizarBodyJSON(String codserv, int calif, String observ) { //construimos el json
        //primero json device
        String loginBody="";
        JSONObject jsonObject=null;

       /* calificarServicio = new CalificarServicio();
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
        }*/

        return jsonObject;
    }


    public void parseLogInError(VolleyError error) {

        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            boolean estado = data.getBoolean("estado");
            String errorlog = data.getString("error");
            Log.i("PerfilUI", "Ha ocurrido un error en el Login : "+estado+" , "+errorlog);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

}
