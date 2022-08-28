package com.josue.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table (name = "contrato")
public class Contrato extends Identificador implements Serializable {

    /** Relation to Cliente table */
    @ManyToOne
    @JoinColumn(name = "tipocontrato_id", referencedColumnName = "cod_tipocontrato")
    private TipoContrato tipocontrato;

    public TipoContrato getTipocontrato() {
        return tipocontrato;
    }

    public void setTipocontrato(TipoContrato tipocontrato) {
        this.tipocontrato = tipocontrato;
    }

    /** Relation to Cliente table */
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    /** Contrato table */
    @Column (name = "fecha_contrato")
    private LocalDate fecha_contrato;

    @Column (name = "descripcion")
    private String descripcion;

    public LocalDate getFecha_contrato() {
        return fecha_contrato;
    }

    public void setFecha_contrato(LocalDate fecha_contrato) {
        this.fecha_contrato = fecha_contrato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + getId() +
                ", tipocontrato=" + tipocontrato +
                ", cliente=" + cliente +
                ", fecha_contrato=" + fecha_contrato +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
