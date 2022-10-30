package com.josue.modelo;

public class ConfiguracionSistema extends Identificador {
    private String nombre;
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
