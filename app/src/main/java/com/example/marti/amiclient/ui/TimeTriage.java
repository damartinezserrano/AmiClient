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
public class TimeTriage extends Fragment {


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

        return view;
    }

}
