package com.example.marti.amiclient.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marti.amiclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeUI extends Fragment {


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

        return view;
    }

}
