package com.josue.modelo;

import javax.persistence.*;
import java.text.DateFormat;
@Entity
@Table (name = "contrato")

public class Contrato extends Identificador{


    //Relation to TipoContrato table
    @ManyToOne
    @JoinColumn(name = "tipocontrato_id", referencedColumnName = "cod_tipocontrato")
    private TipoContrato tipocontrato;

    public TipoContrato getTipocontrato() {
        return tipocontrato;
    }

    public void setTipocontrato(TipoContrato tipocontrato) {
        this.tipocontrato = tipocontrato;
    }

    //Relation to Cliente table
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    //Contrato table
    @Column (name = "fecha_contrato")
    private DateFormat fecha_contrato;

    @Column (name = "descripcion")
    private String descripcion;

    public DateFormat getFecha_contrato() {
        return fecha_contrato;
    }

    public void setFecha_contrato(DateFormat fecha_contrato) {
        this.fecha_contrato = fecha_contrato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



}
