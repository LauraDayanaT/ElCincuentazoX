package com.project.elcincuentazo.models;

import com.project.elcincuentazo.exceptions.MazoVacioException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Mazo {

    private final List <Carta> cartas;

    /**
     * Metodo constructor
     */
    public Mazo() {
        cartas = new ArrayList<>();
        inicializarMazo();
        barajar();
    }

    /**
     * Crea las 52 cartas que se usaran en el juego
     */
    private void inicializarMazo() {
        String[] palos = {"Corazon", "Pica", "Diamante", "Trebol"};
        String[] simbolos = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        for (String palo : palos) {
            for (String simbolo : simbolos) {
                cartas.add(new Carta(simbolo,palo));
            }
        }
    }

    /**
     * Desordena aleatoriamente la lista cartas usando "shuffle"
     */
    public void barajar() {
        Collections.shuffle(cartas, new Random());
    }

    /**
     * Agrega una lista de cartas al final del mazo.
     * Luego mezcla todas las cartas del mazo.
     * @return
     * @throws MazoVacioException
     */
    public Carta tomarCarta() throws MazoVacioException {
        if (cartas.isEmpty())
            throw new MazoVacioException("El mazo esta vacio");
        return cartas.remove(0);
    }

    /**
     * Comprueba si el mazo tiene cartas
     * @param nuevasCartas
     */
    public void agregarCartasAlFinal(List <Carta> nuevasCartas) {
        cartas.addAll(nuevasCartas);
        barajar();
    }

    /**
     * Retorna "true" si no hay cartas en el mazo.
     * @return boolean
     */
    public boolean estaVacio() {
        return cartas.isEmpty();
    }

    /**
     * Devuelve el número de cartas restantes
     * @return size
     */
    public int getTamaño() {
        return cartas.size();
    }
}
