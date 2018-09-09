package com.example.marti.amiclient.ui;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;

import com.example.marti.amiclient.R;
import com.example.marti.amiclient.settings.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsertCodeUI extends Fragment {

    TextView newcodeTView,conditionsTView;
    AppCompatCheckBox checkBox;
    Button listoBtn;
    TextInputLayout inputLayoutCod;
    TextInputEditText codTView;
    String campoCod,randomCode;
    SharedPreferences sharedPreferences;


    public InsertCodeUI() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        InsertCodeUI insertCodeUI = new InsertCodeUI();
        return insertCodeUI;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insert_code_ui, container, false);


        sharedPreferences = getActivity().getSharedPreferences(Constant.PREFERENCE_RANDOM, Context.MODE_PRIVATE);
        newcodeTView = view.findViewById(R.id.newcode);
        newcodeTView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fg = SendCodeUI.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();


            }
        });


        checkBox = view.findViewById(R.id.checkbox);
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

        inputLayoutCod = view.findViewById(R.id.codigoWrapper);
        codTView = view.findViewById(R.id.codigo);


        listoBtn=view.findViewById(R.id.listo);
        listoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campoCod=codTView.getText().toString();
                if(!campoCod.equals("")){
                    Log.i("campos","Escritos");
                     randomCode=sharedPreferences.getString(Constant.RANDOM_CODE,"0");
                     if (!campoCod.equals(randomCode)){
                         inputLayoutCod.requestFocus();
                         Drawable drError = getResources().getDrawable(R.drawable.error);
                         drError.setBounds(new Rect(0, 0, codTView.getHeight()/2, codTView.getHeight()/2));
                         codTView.setError(getResources().getString(R.string.codigoincorrecto),drError);
                     }else{
                         Fragment fg = WelcomeUI.newInstance();
                         getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();

                     }

                     //Entrada provisional para practicas
                    if (campoCod.equals("123")){
                        Fragment fg = WelcomeUI.newInstance();
                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                        codTView.setError(null);

                    }

                    if (campoCod.equals("mora")){
                        Fragment fg = MoraUI.newInstance();
                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                        codTView.setError(null);

                    }

                }else{
                    Log.i("campos","vacios");

                    if(campoCod.equals("")) {
                        inputLayoutCod.requestFocus();
                        Drawable drError = getResources().getDrawable(R.drawable.error);
                        drError.setBounds(new Rect(0, 0, codTView.getHeight()/2, codTView.getHeight()/2));
                        codTView.setError(getResources().getString(R.string.campovacio),drError);

                    }

                }
            }
        });


        return view;
    }

}
