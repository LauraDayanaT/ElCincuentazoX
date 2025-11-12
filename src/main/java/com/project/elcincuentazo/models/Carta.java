package com.project.elcincuentazo.models;

public class Carta {

    private final String simbolo;
    private final int valorBase;
    private final String palo;

    /**
     * Metodo Constructor
     * @param simbolo
     * @param palo
     */
    public Carta(String simbolo, String palo) {
        this.valorBase = calcularValorBase(simbolo);
        this.simbolo = simbolo;
        this.palo = palo;
    }

    /**
     * Calcula el valor base de cada carta
     * @param simbolo
     */
    private int calcularValorBase(String simbolo) {
        return switch (simbolo){
            case "A" -> 1;
            case "J", "Q", "K" -> -10;
            case "9" -> 0;
            default -> Integer.parseInt(simbolo);
        };
    }

    /**
     * Calcula el valor que mas convenga segun la situacion
     * @param sumaActualMesa
     * @return valorCarta
     */
    public int getValorReal(int sumaActualMesa){
        if ("A".equals(simbolo)) {
            // condición ? valor_si_verdadero : valor_si_falso
            return (sumaActualMesa + 10 <= 50) ? 10 : 1;
        }
        return valorBase;
    }

    /**
     * Devuelve una representación lista para mostrar en la interfaz
     * @return simbolo
     */
    public String getSimboloCompleto() {
        return simbolo + " de " + palo;
    }

}
