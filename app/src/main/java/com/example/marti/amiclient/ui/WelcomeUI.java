package com.example.marti.amiclient.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.marti.amiclient.R;
import com.example.marti.amiclient.interfaces.drawer.DrawerLocker;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeUI extends Fragment {

    Button buttonOtros,buttonPropio;


    public WelcomeUI() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        WelcomeUI welcomeUI = new WelcomeUI();
        return welcomeUI;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome_ui, container, false);

        buttonOtros = view.findViewById(R.id.otros);
        buttonOtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fg = ServiceOptionsUI.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();

            }
        });


        buttonPropio = view.findViewById(R.id.propio);
        buttonPropio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fg = ServiceOptionsUI.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();

            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        super.onViewCreated(view, savedInstanceState);
    }
}
