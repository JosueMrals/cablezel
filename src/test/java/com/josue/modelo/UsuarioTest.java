package com.josue.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testUsuario() {
        Usuario usuario = new Usuario("Josue", "1234", "josue", "josue@mail.com");
        assertEquals("Usuario{nombrecompleto='Josue', nickusuario='josue', password='1234', " +
                "email='josue@mail.com'}", usuario.toString());
    }
}