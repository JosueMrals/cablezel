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

    ///Relation to DetallesFacturas table
    @ManyToOne
    @JoinColumn (name = "detalles_id", referencedColumnName = "id")
    private DetalleFactura detalleFactura;

    public DetalleFactura getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFactura = detalleFactura;
    }

    //Factura table
    @Column (name = "num_factura", unique = true)
    private Integer num_factura;

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

    public Integer getNum_factura() {
        return num_factura;
    }

    public void setNum_factura(Integer num_factura) {
        this.num_factura = num_factura;
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
