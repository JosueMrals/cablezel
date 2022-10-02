package com.josue.modelo;

import javax.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario extends Identificador {

    @Column(name = "nombrecompleto", length = 40)
    private String nombrecompleto;

    @Column(name = "nickusuario", length = 40)
    private String nickusuario;

    @Column(name = "password", length = 40)
    private String password;

    @Column(name = "email", length = 40)
    private String email;

    public Usuario() {

    }

    public Usuario(String nombrecompleto, String password, String nickusuario, String email) {
        this.nombrecompleto = nombrecompleto;
        this.password = password;
        this.nickusuario = nickusuario;
        this.email = email;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getNickusuario() {
        return nickusuario;
    }

    public void setNickusuario(String nickusuario) {
        this.nickusuario = nickusuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombrecompleto='" + nombrecompleto + '\'' +
                ", nickusuario='" + nickusuario + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
