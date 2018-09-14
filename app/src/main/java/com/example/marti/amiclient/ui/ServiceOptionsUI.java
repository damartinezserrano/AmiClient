package com.example.marti.amiclient.ui;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.marti.amiclient.MainActivity;
import com.example.marti.amiclient.R;
import com.example.marti.amiclient.interfaces.drawer.DrawerLocker;
import com.example.marti.amiclient.settings.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceOptionsUI extends Fragment {

    LinearLayout ingresarinfo, solicitarLlamada, llamarDir;
    TextView textViewInfo,textViewSol,textViewDir;
    public static boolean solLlamada=false, llamarDirecto=false;


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

        ((DrawerLocker)getActivity()).showToolbar();
        ((DrawerLocker)getActivity()).toolbarBackground(R.color.white);
        ((DrawerLocker)getActivity()).hamburgerColor(R.color.colorPrimary);




        ingresarinfo = view.findViewById(R.id.ingresarinfo);
        ingresarinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Constant.formularioOtros) {
                    Fragment fg = ServiceInfoUI.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                }else{
                    Fragment fg = ServiceRequestUI.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                }

            }
        });

        solicitarLlamada = view.findViewById(R.id.solicitarllamada);
        solicitarLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solLlamada=true;
                llamarDirecto=false;
                Fragment fg = ContactServiceUI.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();

            }
        });

        llamarDir = view.findViewById(R.id.llamardirecto);
        llamarDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamarDirecto=true;
                solLlamada=false;
                Fragment fg = ContactServiceUI.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();

            }
        });

        textViewInfo  = view.findViewById(R.id.infoservicio);
        Bitmap drawable =BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.icono_hoja_verde);
        int size = (textViewInfo.getLineHeight()*2);
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(drawable, size, size, true));
        textViewInfo.setCompoundDrawablesRelativeWithIntrinsicBounds(d, null, null, null);

        textViewSol  = view.findViewById(R.id.llamadasol);
          drawable =BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.icono_auriculares_verde);
          size = (textViewSol.getLineHeight()*2);
          d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(drawable, size, size, true));
        textViewSol.setCompoundDrawablesRelativeWithIntrinsicBounds(d, null, null, null);

        textViewDir  = view.findViewById(R.id.llamadadir);
         drawable =BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.icono_telefono_verde);
          size = (textViewDir.getLineHeight()*2);
          d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(drawable, size, size, true));
        textViewDir.setCompoundDrawablesRelativeWithIntrinsicBounds(d, null, null, null);


        return view;
    }



}
