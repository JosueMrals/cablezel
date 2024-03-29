package com.josue.modelo;

import javax.persistence.*;

@Entity
@Table(name = "detalle_pago")

public class DetallePago extends Identificador{

    //Relation with Pago
    @ManyToOne
    @JoinColumn(name = "pago_id", referencedColumnName = "id")
    private Pago pago;

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    //Relation with DetalleFactura
    @ManyToOne
    @JoinColumn(name = "detalle_factura_id", referencedColumnName = "id")
    private DetalleFactura detalleFactura;

    public DetalleFactura getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFactura = detalleFactura;
    }

    @Column(name = "descripcion")
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
