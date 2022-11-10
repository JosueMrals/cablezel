package com.josue.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    public void testUsuario() {
        Usuario usuario = new Usuario("Josue", "1234", "josue", "admin", "josue@mail.com");
        assertEquals("Usuario{nombrecompleto='Josue', nickusuario='josue', rol='admin', password='1234', " +
                "email='josue@mail.com'}", usuario.toString());
    }
}