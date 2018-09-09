package com.example.marti.amiclient.estructura.persona;

/**
 * Created by Marti on 7/09/18.
 */

public class Identificacion {

    String persona_cc;

    public Identificacion() {
    }

    public Identificacion(String persona_cc) {
        this.persona_cc = persona_cc;
    }

    public String getPersona_cc() {
        return persona_cc;
    }

    public void setPersona_cc(String persona_cc) {
        this.persona_cc = persona_cc;
    }
}
