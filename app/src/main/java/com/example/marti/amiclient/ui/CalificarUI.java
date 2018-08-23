package com.example.marti.amiclient.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.marti.amiclient.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalificarUI extends Fragment {

    ImageView cora1, cora2, cora3, cora4, cora5;
    List<Integer> coras = new ArrayList<Integer>();
    View view2;


    public CalificarUI() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        CalificarUI calificarUI = new CalificarUI();
        return calificarUI;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calificar_ui, container, false);
        view2 = view;

        coras.add(R.id.c1);
        coras.add(R.id.c2);
        coras.add(R.id.c3);
        coras.add(R.id.c4);
        coras.add(R.id.c5);

        cora1=view.findViewById(R.id.c1);
        cora2=view.findViewById(R.id.c2);
        cora3=view.findViewById(R.id.c3);
        cora4=view.findViewById(R.id.c4);
        cora5=view.findViewById(R.id.c5);


        cora1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             fillCoras(R.id.c1,view);

            }
        });

        cora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillCoras(R.id.c2,view2);

            }
        });

        cora3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillCoras(R.id.c3,view2);

            }
        });

        cora4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillCoras(R.id.c4,view2);

            }
        });

        cora5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillCoras(R.id.c5,view2);

            }
        });


        return view;
    }


    public void fillCoras(int rateCora, View view){
        Iterator<Integer> myListIterator = coras.iterator();
       // while (myListIterator.hasNext()) {
        for (Integer number : coras) {

            if(number!=rateCora) {
                view.findViewById(number).setBackground(getResources().getDrawable(R.drawable.corazon_lleno));
            }else{
                view.findViewById(rateCora).setBackground(getResources().getDrawable(R.drawable.corazon_lleno));
                break;
            }
        }
      //  }
    }

}
