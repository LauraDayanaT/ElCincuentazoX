package com.project.elcincuentazo.model;

public class Carta {

    private final String simbolo;
    private final int valor;

    public Carta(String simbolo, int valor) {
        this.simbolo = simbolo;
        this.valor = valor;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public int getValor() {
        return valor;
    }

    public int getValorNumerico() { // ✅ agregado
        return valor;
    }

    @Override
    public String toString() {
        return simbolo + " (" + valor + ")";
    }
}
