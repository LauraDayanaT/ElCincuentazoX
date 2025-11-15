package com.project.elcincuentazo.models;

import java.util.ArrayList;
import java.util.List;
/**
 * Representa a un jugador dentro del juego El Cincuentazo.
 * Un jugador puede ser humano o CPU, tiene una mano de cartas
 * y puede ser eliminado si pierde una ronda.
 */
public class Jugador {

    private final String nombre;
    private final boolean esCPU;
    private final List<Carta> mano;
    private boolean eliminado;
    /**
     * Crea un nuevo jugador.
     *
     * @param nombre nombre del jugador.
     * @param esCPU true si el jugador es controlado por la IA, false si es humano.
     */
    public Jugador(String nombre, boolean esCPU) {
        this.nombre = nombre;
        this.esCPU = esCPU;
        this.mano = new ArrayList<>();
        this.eliminado = false;
    }
    /**
     * @return nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * @return true si es un jugador IA.
     */
    public boolean esCPU() {
        return esCPU;
    }
    /**
     * @return lista de cartas que tiene el jugador en la mano.
     */
    public List<Carta> getMano() {
        return mano;
    }
    /**
     * Agrega una carta a la mano del jugador.
     *
     * @param carta carta que se va a añadir.
     */
    public void agregarCarta(Carta carta) {
        if (carta != null) mano.add(carta);
    }
    /**
     * Elimina una carta específica de la mano del jugador.
     *
     * @param carta carta que se desea remover.
     */
    public void eliminarCarta(Carta carta) {
        mano.remove(carta);
    }

    /**
     * @return true si el jugador ya fue eliminado del juego.
     */
    public boolean estaEliminado() {
        return eliminado;
    }
    /**
     * Marca al jugador como eliminado.
     */
    public void eliminar() {
        eliminado = true;
    }
    /**
     * Elimina todas las cartas de la mano del jugador.
     */
    public void limpiarMano() {
        mano.clear();
    }
    /**
     * Representación textual del jugador.
     *
     * @return nombre y si es CPU en formato "Nombre (CPU)".
     */
    @Override
    public String toString() {
        return nombre + (esCPU ? " (CPU)" : "");
    }
}
