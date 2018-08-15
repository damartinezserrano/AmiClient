package com.example.marti.amiclient.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.marti.amiclient.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceInfoUI extends Fragment {


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

        String[] motivos = new String[]{
                getResources().getString(R.string.motivo),
                "Motivo 1",
                "Motivo 2",
                "Movito 3",
                "Motivo 4"
        };

        final List<String> motivosList = new ArrayList<>(Arrays.asList(motivos));

        Spinner spinner = view.findViewById(R.id.motivoSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.custom_spinner,
                motivosList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        String[] ciudades = new String[]{
                getResources().getString(R.string.selciudad),
                "Ciudad 1",
                "Ciudad 2",
                "Ciudad 3",
                "Ciudad 4"
        };

        final List<String> ciudadesList = new ArrayList<>(Arrays.asList(ciudades));

        Spinner spinnerC = view.findViewById(R.id.ciudadSpinner);
        ArrayAdapter<String> adapterC = new ArrayAdapter<String>(
                getActivity(),
                R.layout.custom_spinner,
                ciudadesList
        );
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerC.setAdapter(adapterC);


        return view;
    }

}
