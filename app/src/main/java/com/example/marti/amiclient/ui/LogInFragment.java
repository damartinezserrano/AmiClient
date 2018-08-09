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
public class LogInFragment extends Fragment {


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
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

}
