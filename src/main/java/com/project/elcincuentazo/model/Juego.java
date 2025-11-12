package com.project.elcincuentazo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego {

    private final List<Jugador> jugadores;
    private final Mazo mazo;
    private final List<Carta> cartasMesa;
    private int sumaMesa;
    private int turnoActual;

    public Juego(int numCPU) {
        this.jugadores = new ArrayList<>();
        this.mazo = new Mazo();
        this.cartasMesa = new ArrayList<>();
        this.sumaMesa = 0;
        this.turnoActual = 0;
        inicializarJugadores(numCPU);
        repartirCartasIniciales();
        iniciarMesa();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000); /* hilo para revisar si el mazo esta vacio**/
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (mazo.estaVacio()) {
                    reciclarCartasDeMesa();
                    System.out.println("♻️ Se reciclaron las cartas al mazo automáticamente.");
                }
            }
        }).start();
    }

    private void inicializarJugadores(int numCPU) {
        jugadores.add(new Jugador("Jugador", false)); // humano
        for (int i = 1; i <= numCPU; i++) {
            jugadores.add(new Jugador("CPU " + i, true));
        }
    }

    private void repartirCartasIniciales() {
        for (Jugador jugador : jugadores) {
            for (int i = 0; i < 4; i++) {
                jugador.agregarCarta(mazo.tomarCarta());
            }
        }
    }

    private void iniciarMesa() {
        Carta cartaInicial = mazo.tomarCarta();
        cartasMesa.add(cartaInicial);
        sumaMesa = cartaInicial.getValorReal(0);
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public Mazo getMazo() {
        return mazo;
    }

    public List<Carta> getCartasMesa() {
        return cartasMesa;
    }

    public int getSumaMesa() {
        return sumaMesa;
    }

    public Jugador getJugadorActual() {
        return jugadores.get(turnoActual);
    }

    public void siguienteTurno() {
        do {
            turnoActual = (turnoActual + 1) % jugadores.size();
        } while (jugadores.get(turnoActual).estaEliminado());
    }

    public boolean jugarCarta(Jugador jugador, Carta carta) {
        int nuevoTotal = calcularNuevaSuma(carta);
        if (nuevoTotal > 50) {
            jugador.eliminar();
            return false;
        }

        jugador.eliminarCarta(carta);
        cartasMesa.add(carta);
        sumaMesa = nuevoTotal;

        if (!mazo.estaVacio()) {
            jugador.agregarCarta(mazo.tomarCarta());
        } else {
            reciclarCartasDeMesa(); /* devuelve las cartas jugadas al mazo **/
            jugador.agregarCarta(mazo.tomarCarta());
        }

        return true;

    }

    private int calcularNuevaSuma(Carta carta) {
        int valor = carta.getValorReal(sumaMesa);
        return sumaMesa + valor;
    }


    public boolean hayGanador() {
        return jugadores.stream().filter(j -> !j.estaEliminado()).count() == 1;
    }

    public Jugador getGanador() {
        return jugadores.stream()
                .filter(j -> !j.estaEliminado())
                .findFirst()
                .orElse(null);
    }

    public void reciclarCartasDeMesa() {
        if (mazo.estaVacio() && cartasMesa.size() > 1) {
            Carta ultima = cartasMesa.remove(cartasMesa.size() - 1);
            mazo.agregarCartasAlFinal(new ArrayList<>(cartasMesa));
            cartasMesa.clear();
            cartasMesa.add(ultima);
        }
    }
}
