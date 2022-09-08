package com.josue.modelo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tipo_contrato")

public class TipoContrato extends Identificador implements Serializable {

    //Relation with Servicio table
    @ManyToOne
    @JoinColumn(name = "ServicioId", referencedColumnName = "id")
    private Servicio servicio;

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    @Column (name = "cod_tipocontrato")
    private String cod_tipocontrato;

    @Column (name = "tipo_contrato")
    private String tipo_contrato;

    @Column (name = "descripcion")
    private String descripcion;

    @Column (name = "cantidad_tv")
    private String cantidad_tv;

    public TipoContrato() {

    }

    public String getCod_tipocontrato() {
        return cod_tipocontrato;
    }

    public void setCod_tipocontrato(String cod_tipocontrato) {
        this.cod_tipocontrato = cod_tipocontrato;
    }

    public String getTipo_contrato() {
        return tipo_contrato;
    }

    public void setTipo_contrato(String tipo_contrato) {
        this.tipo_contrato = tipo_contrato;
    }

    public String getCantidad_tv() {
        return cantidad_tv;
    }

    public void setCantidad_tv(String cantidad_tv) {
        this.cantidad_tv = cantidad_tv;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoContrato(String cod_tipoContrato, String tipo_contrato, String descripcion, String cantidad_tv) {
        this.cod_tipocontrato = cod_tipoContrato;
        this.tipo_contrato = tipo_contrato;
        this.descripcion = descripcion;
        this.servicio = servicio;
        this.cantidad_tv = cantidad_tv;
    }

    @Override
    public String toString() {
        return getId() + " " + tipo_contrato;
    }

}
