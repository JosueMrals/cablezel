package com.josue.modelo;

import javax.persistence.*;

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

    @Column (name = "precio_contrato")
    private String precio_contrato;

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

    public String getPrecio_contrato() {
        return precio_contrato;
    }

    public void setPrecio_contrato(String precio_contrato) {
        this.precio_contrato = precio_contrato;
    }

    public TipoContrato(String cod_tipoContrato, String tipo_contrato, String descripcion, String cantidad_tv, String precio_contrato) {
        this.cod_tipocontrato = cod_tipoContrato;
        this.tipo_contrato = tipo_contrato;
        this.descripcion = descripcion;
        this.cantidad_tv = cantidad_tv;
        this.precio_contrato = precio_contrato;
    }

    @Override
    public String toString() {
        return getId() + " " + tipo_contrato;
    }
}
