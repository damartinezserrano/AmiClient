package com.example.marti.amiclient.estructura.eps;

/**
 * Created by Marti on 2/10/18.
 */

public class ListaEPS {

    boolean estado;

    String mensaje;

    EPS[] lista;

    public ListaEPS() {
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

    public EPS[] getLista() {
        return lista;
    }

    public void setLista(EPS[] lista) {
        this.lista = lista;
    }
}
