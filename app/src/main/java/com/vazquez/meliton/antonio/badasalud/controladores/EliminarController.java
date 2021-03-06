package com.vazquez.meliton.antonio.badasalud.controladores;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.vazquez.meliton.antonio.badasalud.constantes.Constantes;

import java.util.Map;

public class EliminarController extends StringRequest {
    private static final String url = Constantes.DELETE_CITA;
    private Map<String, String> params;

    public EliminarController(String id, Response.Listener<String> listener) {
        super(Method.GET, url + id, listener, null);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
