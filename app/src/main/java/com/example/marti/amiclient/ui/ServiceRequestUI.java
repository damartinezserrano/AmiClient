package com.example.marti.amiclient.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.marti.amiclient.R;
import com.example.marti.amiclient.settings.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceRequestUI extends Fragment {

    Button buttonContinue;
    Spinner spinnerM,spinnerC;
    TextInputEditText textInputEditTextTelefono;
    EditText editTextDireccion;
    TextView textViewSpinner;
    Boolean camposErroneos=false;


    public ServiceRequestUI() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        ServiceRequestUI serviceInfoUI = new ServiceRequestUI();
        return serviceInfoUI;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_request_ui, container, false);

        textInputEditTextTelefono = view.findViewById(R.id.telefono);
        editTextDireccion = view.findViewById(R.id.midireccion);

        String[] motivos = new String[]{
                getResources().getString(R.string.motivo),
                "Motivo 1",
                "Motivo 2",
                "Movito 3",
                "Motivo 4"
        };

        final List<String> motivosList = new ArrayList<>(Arrays.asList(motivos));

        spinnerM = view.findViewById(R.id.motivoSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.custom_spinner,
                motivosList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerM.setAdapter(adapter);



        String[] ciudades = new String[]{
                getResources().getString(R.string.selciudad),
                "Ciudad 1",
                "Ciudad 2",
                "Ciudad 3",
                "Ciudad 4"
        };

        final List<String> ciudadesList = new ArrayList<>(Arrays.asList(ciudades));

        spinnerC = view.findViewById(R.id.ciudadSpinner);
        ArrayAdapter<String> adapterC = new ArrayAdapter<String>(
                getActivity(),
                R.layout.custom_spinner,
                ciudadesList
        );
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerC.setAdapter(adapterC);


        buttonContinue = view.findViewById(R.id.continua);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camposErroneos=false;
                String motivoData = spinnerM.getSelectedItem().toString();
                String ciudadData = spinnerC.getSelectedItem().toString();
                String telefono = textInputEditTextTelefono.getText().toString();
                String direccion = editTextDireccion.getText().toString();
                String camposFaltantes = "Los siguientes campos necesitan ser completados :\n";

                if(motivoData.equals(getResources().getString(R.string.motivo))){camposFaltantes=camposFaltantes+"Motivo\n"; camposErroneos=true;}
                if(ciudadData.equals(getResources().getString(R.string.selciudad))){camposFaltantes=camposFaltantes+"Ciudad\n";camposErroneos=true;}
                if(telefono.equals("")){camposFaltantes=camposFaltantes+"Telefono\n";camposErroneos=true;}
                if(direccion.equals("")){camposFaltantes=camposFaltantes+"Dirección\n";camposErroneos=true;}

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

                        Fragment fg = TriageUI.newInstance();
                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();

                }


            }
        });

        return view;
    }

}
