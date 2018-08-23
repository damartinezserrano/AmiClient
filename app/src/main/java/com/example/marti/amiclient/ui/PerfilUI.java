package com.example.marti.amiclient.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marti.amiclient.R;
import com.example.marti.amiclient.interfaces.drawer.DrawerLocker;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilUI extends Fragment {

    TextView nombre,tel,mail,ciudad,dir;
    ImageView editarNombre, editarTel,editarMail,editarCiudad,editarDir;
    String m_Text = "";
    EditText input;

    public PerfilUI() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        PerfilUI perfilUI = new PerfilUI();
        return perfilUI;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil_ui, container, false);

        nombre = view.findViewById(R.id.nombre1);
        tel = view.findViewById(R.id.tel);
        mail = view.findViewById(R.id.mail);
        ciudad = view.findViewById(R.id.ciudad);
        dir = view.findViewById(R.id.dir);

        editarNombre = view.findViewById(R.id.editarnombre);
        editarTel = view.findViewById(R.id.editartel);
        editarMail = view.findViewById(R.id.editarmail);
        editarCiudad = view.findViewById(R.id.editarciudad);
        editarDir = view.findViewById(R.id.editardir);




        editarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editDialog(getResources().getString(R.string.editarnombre),nombre,InputType.TYPE_CLASS_TEXT);

            }
        });

        editarTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog(getResources().getString(R.string.editartel),tel,InputType.TYPE_CLASS_NUMBER);

            }
        });

        editarMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editDialog(getResources().getString(R.string.editarmail),mail,InputType.TYPE_CLASS_TEXT);
            }
        });

        editarCiudad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog(getResources().getString(R.string.editarciud),ciudad,InputType.TYPE_CLASS_TEXT);

            }
        });

        editarDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog(getResources().getString(R.string.editardir),dir,InputType.TYPE_CLASS_TEXT);

            }
        });


        return view;
    }

    public void editDialog(String title, final TextView textView, int inputType){

        input = new EditText(getActivity());
        input.setInputType(inputType);

        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setView(input)
                .setPositiveButton(getResources().getString(R.string.guardar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        m_Text = input.getText().toString();
                        textView.setText(m_Text);

                        if(textView.getId()==R.id.nombre1){
                            ((DrawerLocker)getActivity()).editHeaderName(m_Text);
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancelar),null)
                .show();
    }

}
