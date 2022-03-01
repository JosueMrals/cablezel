package com.josue.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name = "clientes")

public class Cliente extends Identificador{

    @Column (name = "NumeroCedula", length = 16) /*Tamaño con guiones*/
    private String num_cedula;

    @Column (name = "PrimerNombre", length = 50)
    private String primer_nombre;

    @Column (name = "SegundoNombre", length = 50)
    private String segundo_nombre;

    @Column (name = "PrimerApellido", length = 50)
    private String primer_apellido;

    @Column (name = "SegundoApellido", length = 50)
    private String segundo_apellido;

    @Column (name = "Direccion", length = 100)
    private String direccion;

    @Column (name = "Barrio")
    private String cod_barrio;

    @Column (name = "TipoCliente")
    private String id_tipo_cliente;

    @Column (name = "Numero de Telefono", length = 15)
    private String num_telefono;

    public String getNum_cedula() {
        return num_cedula;
    }

    public void setNum_cedula(String num_cedula) {
        this.num_cedula = num_cedula;
    }

    public String getPrimer_nombre() {
        return primer_nombre;
    }

    public void setPrimer_nombre(String primer_nombre) {
        this.primer_nombre = primer_nombre;
    }

    public String getSegundo_nombre() {
        return segundo_nombre;
    }

    public void setSegundo_nombre(String segundo_nombre) {
        this.segundo_nombre = segundo_nombre;
    }

    public String getPrimer_apellido() {
        return primer_apellido;
    }

    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    public String getNum_telefono() {
        return num_telefono;
    }

    public void setNum_telefono(String num_telefono) {
        this.num_telefono = num_telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCod_barrio() {
        return cod_barrio;
    }

    public void setCod_barrio(String cod_barrio) {
        this.cod_barrio = cod_barrio;
    }

    public String getId_tipo_cliente() {
        return id_tipo_cliente;
    }

    public void setId_tipo_cliente(String id_tipo_cliente) {
        this.id_tipo_cliente = id_tipo_cliente;
    }

    public Cliente(){

    }

    @Override
    public String toString() {
        return "Cliente{}";
    }


}
