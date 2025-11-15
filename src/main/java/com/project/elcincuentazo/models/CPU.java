package com.project.elcincuentazo.models;

import com.project.elcincuentazo.interfaces.IASeleccionCarta;
import java.util.Optional;
/**
 * Representa un jugador controlado por la inteligencia artificial (CPU).
 * Utiliza una estrategia para decidir qué carta jugar dependiendo del estado actual del juego.
 */
public class CPU extends Jugador {

    private final IASeleccionCarta estrategia;
    /**
     * Crea un jugador CPU con una estrategia específica.
     *
     * @param nombre nombre del jugador CPU.
     * @param estrategia estrategia que define cómo selecciona cartas la IA.
     */
    public CPU(String nombre, IASeleccionCarta estrategia) {
        super(nombre, true);
        this.estrategia = estrategia;
    }
    /**
     * Permite a la CPU elegir una carta según la estrategia configurada.
     *
     * @param sumaMesa valor actual acumulado en la mesa.
     * @return un Optional que contiene la carta seleccionada, o vacío si la CPU decide no jugar o no puede jugar.
     */
    public Optional<Carta> elegirCarta(int sumaMesa) {
        return estrategia.seleccionarCarta(this, sumaMesa);
    }
}
