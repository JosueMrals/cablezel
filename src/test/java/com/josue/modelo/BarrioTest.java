package com.josue.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BarrioTest {

    @Test
    public void testBarrio() {
        Barrio barrio = new Barrio("Jorge Navarro", "Donde esta ubicado ENEL");

        assertEquals("Jorge Navarro", barrio.toString());
    }

}
