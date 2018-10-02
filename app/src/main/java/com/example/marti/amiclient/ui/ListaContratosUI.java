package com.example.marti.amiclient.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marti.amiclient.MainActivity;
import com.example.marti.amiclient.R;
import com.example.marti.amiclient.estructura.ciudad.Ciudad;
import com.example.marti.amiclient.estructura.contrato.ListaContratos;
import com.example.marti.amiclient.estructura.motivo.ListaMotivos;
import com.example.marti.amiclient.settings.Constant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaContratosUI extends Fragment {

    Button buttonContinue;
    Spinner spinnerContratos;
    NumberPicker numberPicker;

    RequestQueue requestQueue;


    String[] contrato;
    String[] num_contrato;


    public ListaContratosUI() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        ListaContratosUI listaContratosUI = new ListaContratosUI();
        return listaContratosUI;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_contratos_ui, container, false);

        //spinnerContratos = view.findViewById(R.id.contratoSpinner);
        numberPicker = view.findViewById(R.id.string_picker);
        //getListaContratos(Constant.HTTP_DOMAIN+Constant.APP_PATH+Constant.ENDPOINT_USUARIO+Constant.LISTAR_CONTRATO+Constant.SLASH+Constant.ID,spinnerContratos);
        getStaticListaContratos();

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue((num_contrato.length)-1);
        numberPicker.setDisplayedValues(num_contrato);


        buttonContinue = view.findViewById(R.id.continuar);
        buttonContinue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //int selectedContrato = spinnerContratos.getSelectedItemPosition();
                  int selectedContrato = numberPicker.getValue();

                if(selectedContrato>0){
                    if(contrato[selectedContrato].equals("3")){
                        Fragment fg = MoraUI.newInstance();
                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                    }else{
                        Constant.NRO_CONTRATO_SELECCIONADO = num_contrato[selectedContrato];
                        Fragment fg = WelcomeUI.newInstance();
                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();

                    }
                }


            }
        });


        return view;
    }

    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity());
        }

        return requestQueue;
    }


    public void getListaContratos(String UrlQuest, Spinner spinnerContrato) {

        requestQueue = getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlQuest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("ListaContratosUI :", "success");
                        parseContratoResponse(response,spinnerContrato);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { //errores de peticion
                Log.i("ListaContratosUI :", "error");
            }
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
        requestQueue.add(stringRequest);
    }


    public void parseContratoResponse(String response, Spinner spinnerContrato) {

        Gson gson3 = new Gson();
        ListaContratos listaContratos = new ListaContratos();
        listaContratos = gson3.fromJson(response,ListaContratos.class);


        if(listaContratos.getLista().length>1) {

            contrato = new String[listaContratos.getLista().length + 1];
            contrato[0] = "Seleccionar Contrato";
            num_contrato = new String[listaContratos.getLista().length + 1];
            num_contrato[0] = "Seleccionar Contrato";

            for (int i = 1; i <= listaContratos.getLista().length; i++) {// si esta activo agregarlo a droplist
                num_contrato[i] = listaContratos.getLista()[i - 1].getContrato_nro_contrato();
            }

            final List<String> ciudadesList = new ArrayList<>(Arrays.asList(num_contrato));

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.custom_spinner,
                    ciudadesList
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerContrato.setAdapter(adapter);

            spinnerContrato.setSelection(Constant.ciudad_pos);

        }else{
            if(listaContratos.getLista().length==1) {
                Fragment fg = WelcomeUI.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
            }else{
                if(listaContratos.getLista().length==0) {
                    Fragment fg = MoraUI.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                }
            }
        }
    }


    public void getStaticListaContratos() {

        ListaContratos listaContratos = new ListaContratos();


        if(Constant.slistaContratos.length>1) {

            ((MainActivity)getActivity()).getCalificacionesPendientes(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.LISTAR_CALIFICACIONES_PENDIENTES + Constant.SLASH + Constant.ID);


            contrato = new String[Constant.slistaContratos.length + 1];
            contrato[0] = "Estado Contrato";
            num_contrato = new String[Constant.slistaContratos.length + 1];
            num_contrato[0] = "Seleccionar Contrato";

            for (int i = 1; i <= Constant.slistaContratos.length; i++) {// si esta activo agregarlo a droplist
                num_contrato[i] = Constant.slistaContratos[i - 1].getContrato_nro_contrato();
                contrato[i] = Constant.slistaContratos[i - 1].getEstado();
            }

            final List<String> contratosList = new ArrayList<>(Arrays.asList(num_contrato));

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.custom_spinner,
                    contratosList
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //spinnerContrato.setAdapter(adapter);

            //spinnerContrato.setSelection(0);

        }else{
            if(Constant.slistaContratos.length==1) {
                contrato = new String[Constant.slistaContratos.length + 1];
                contrato[0] = "Estado Contrato";
                num_contrato = new String[Constant.slistaContratos.length + 1];
                num_contrato[0] = "Seleccionar Contrato";

                for (int i = 1; i <= Constant.slistaContratos.length; i++) {// si esta activo agregarlo a droplist
                    num_contrato[i] = Constant.slistaContratos[i - 1].getContrato_nro_contrato();
                    contrato[i] = Constant.slistaContratos[i - 1].getEstado();
                }
                if(contrato[1].equals("3")){
                    Fragment fg = MoraUI.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();

                }else {
                    Constant.NRO_CONTRATO_SELECCIONADO = num_contrato[1];
                    Fragment fg = WelcomeUI.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                }
            }else{
                if(listaContratos!=null) {

                    if (listaContratos.getLista().length == 0) {
                        Fragment fg = MoraUI.newInstance();
                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                    }

                }else{
                    Fragment fg = MoraUI.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                }
            }
        }

    }

}
