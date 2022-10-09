package com.josue.modelo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "servicios")
public class Servicio extends Identificador implements Serializable {

    @Column(name = "nombre")
    private String nombre;

    @Column (name = "descripcion")
    private String descripcion;

    @Column (name = "precio")
    private Float precio;

    public Servicio(String nombre, String descripcion, String precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = Float.parseFloat(precio);
    }

    public Servicio() {

    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Float getPrecio() {
        return precio;
    }
    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return  getNombre();
    }
}
