package com.josue.modelo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table (name = "pagos")

public class Pago extends Identificador{

    //Relation with Usuario
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    @Column (name = "fecha_pago")
    private LocalDate fecha_pago;

    @Column(name = "total_pagar")
    private Float total_pagar;

    public Pago() { }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDate getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(LocalDate fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public Float getTotal_pagar() {
        return total_pagar;
    }

    public void setTotal_pagar(Float total_pagar) {
        this.total_pagar = total_pagar;
    }

}
