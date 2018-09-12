package com.example.marti.amiclient.estructura;

/**
 * Created by Marti on 11/09/18.
 */

public class EstructuraSolicitarServicio {

    String contrato_nro_contrato;

    String persona_cc;

    String motivo_consulta;

    String direccion_servicio;

    String ciudad_cod_ciudad;

    String telefono_servicio;

    public EstructuraSolicitarServicio() {
    }

    public String getContrato_nro_contrato() {
        return contrato_nro_contrato;
    }

    public void setContrato_nro_contrato(String contrato_nro_contrato) {
        this.contrato_nro_contrato = contrato_nro_contrato;
    }

    public String getPersona_cc() {
        return persona_cc;
    }

    public void setPersona_cc(String persona_cc) {
        this.persona_cc = persona_cc;
    }

    public String getMotivo_consulta() {
        return motivo_consulta;
    }

    public void setMotivo_consulta(String motivo_consulta) {
        this.motivo_consulta = motivo_consulta;
    }

    public String getDireccion_servicio() {
        return direccion_servicio;
    }

    public void setDireccion_servicio(String direccion_servicio) {
        this.direccion_servicio = direccion_servicio;
    }

    public String getCiudad_cod_ciudad() {
        return ciudad_cod_ciudad;
    }

    public void setCiudad_cod_ciudad(String ciudad_cod_ciudad) {
        this.ciudad_cod_ciudad = ciudad_cod_ciudad;
    }

    public String getTelefono_servicio() {
        return telefono_servicio;
    }

    public void setTelefono_servicio(String telefono_servicio) {
        this.telefono_servicio = telefono_servicio;
    }
}
