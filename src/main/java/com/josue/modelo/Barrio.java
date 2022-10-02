package com.josue.modelo;

import javax.persistence.*;

@Entity
@Table (name = "barrios")

public class Barrio extends Identificador{

    @Column (name = "nombre_barrio", length = 50)
    private String nombre_barrio;

    @Column (name = "descripcion", length = 80)
    private String descripcion;

    public String getNombre_barrio() {
        return nombre_barrio;
    }

    public void setNombre_barrio(String nombre_barrio) {
        this.nombre_barrio = nombre_barrio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Barrio(String nombre_barrio, String descripcion) {
        this.nombre_barrio = nombre_barrio;
        this.descripcion = descripcion;
    }

    public Barrio() {

    }

    @Override
    public String toString() {

        return getId() + " " + nombre_barrio;
    }

}
