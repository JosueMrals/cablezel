package com.josue.modelo;

import javax.persistence.*;

@Entity
@Table(name="usuarios")
public class Usuario extends Identificador<Long>{
    @Column(name = "nombres", length = 40)
    private String Nombres;

    @Column(name = "apellidos", length = 40)
    private String Apellidos;

    @Column(name = "nick", length = 40)
    private String Nick;

    @Column(name = "clave", length = 40)
    private String Clave;

    public Usuario()
    {

    }

    public Usuario(String Nombres, String Apellidos, String Nick, String Clave)
    {
        this.Nombres = Nombres;
        this.Apellidos = Apellidos;
        this.Nick = Nick;
        this.Clave = Clave;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getNick() {
        return Nick;
    }

    public void setNick(String nick) {
        Nick = nick;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    @Override
    public String toString() {
        return "Usuario{}";
    }
}
