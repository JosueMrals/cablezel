package com.josue.modelo;

import javax.persistence.*;

@Entity
@Table (name = "servicios")
public class Servicio extends Identificador{

    @ManyToOne
    @JoinColumn (name = "contrato_id",referencedColumnName = "id")
    private Contrato contrato;

    //Relation with DetalleFactura
    @ManyToOne
    @JoinColumn (name = "detalles_id", referencedColumnName = "id")
    private DetalleFactura detalleFactura;

    @Column(name = "nombre")
    private String nombre;

    @Column (name = "descripcion")
    private String descripcion;

    @Column (name = "precio")
    private Float precio;

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


}
