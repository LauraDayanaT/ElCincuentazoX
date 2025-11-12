package com.project.elcincuentazo.interfaces;

import com.project.elcincuentazo.models.Carta;
import com.project.elcincuentazo.models.Jugador;

import java.util.Optional;

public class IAConservadora implements IASeleccionCarta{
    @Override
    public Optional<Carta> seleccionarCarta(Jugador jugador, int sumaMesa) {
        // Selecciona la primera carta válida que no exceda 50
        for (Carta carta : jugador.getMano()) {
            int valorReal = carta.getValorReal(sumaMesa);
            if (sumaMesa + valorReal <= 50) {
                return Optional.of(carta);
            }
        }
        return Optional.empty();
    }
}
