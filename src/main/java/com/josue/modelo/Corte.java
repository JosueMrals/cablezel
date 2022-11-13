package com.josue.modelo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cortes")
public class Corte extends Identificador{
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

    @Column(name = "fecha_corte")
    private LocalDate fechaCorte;

    public LocalDate getFecha_corte() {
        return fechaCorte;
    }

    public void setFecha_corte(LocalDate fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    @Column(name = "estado")
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
