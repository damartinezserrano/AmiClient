package com.example.marti.amiclient.ui;


import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.marti.amiclient.MainActivity;
import com.example.marti.amiclient.R;
import com.example.marti.amiclient.settings.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTriage extends Fragment {

    ImageView ambulancia;


    public TimeTriage() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        TimeTriage timeTriage = new TimeTriage();
        return timeTriage;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_triage, container, false);

        ambulancia = view.findViewById(R.id.ambulancia);
        ambulancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Fragment fg = CalificarUI.newInstance();
               // getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();


            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).getCalificacionesPendientes(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.LISTAR_CALIFICACIONES_PENDIENTES + Constant.SLASH + Constant.ID);
        ((MainActivity)getActivity()).getTripulacionPendientes(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.VER_TRIPULACION + Constant.SLASH + Constant.ID);

    }
}
