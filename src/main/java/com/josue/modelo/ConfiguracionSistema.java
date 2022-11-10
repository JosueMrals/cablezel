package com.josue.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name = "configuracion_sistema")
public class ConfiguracionSistema extends Identificador {
    @Column (name = "nombre")
    private String nombre;

    @Column (name = "valor")
    private String valor;
    public ConfiguracionSistema() {
    }
    public ConfiguracionSistema(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public String toString() {
        return "ConfiguracionSistema{" +
                "nombre='" + nombre + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
