package com.josue.modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;

@Entity
@Table (name = "clientes")

public class Cliente extends Identificador{

    //Relation to Barrio table
    @ManyToOne
    @JoinColumn(name = "BarrioId", referencedColumnName = "id")
    private Barrio barrio;

    public Barrio getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrio barrio) {
        this.barrio = barrio;
    }

    //Relation to Contrato table
    @ManyToOne
    @JoinColumn (name = "ContratoId", referencedColumnName = "id")
    private Contrato contrato;

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    //Cliente table
    @Column (name = "num_cedula")
    private String num_cedula;

    @Column (name = "primer_nombre")
    private String primer_nombre;

    @Column (name = "segundo_nombre")
    private String segundo_nombre;

    @Column (name = "primer_apellido")
    private String primer_apellido;

    @Column (name = "segundo_apellido")
    private String segundo_apellido;

    @Column (name = "direccion")
    private String direccion;

    @Column (name = "num_telefono")
    private String num_telefono;

    public String getNum_cedula() { return num_cedula; }

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

    public Cliente(){

    }

    @Override
    public String toString() {
        return "Cliente{}";
    }

    public ObservableList<Cliente> getClientes() {
        ObservableList<Cliente> obs = FXCollections.observableArrayList();

        return obs;
    }


}
