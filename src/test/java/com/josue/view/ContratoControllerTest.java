package com.josue.view;

import com.josue.modelo.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContratoControllerTest {

    /**
     * La práctica de las pruebas se basa en probar un poco, codificar un poco, probar un poco, codificar un poco.
     * Esto aumenta la productividad del programador y la estabilidad del código, lo que a su vez reduce el estrés del
     * programador y el tiempo dedicado a la depuración.
     */

    @Test
    void registrarContratos() {
        Servicio servicio = new Servicio("Internet", "Servicio de Internet", "1000");

        TipoContrato tipoContrato = new TipoContrato("C_Arren", "Contrato de arrendamiento",
                "Contrato de arrendamiento", servicio, "1");

        Barrio barrio = new Barrio("San Jose", "Barrio de San Jose");

        Cliente cliente = new Cliente(barrio, "610-060700-1004J", "Victor",
                "Manuel", "Zeledon",
                "Chavarria", "Calle 1", "809-123-4567");

        Contrato contrato = new Contrato(tipoContrato, LocalDate.now(), "Contrato de arrendamiento"
                , cliente);
        assertEquals("Contrato{tipocontrato=Contrato de arrendamiento, cliente=Victor Manuel Zeledon Chavarria, " +
                "fecha_contrato=" + LocalDate.now() + ", " +
                "descripcion=Contrato de arrendamiento}", contrato.toString());
    }
}