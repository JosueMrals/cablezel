package com.josue.util;

import com.josue.modelo.Usuario;

public final class ManejadorUsuario {
    private Usuario usuario;
    private Boolean servidorConfigurado;
    private static final ManejadorUsuario instance = new ManejadorUsuario();

    public static ManejadorUsuario getInstance() {
        return instance;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getServidorConfigurado() {
        return servidorConfigurado;
    }

    public void setServidorConfigurado(Boolean servidorConfigurado) {
        this.servidorConfigurado = servidorConfigurado;
    }
}
