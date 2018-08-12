package com.example.marti.amiclient.ui;


import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.marti.amiclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {

    TextView conditionsTView;
    TextInputLayout inputLayoutIden,inputLayoutCel;
    TextInputEditText idenTView,celTView;
    Button loginBtn;
    AppCompatCheckBox checkBox;
    RelativeLayout layoutBotonesAyuda;
    String campoIden,campoCel;

    public LogInFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        LogInFragment logInFragment = new LogInFragment();
        return logInFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);


        checkBox = view.findViewById(R.id.checkbox);
        inputLayoutIden = view.findViewById(R.id.identificacionWrapper);
        inputLayoutCel = view.findViewById(R.id.celularWrapper);
        idenTView = view.findViewById(R.id.identificacion);
        celTView = view.findViewById(R.id.celular);
        layoutBotonesAyuda = view.findViewById(R.id.botonesayuda);

        campoIden=idenTView.getText().toString();
        campoCel=celTView.getText().toString();

        conditionsTView = view.findViewById(R.id.cond);
        conditionsTView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getContext())
                        .setTitle(getResources().getString(R.string.popupTitle))
                        .setPositiveButton(getResources().getString(R.string.popupPositivo), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                              checkBox.setChecked(true);
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.popupNegativo),null)
                        .setMessage(getResources().getString(R.string.popupMsg))
                        .show();


            }
        });


        loginBtn=view.findViewById(R.id.ingresar);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campoIden=idenTView.getText().toString();
                campoCel=celTView.getText().toString();
                if(!campoIden.equals("")&&!campoCel.equals("")){
                    Log.i("campos","Escritos");
                    Fragment fg = SendCodeUI.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).commit();
                }else{
                    Log.i("campos","vacios");

                    if(campoIden.equals("")) {
                        inputLayoutIden.requestFocus();
                        inputLayoutIden.setError("");
                        idenTView.requestFocus();
                        idenTView.setError("Ha ocurrido un error en este campo");
                    }

                    if(campoCel.equals("")) {
                        inputLayoutCel.requestFocus();
                        inputLayoutCel.setError("");
                        celTView.requestFocus();
                        celTView.setError("Ha ocurrido un error en este campo");
                    }


                    layoutBotonesAyuda.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }




}
