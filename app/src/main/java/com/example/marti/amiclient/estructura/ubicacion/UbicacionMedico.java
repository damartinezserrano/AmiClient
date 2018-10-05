package com.example.marti.amiclient.estructura.ubicacion;

/**
 * Created by Marti on 4/10/18.
 */

public class UbicacionMedico {

    Medico message;

    String type;

    public UbicacionMedico() {
    }

    public Medico getMessage() {
        return message;
    }

    public void setMessage(Medico message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
