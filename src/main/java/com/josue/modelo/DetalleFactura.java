package com.josue.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name = "detalles_facturas")

public class DetalleFactura extends Identificador{

    @Column (name = "cantidad_meses")
    private Integer cantidad_meses;

    @Column (name = "total_pagar")
    private Float total_pagar;

    @Column (name = "descricion")
    private String descripcion;

    public Integer getCantidad_meses() {
        return cantidad_meses;
    }

    public void setCantidad_meses(Integer cantidad_meses) {
        this.cantidad_meses = cantidad_meses;
    }

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
