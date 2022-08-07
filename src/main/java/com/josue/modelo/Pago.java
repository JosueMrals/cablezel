package com.josue.modelo;

import javax.persistence.*;

@Entity
@Table (name = "pagos")

public class Pago extends Identificador{

    @Column (name = "fecha_pago")
    private String fecha_pago;

    @Column(name = "total_pagar")
    private Float total_pagar;

    public String getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(String fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public Float getTotal_pagar() {
        return total_pagar;
    }

    public void setTotal_pagar(Float total_pagar) {
        this.total_pagar = total_pagar;
    }


}
