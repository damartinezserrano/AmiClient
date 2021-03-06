package com.example.marti.amiclient.ui;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.marti.amiclient.MainActivity;
import com.example.marti.amiclient.R;
import com.example.marti.amiclient.interfaces.drawer.DrawerLocker;
import com.example.marti.amiclient.settings.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeUI extends Fragment {

    Button buttonOtros,buttonPropio;
    TextView textViewPreguta;


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

        textViewPreguta=view.findViewById(R.id.pregunta);

        buttonOtros = view.findViewById(R.id.otros);
        buttonOtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.formularioOtros=true;
                Fragment fg = ServiceOptionsUI.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();

            }
        });


        buttonPropio = view.findViewById(R.id.propio);
        buttonPropio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.formularioOtros=false;
                Fragment fg = ServiceOptionsUI.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();

            }
        });

        implementFonts();


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((MainActivity)getActivity()).getCalificacionesPendientes(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.LISTAR_CALIFICACIONES_PENDIENTES + Constant.SLASH + Constant.ID);
        ((MainActivity)getActivity()).getTripulacionPendientes(Constant.HTTP_DOMAIN + Constant.APP_PATH + Constant.ENDPOINT_USUARIO + Constant.VER_TRIPULACION + Constant.SLASH + Constant.ID);
        super.onViewCreated(view, savedInstanceState);
    }

    public void implementFonts(){
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/futura_book_font.ttf");
        textViewPreguta.setTypeface(typeface);
        buttonPropio.setTypeface(typeface);
        buttonOtros.setTypeface(typeface);
    }
}
