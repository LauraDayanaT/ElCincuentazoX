package com.project.elcincuentazo.models;

import java.util.ArrayList;
import java.util.List;

public class Jugador {

    private final String nombre;
    private final boolean esCPU;
    private final List<Carta> mano;
    private boolean eliminado;

    public Jugador(String nombre, boolean esCPU) {
        this.nombre = nombre;
        this.esCPU = esCPU;
        this.mano = new ArrayList<>();
        this.eliminado = false;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean esCPU() {
        return esCPU;
    }

    public List<Carta> getMano() {
        return mano;
    }

    public void agregarCarta(Carta carta) {
        if (carta != null) mano.add(carta);
    }

    public void eliminarCarta(Carta carta) {
        mano.remove(carta);
    }

    public boolean estaEliminado() {
        return eliminado;
    }

    public void eliminar() {
        eliminado = true;
    }

    public void limpiarMano() {
        mano.clear();
    }

    @Override
    public String toString() {
        return nombre + (esCPU ? " (CPU)" : "");
    }
}
