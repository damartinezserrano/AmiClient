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
import com.example.marti.amiclient.R;
import com.example.marti.amiclient.estructura.contrato.ListaContratos;
import com.example.marti.amiclient.estructura.persona.DatosPersonales;
import com.example.marti.amiclient.interfaces.drawer.DrawerLocker;
import com.example.marti.amiclient.settings.Constant;
import com.google.android.gms.common.util.IOUtils;
import com.google.gson.Gson;

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

    TextView nombre,tel,docum,ciudad,dir, benef;
    ImageView editarNombre, editarTel,editarDocum,editarCiudad,editarDir,editarBenef;
    String m_Text = "";
    EditText input;

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
        tel = view.findViewById(R.id.tel);
        docum = view.findViewById(R.id.docum);
        ciudad = view.findViewById(R.id.ciudad);
        dir = view.findViewById(R.id.dir);
        benef = view.findViewById(R.id.benef);

        editarNombre = view.findViewById(R.id.editarnombre);
        editarTel = view.findViewById(R.id.editartel);
        editarDocum = view.findViewById(R.id.editardocum);
        editarCiudad = view.findViewById(R.id.editarciudad);
        editarDir = view.findViewById(R.id.editardir);
        editarBenef = view.findViewById(R.id.editarbenef);



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

        editarBenef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog(getResources().getString(R.string.editarbenef),benef,InputType.TYPE_CLASS_TEXT);

            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       getDatosPerfil(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.CONSULTAR_DATOS_PERSONALES + Constant.SLASH + Constant.ID + Constant.SLASH + Constant.NRO_CONTRATO_SELECCIONADO);

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

        String setNombre = datosPersonales.getPrimer_nombre()+" "+datosPersonales.getPrimer_apellido();
        nombre.setText(setNombre);

        String setTel = datosPersonales.getTelefono();
        tel.setText(setTel);

        docum.setText(Constant.ID);

        String setDir = datosPersonales.getDir1()+" "+datosPersonales.getDir2()+" # "+datosPersonales.getDir3()+" - "+datosPersonales.getDir4();
        dir.setText(setDir);



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






    public class ConnTest extends AsyncTask<String,Void,String> {

        String numeroCalle="";
        String numeroCarrera="";

        public ConnTest(){

        }

        @Override
        protected String doInBackground(String... url) {
            String data = "";
            getDatosPerfil2(url[0]);
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);



        }

        public void getDatosPerfil2(String UrlQuest) {

            BufferedReader in = null;


            try {
               /* HttpClient httpclient = new DefaultHttpClient();

                HttpGet request = new HttpGet();
                URI website = new URI(UrlQuest);
                request.setURI(website);
                request.addHeader("Authorization",Constant.AUTH);
                request.addHeader("Token",Constant.TOKEN);
                Log.i("token ", Constant.TOKEN);
                org.apache.http.HttpResponse response = httpclient.execute(request);
                in = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()));

                String line = in.readLine();

                Log.i("PerfilUIToken ", line+" - ");*/

                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

                httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                        okhttp3.Request original = chain.request();

                        okhttp3.Request request = original.newBuilder()
                                .header("Authorization", Constant.AUTH)
                                .header("Token", Constant.TOKEN)
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                });

                OkHttpClient client = httpClient.build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(UrlQuest)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();

            }catch (Exception e){e.printStackTrace();}
        }

    }
}
