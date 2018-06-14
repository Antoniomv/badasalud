package com.vazquez.meliton.antonio.badasalud.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vazquez.meliton.antonio.badasalud.LoginActivity;
import com.vazquez.meliton.antonio.badasalud.PrincipalActivity;
import com.vazquez.meliton.antonio.badasalud.R;
import com.vazquez.meliton.antonio.badasalud.constantes.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InicioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private SharedPreferences sharedPreferences;
    private String usuarioId;

    private TextView vernombre, verapellidos, vertelefono;

    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
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
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        sharedPreferences = getContext().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        usuarioId = sharedPreferences.getString("idKey",null);
        //muestro por consola la id para saber si la trae correctamente
        System.out.println(usuarioId);


        vernombre = view.findViewById(R.id.ver_nombre);
        verapellidos = view.findViewById(R.id.ver_apellidos);
        vertelefono = view.findViewById(R.id.ver_telefono);

        cargarDatosUsuario();

        return view;
    }

    public void enviarDatosUduario(){
        //recojo valores
        String envioNombre = vernombre.getText().toString();
        String envioApellidos = verapellidos.getText().toString();
        String envioTelefono = vertelefono.getText().toString();

        //creo un bundle para poder moverlo a otro fragmento
        Bundle bundle = new Bundle();
        bundle.putString("nombre", envioNombre);
        bundle.putString("apellidos", envioApellidos);
        bundle.putString("telefono", envioTelefono);
        Intent intent = new Intent(getActivity(), PrincipalActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void cargarDatosUsuario(){
        final String url = Constantes.PRINCIPAL+usuarioId;
        //muestro por consola la url para saber si la trae correctamente
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        String nombreSend   =   jsonObject1.getString("nombre");
                        String apellidosSend =   jsonObject1.getString("apellidos");
                        String telefonoSend  =   jsonObject1.getString("telefono");

                        //muestro por consola  los datos para saber si la trae correctamente
                        System.out.println("nombre: "+nombreSend + "\n apellidos: " + apellidosSend + "\n telefono: "+ telefonoSend);


                        vernombre.setText(nombreSend);
                        verapellidos.setText(apellidosSend);
                        vertelefono.setText(telefonoSend);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },null);

        Volley.newRequestQueue(getContext()).add(stringRequest);
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
