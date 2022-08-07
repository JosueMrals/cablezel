package com.josue.modelo;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class DetallePago extends Identificador{

    //Relation with Pago
    @ManyToOne
    @JoinColumn(name = "pago_id", referencedColumnName = "id")
    private Pago pago;
}
