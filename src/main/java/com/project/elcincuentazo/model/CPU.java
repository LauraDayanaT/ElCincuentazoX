package com.project.elcincuentazo.model;

import java.util.Optional;

public class CPU extends Jugador {

    public CPU(String nombre) {
        super(nombre, true);
    }

    public Optional<Carta> elegirCarta(int sumaMesa) {
        for (Carta carta : getMano()) {
            int valor = carta.getValorNumerico();

            // ✅ usar simbolo, no valor numérico
            if (carta.getSimbolo().startsWith("A")) {
                if (sumaMesa + 10 <= 50) valor = 10;
                else valor = 1;
            }

            if (sumaMesa + valor <= 50) {
                return Optional.of(carta);
            }
        }
        return Optional.empty();
    }
}
