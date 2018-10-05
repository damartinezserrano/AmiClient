package com.example.marti.amiclient.estructura.tripulacion;

/**
 * Created by Marti on 4/10/18.
 */

public class VerTripulacion {

    boolean estado;

    String mensaje;

    Tripulacion tripulacion;

    public VerTripulacion() {
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Tripulacion getTripulacion() {
        return tripulacion;
    }

    public void setTripulacion(Tripulacion tripulacion) {
        this.tripulacion = tripulacion;
    }
}
