package com.josue.modelo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "facturas_automaticas")
public class FacturaAutomatica extends Identificador {
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @ManyToOne
    @JoinColumn(name = "contrato_id", referencedColumnName = "id")
    private Contrato contrato;
    public Contrato getContrato() {
        return contrato;
    }
    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    @Column(name = "fecha_factura_automatica")
    private LocalDate fecha_factura_automatica;
    public LocalDate getFecha_factura_automatica() {
        return fecha_factura_automatica;
    }
public void setFecha_factura_automatica(LocalDate fecha_factura_automatica) {
        this.fecha_factura_automatica = fecha_factura_automatica;
    }

    @Column(name = "cantidad_facturas")
    private Integer cantidad_facturas;
    public Integer getCantidad_facturas() {
        return cantidad_facturas;
    }
    public void setCantidad_facturas(Integer cantidad_facturas) {
        this.cantidad_facturas = cantidad_facturas;
    }
}
