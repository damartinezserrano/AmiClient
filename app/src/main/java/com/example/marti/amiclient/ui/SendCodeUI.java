package com.example.marti.amiclient.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.marti.amiclient.R;
import com.example.marti.amiclient.settings.Constant;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendCodeUI extends Fragment {

    Button button;
    TextInputEditText textInputEditText;
    String campoNumero;
    SharedPreferences sharedPreferences;

    public SendCodeUI() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        SendCodeUI sendCodeUI = new SendCodeUI();
        return sendCodeUI;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_code_ui, container, false);

        textInputEditText = view.findViewById(R.id.numero);
        sharedPreferences = getActivity().getSharedPreferences(Constant.PREFERENCE_RANDOM, Context.MODE_PRIVATE);


        button=view.findViewById(R.id.continua);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campoNumero=textInputEditText.getText().toString();
                if(!campoNumero.equals("")){
                    Log.i("campos","Escritos");
                    sendSMS(campoNumero);
                    Fragment fg = InsertCodeUI.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fg).addToBackStack(null).commit();
                }else{
                    Log.i("campos","vacios");
                    textInputEditText.requestFocus();
                    Drawable drError = getResources().getDrawable(R.drawable.error);
                    drError.setBounds(new Rect(0, 0, textInputEditText.getHeight()/2, textInputEditText.getHeight()/2));
                    textInputEditText.setError("Ha ocurrido un error en este campo",drError);
                }
            }
        });


        return view;
    }

    public void sendSMS(String phoneNo) {

        try {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo,null, generateCode(),null,null);
            Toast.makeText(getActivity(), "El Código ha sido enviado.",
                    Toast.LENGTH_LONG).show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String generateCode(){

        SecureRandom secureRandom = new SecureRandom();
        String randomCode = new BigInteger(30, secureRandom).toString(32).toUpperCase();
        sharedPreferences.edit().putString(Constant.RANDOM_CODE,randomCode).apply();
        return randomCode;
    }



}
