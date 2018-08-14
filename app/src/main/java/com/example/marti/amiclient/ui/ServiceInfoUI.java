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
public class ServiceInfoUI extends Fragment {


    public ServiceInfoUI() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        ServiceInfoUI serviceInfoUI = new ServiceInfoUI();
        return serviceInfoUI;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_info_ui, container, false);

        return view;
    }

}
