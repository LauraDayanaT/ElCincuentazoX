package com.project.elcincuentazo.interfaces;

import com.project.elcincuentazo.models.Carta;
import com.project.elcincuentazo.models.Jugador;

import java.util.*;

public interface IASeleccionCarta {
    Optional< Carta> seleccionarCarta(Jugador jugador, int sumaMesa);
}
