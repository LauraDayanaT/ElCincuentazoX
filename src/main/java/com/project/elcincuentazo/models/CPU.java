package com.project.elcincuentazo.models;

import com.project.elcincuentazo.interfaces.IASeleccionCarta;
import java.util.Optional;

public class CPU extends Jugador {

    private final IASeleccionCarta estrategia;

    public CPU(String nombre, IASeleccionCarta estrategia) {
        super(nombre, true);
        this.estrategia = estrategia;
    }
    public Optional<Carta> elegirCarta(int sumaMesa) {
        return estrategia.seleccionarCarta(this, sumaMesa);
    }
}
