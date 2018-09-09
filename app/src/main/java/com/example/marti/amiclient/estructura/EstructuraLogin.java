package com.example.marti.amiclient.estructura;

import com.example.marti.amiclient.estructura.persona.Usuario;

/**
 * Created by Marti on 7/09/18.
 */

public class EstructuraLogin {

    boolean estado;

    String mensaje;

    Usuario usuario;

    public EstructuraLogin() {
    }

    public EstructuraLogin(boolean estado, String mensaje, Usuario usuario) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
