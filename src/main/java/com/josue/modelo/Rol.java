package com.josue.modelo;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Rol extends Identificador {

    @Column(name = "nombre", length = 40)
    private String nombre;

    @Column(name = "descripcion", length = 40)
    private String descripcion;

    public Rol(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol(Usuario usuario, String nombre, String descripcion) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Rol() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

