package com.project.elcincuentazo.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JugadorTest {

    private Jugador jugador;
    private Carta carta1;

    @BeforeEach
    void setUp() {
        jugador = new Jugador("Player1", false);
        carta1 = new Carta("10", "Corazones");
    }

    @Test
    void testCrearJugador() {
        assertEquals("Player1", jugador.getNombre());
        assertFalse(jugador.esCPU());
        assertFalse(jugador.estaEliminado());
        assertTrue(jugador.getMano().isEmpty());
    }

    @Test
    void testAgregarCarta() {
        jugador.agregarCarta(carta1);

        assertEquals(1, jugador.getMano().size());
        assertFalse(jugador.getMano().isEmpty());
    }

    @Test
    void testEliminarJugador() {
        jugador.eliminar();
        assertTrue(jugador.estaEliminado());
    }

}