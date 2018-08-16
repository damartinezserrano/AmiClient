package com.example.marti.amiclient.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.marti.amiclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TriageUI extends Fragment {

    Button consultar;


    public TriageUI() {
        // Required empty public constructor
    }


    public static Fragment newInstance() {
        TriageUI triageUI = new TriageUI();
        return triageUI;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_triage_ui, container, false);

        consultar = view.findViewById(R.id.consultar);
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fg = TimeTriage.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();



            }
        });

        return view;
    }

}
