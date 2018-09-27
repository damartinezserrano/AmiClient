package com.example.marti.amiclient.estructura.calificaciones;

/**
 * Created by Marti on 26/09/18.
 */

public class ListaCalificacionesPendientes {

    boolean estado;
    String mensaje;
    CalificacionPendiente[] lista;

    public ListaCalificacionesPendientes() {
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

    public CalificacionPendiente[] getLista() {
        return lista;
    }

    public void setLista(CalificacionPendiente[] lista) {
        this.lista = lista;
    }
}
