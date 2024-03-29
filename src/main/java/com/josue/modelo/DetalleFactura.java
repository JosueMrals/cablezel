package com.josue.modelo;

import javax.persistence.*;

@Entity
@Table (name = "detalles_facturas")

public class DetalleFactura extends Identificador{

    //Relation with Factura
    @ManyToOne
    @JoinColumn (name = "factura_id", referencedColumnName = "id")
    private Factura factura;

    public Factura getFactura() {return factura;}

    public void setFactura(Factura factura) {this.factura = factura;}

    //Relation with Servicio table
    @ManyToOne
    @JoinColumn(name = "ServicioId", referencedColumnName = "id")
    private Servicio servicio;

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    @Column (name = "total_pagar")
    private Float total_pagar;

    @Column (name = "descripcion")
    private String descripcion;

    public Float getTotal_pagar() {
        return total_pagar;
    }

    public void setTotal_pagar(Float total_pagar) {
        this.total_pagar = total_pagar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String toString(){
        return "DetalleFactura{" +
                "servicio=" + servicio +
                ", factura=" + factura +
                ", total a pagar=" + total_pagar +
                ", descripcion=" + descripcion +
                '}';
    }
}
