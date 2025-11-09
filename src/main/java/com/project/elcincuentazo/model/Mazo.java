package com.project.elcincuentazo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Mazo {

    private final List<Carta> cartas;

    public Mazo() {
        cartas = new ArrayList<>();
        inicializarMazo();
        barajar();
    }

    private void inicializarMazo() {
        String[] palos = {"Corazon", "Pica", "Diamante", "Trebol"};
        String[] simbolos = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String palo : palos) {
            for (String simbolo : simbolos) {
                int valor = obtenerValor(simbolo);
                cartas.add(new Carta(simbolo + " de " + palo, valor));
            }
        }
    }

    private int obtenerValor(String simbolo) {
        return switch (simbolo) {
            case "A" -> 1;
            case "J", "Q", "K" -> -10;
            case "9" -> 0;
            default -> Integer.parseInt(simbolo);
        };
    }

    public void barajar() {
        Collections.shuffle(cartas, new Random());
    }

    public Carta tomarCarta() {
        if (cartas.isEmpty()) return null;
        return cartas.remove(0);
    }

    public void agregarCartasAlFinal(List<Carta> nuevasCartas) {
        cartas.addAll(nuevasCartas);
    }

    public boolean estaVacio() {
        return cartas.isEmpty();
    }

    public int getTamaño() {
        return cartas.size();
    }
}
