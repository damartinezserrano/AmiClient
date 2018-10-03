package com.example.marti.amiclient.estructura.ciudad;

/**
 * Created by Marti on 2/10/18.
 */

public class CiudadPorCodigo {

    Ciudad[] message;

    String type;

    public CiudadPorCodigo() {
    }

    public Ciudad[] getMessage() {
        return message;
    }

    public void setMessage(Ciudad[] message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
