package com.project.elcincuentazo.models;

import com.project.elcincuentazo.exceptions.MazoVacioException;
import com.project.elcincuentazo.interfaces.IASeleccionCarta;
import org.junit.jupiter.api.Test;
import com.project.elcincuentazo.models.CPU;
import com.project.elcincuentazo.models.Carta;
import com.project.elcincuentazo.interfaces.IASeleccionCarta;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class JuegoTest {
    /**
     * Prueba básica:
     * - El juego se crea sin errores.
     * - Se crean jugadores.
     * - La mesa inicia con una carta.
     * - La suma de la mesa es mayor a 0.
     */
    @Test
    void debeInicializarCorrectamenteElJuego() throws MazoVacioException {

        // Crear un juego con 2 CPUs
        Juego juego = new Juego(2);

        // Validar que hay 3 jugadores (1 humano + 2 CPU)
        assertEquals(3, juego.getJugadores().size(), "Debe haber 3 jugadores");

        // Validar que se creó la primera carta en la mesa
        assertFalse(juego.getCartasMesa().isEmpty(), "La mesa debe tener al menos una carta");

        // Validar que la suma de la mesa es mayor a 0
        assertTrue(juego.getSumaMesa() > 0, "La suma inicial de la mesa debe ser mayor a 0");

        // Si llegamos aquí, imprimimos algo en consola
        System.out.println("✔ Juego inicializado correctamente");
    }

    @Test
    void detectarGanadorCorrectamenteCuandoQuedaUnSoloJugador() throws MazoVacioException {

        // Crear juego con 2 CPUs
        Juego juego = new Juego(2);

        // Lista de jugadores
        var jugadores = juego.getJugadores();

        // Dejamos solo al jugador 0 sin eliminar
        Jugador ganadorEsperado = jugadores.get(0);

        // Eliminamos manualmente a los demás
        jugadores.get(1).eliminar();
        jugadores.get(2).eliminar();

        // Validaciones
        assertTrue(juego.hayGanador(),
                "El juego debería detectar que ya hay un ganador");

        assertEquals(ganadorEsperado, juego.getGanador(),
                "El ganador detectado debe ser el único jugador activo");

        System.out.println("Ganador detectado correctamente: " + juego.getGanador().getNombre());
    }
}