package com.josue.modelo;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


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

    //Relation with Factura
    @ManyToOne
    @JoinColumn(name = "factura_id", referencedColumnName = "id")
    private Factura factura;

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
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


}
