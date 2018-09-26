package com.example.marti.amiclient.estructura.persona;

/**
 * Created by Marti on 25/09/18.
 */

public class ListaInscritos {

    boolean estado;
    String mensaje;
    Inscritos[] lista;

    public ListaInscritos() {
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

    public Inscritos[] getLista() {
        return lista;
    }

    public void setLista(Inscritos[] lista) {
        this.lista = lista;
    }
}
