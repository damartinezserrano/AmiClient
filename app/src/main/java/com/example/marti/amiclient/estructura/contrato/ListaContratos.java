package com.example.marti.amiclient.estructura.contrato;

/**
 * Created by Marti on 12/09/18.
 */

public class ListaContratos {

    String estado;

    String mensaje;

    Contrato[] lista;

    public ListaContratos() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Contrato[] getLista() {
        return lista;
    }

    public void setLista(Contrato[] lista) {
        this.lista = lista;
    }
}
