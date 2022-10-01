package com.josue.modelo;

import javax.persistence.*;
import java.text.DateFormat;

@Entity
@Table (name = "facturas")

public class Factura extends Identificador{

    //Relation to Cliente table
    @ManyToOne
    @JoinColumn (name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    public Cliente getCliente() {return cliente;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}
    //Relation to Usuario table
    @ManyToOne
    @JoinColumn (name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //Factura table
    @Column (name = "total")
    private Float total;

    @Column (name = "fecha_factura")
    private DateFormat fecha_factura;

    @Column (name = "estado")
    private String estado;
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public DateFormat getFecha_factura() {
        return fecha_factura;
    }

    public void setFecha_factura(DateFormat fecha_factura) {
        this.fecha_factura = fecha_factura;
    }
}
