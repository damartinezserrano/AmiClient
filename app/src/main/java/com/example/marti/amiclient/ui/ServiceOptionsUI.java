package com.example.marti.amiclient.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.marti.amiclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceOptionsUI extends Fragment {

    LinearLayout ingresarinfo;


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

        ingresarinfo = view.findViewById(R.id.ingresarinfo);
        ingresarinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fg = ServiceInfoUI.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).commit();

            }
        });

        return view;
    }

}
