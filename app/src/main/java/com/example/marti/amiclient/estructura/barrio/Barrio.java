package com.example.marti.amiclient.estructura.barrio;

import com.example.marti.amiclient.estructura.ciudad.Ciudad;

/**
 * Created by Marti on 2/10/18.
 */

public class Barrio {

    String _id;

    String cod_barrio;

    String nombre_barrio;

    String ciudad_cod_ciudad;

    int __v;

    Ciudad[] ciudad;

    String id;

    public Barrio() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCod_barrio() {
        return cod_barrio;
    }

    public void setCod_barrio(String cod_barrio) {
        this.cod_barrio = cod_barrio;
    }

    public String getNombre_barrio() {
        return nombre_barrio;
    }

    public void setNombre_barrio(String nombre_barrio) {
        this.nombre_barrio = nombre_barrio;
    }

    public String getCiudad_cod_ciudad() {
        return ciudad_cod_ciudad;
    }

    public void setCiudad_cod_ciudad(String ciudad_cod_ciudad) {
        this.ciudad_cod_ciudad = ciudad_cod_ciudad;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public Ciudad[] getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad[] ciudad) {
        this.ciudad = ciudad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
