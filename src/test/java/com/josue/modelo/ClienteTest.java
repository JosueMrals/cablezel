package com.josue.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteTest {

    @Test
    void testCliente() {
        Barrio barrio = new Barrio("San Jose", "Barrio de San Jose");

        Cliente cliente = new Cliente(barrio, "610-060700-1004J", "Victor",
                "Manuel", "Zeledon",
                "Chavarria", "Calle 1", "809-123-4567");

        assertEquals("Victor Manuel Zeledon Chavarria", cliente.toString());
    }

}
