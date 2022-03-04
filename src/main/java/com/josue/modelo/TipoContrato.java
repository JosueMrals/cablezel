package com.josue.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_contrato")

public class TipoContrato extends Identificador{
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

    public String getCod_tipoContrato() {
        return cod_tipocontrato;
    }

    public void setCod_tipoContrato(String cod_tipoContrato) {
        this.cod_tipocontrato = cod_tipoContrato;
    }

    public String getTipo_contrato() {
        return tipo_contrato;
    }

    public void setTipo_contrato(String tipo_contrato) {
        this.tipo_contrato = tipo_contrato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad_tv() {
        return cantidad_tv;
    }

    public void setCantidad_tv(String cantidad_tv) {
        this.cantidad_tv = cantidad_tv;
    }


    public TipoContrato(String cod_tipoContrato, String tipo_contrato, String descripcion, String cantidad_tv) {
        this.cod_tipocontrato = cod_tipoContrato;
        this.tipo_contrato = tipo_contrato;
        this.descripcion = descripcion;
        this.cantidad_tv = cantidad_tv;
    }
}
