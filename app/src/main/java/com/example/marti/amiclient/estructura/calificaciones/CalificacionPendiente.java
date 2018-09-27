package com.example.marti.amiclient.estructura.calificaciones;

/**
 * Created by Marti on 26/09/18.
 */

public class CalificacionPendiente {

    String consec_movserv;
    String primer_nombre;
    String segundo_nombre;
    String primer_apellido;
    String segundo_apellido;
    String diagnostico;
    String fecha_servicio;

    public CalificacionPendiente() {
    }

    public String getConsec_movserv() {
        return consec_movserv;
    }

    public void setConsec_movserv(String consec_movserv) {
        this.consec_movserv = consec_movserv;
    }

    public String getPrimer_nombre() {
        return primer_nombre;
    }

    public void setPrimer_nombre(String primer_nombre) {
        this.primer_nombre = primer_nombre;
    }

    public String getSegundo_nombre() {
        return segundo_nombre;
    }

    public void setSegundo_nombre(String segundo_nombre) {
        this.segundo_nombre = segundo_nombre;
    }

    public String getPrimer_apellido() {
        return primer_apellido;
    }

    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getFecha_servicio() {
        return fecha_servicio;
    }

    public void setFecha_servicio(String fecha_servicio) {
        this.fecha_servicio = fecha_servicio;
    }
}
