package com.josue.modelo;

import javax.persistence.*;
import java.time.LocalDate;

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

    //Relation to Contrato table
    @ManyToOne
    @JoinColumn (name = "contrato_id", referencedColumnName = "id")
    private Contrato contrato;
    public Contrato getContrato() { return contrato; }
    public void setContrato(Contrato contrato) { this.contrato = contrato; }

    //Factura table
    @Column (name = "total")
    private Float total;
    @Column (name = "fecha_factura")
    private LocalDate fecha_factura;
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
    public LocalDate getFecha_factura() {
        return fecha_factura;
    }
    public void setFecha_factura(LocalDate fecha_factura) {
        this.fecha_factura = fecha_factura;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + getId() +
                "cliente=" + cliente +
                ", usuario=" + usuario +
                ", total=" + total +
                ", fecha_factura=" + fecha_factura +
                ", estado='" + estado + '\'' +
                '}';
    }
}
