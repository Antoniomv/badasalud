package com.vazquez.meliton.antonio.badasalud.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.vazquez.meliton.antonio.badasalud.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ContactoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactoFragment newInstance(String param1, String param2) {
        ContactoFragment fragment = new ContactoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacto, container, false);

        final EditText emailNombre = view.findViewById(R.id.tu_nombre);
        final EditText emailEmail= view.findViewById(R.id.tu_email);
        final EditText emailAsunto= view.findViewById(R.id.tu_asunto);
        final EditText emailMensaje= view.findViewById(R.id.tu_mensaje);

        Button emailBoton = (Button) view.findViewById(R.id.enviar_mensaje);
        emailBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = emailNombre.getText().toString();
                String email = emailEmail.getText().toString();
                String asunto = emailAsunto.getText().toString();
                String mensaje = emailMensaje.getText().toString();

                if (TextUtils.isEmpty(nombre)){
                    emailNombre.setError("Escribe tu nombre");
                    emailNombre.requestFocus();
                    return;
                }

                Boolean onError = false;
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailEmail.setError("Escribe tu email");
                    emailEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(asunto)){
                    emailAsunto.setError("Escribe un asunto");
                    emailAsunto.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(mensaje)){
                    emailMensaje.setError("Escribe un mensaje");
                    emailMensaje.requestFocus();
                    return;
                }

                Intent sendEmail = new Intent(Intent.ACTION_SEND);

                /* Fill it with Data */
                sendEmail.setType("plain/text");
                sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"amelitonvazquez@gmail.com"});
                sendEmail.putExtra(Intent.EXTRA_SUBJECT, asunto);
                sendEmail.putExtra(Intent.EXTRA_TEXT,
                        "Nombre:"+nombre+'\n'+"Email:"+email+'\n'+"Mensaje:"+'\n'+mensaje);

                /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(sendEmail, "Enviando Email..."));
            }
        });

        return view;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}