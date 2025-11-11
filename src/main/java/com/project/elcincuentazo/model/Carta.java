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

    /**
     * Devuelve el valor real de la carta según las reglas del juego Cincuentazo.
     * - 2 a 8 y 10 suman su número.
     * - 9 vale 0.
     * - J, Q, K restan 10.
     * - A puede valer 1 o 10 según convenga.
     */
    public int getValorReal(int sumaActualMesa) {
        String valorStr = String.valueOf(this.valor).toUpperCase();


        switch (valorStr) {
            case "J":
            case "Q":
            case "K":
                return -10;
            case "9":
                return 0;
            case "A":
                /** Si sumar 10 no excede 50, usa 10. Si no, usa 1 **/
                return (sumaActualMesa + 10 <= 50) ? 10 : 1;
            default:
                /** Para números del 2 al 10 **/
                try {
                    return Integer.parseInt(valorStr);
                } catch (NumberFormatException e) {
                    return 0;
                }
        }
    }


}
