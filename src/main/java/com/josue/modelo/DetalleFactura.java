package com.josue.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name = "detalles_facturas")

public class DetalleFactura extends Identificador{

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
}
