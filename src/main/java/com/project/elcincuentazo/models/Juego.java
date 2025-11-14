package com.project.elcincuentazo.models;

import com.project.elcincuentazo.exceptions.MazoVacioException;
import com.project.elcincuentazo.interfaces.IAConservadora;
import com.project.elcincuentazo.interfaces.IASeleccionCarta;

import java.util.ArrayList;
import java.util.List;

public class Juego {

    private final List<Jugador> jugadores;
    private final List<Carta> cartasMesa;
    private final Mazo mazo;

    private int turnoActual;
    private int sumaMesa;

    public Juego(int numCPU) throws MazoVacioException {
        this.cartasMesa = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        this.mazo = new Mazo();

        this.turnoActual = 0;
        this.sumaMesa = 0;

        inicializarJugadores(numCPU);
        repartirCartasIniciales();
        iniciarMesa();
    }

    private void inicializarJugadores(int numCPU) {
        jugadores.add(new Jugador("Jugador", false)); // humano
        IASeleccionCarta estrategia = new IAConservadora(); // CPU
        for (int i = 1; i <= numCPU; i++) {
            jugadores.add(new Jugador("CPU " + i, true));
        }
    }

    private void repartirCartasIniciales() throws MazoVacioException {
        for (Jugador jugador : jugadores) {
            for (int i = 0; i < 4; i++) {
                jugador.agregarCarta(mazo.tomarCarta());
            }
        }
    }

    private void iniciarMesa() throws MazoVacioException {
        Carta cartaInicial = mazo.tomarCarta();
        cartasMesa.add(cartaInicial);
        sumaMesa = cartaInicial.getValorReal(0);
    }

    // === GETTERS ===
    public Jugador getJugadorActual() {
        return jugadores.get(turnoActual);
    }
    public List<Jugador> getJugadores() {
        return jugadores;
    }
    public List<Carta> getCartasMesa() {
        return cartasMesa;
    }
    public int getSumaMesa() {
        return sumaMesa;
    }
    public Mazo getMazo() {
        return mazo;
    }

    public void siguienteTurno() {
        do {
            turnoActual = (turnoActual + 1) % jugadores.size();
        } while (jugadores.get(turnoActual).estaEliminado());
    }
    private void enviarCartasAlMazo(Jugador jugador) {
        List<Carta> cartas = new ArrayList<>(jugador.getMano());
        jugador.limpiarMano();
        mazo.agregarCartasAlFinal(cartas);
    }

    public boolean jugarCarta(Jugador jugador, Carta carta) {
        int valor = carta.getValorReal(sumaMesa);
        int nuevoTotal = sumaMesa + valor;

        if (nuevoTotal >= 50) {
            enviarCartasAlMazo(jugador);
            jugador.eliminar();
            return false;
        }

        jugador.eliminarCarta(carta);
        cartasMesa.add(carta);
        sumaMesa = nuevoTotal;

        if (mazo.estaVacio()) {
            reciclarCartasMesa();
        }

        try {
            jugador.agregarCarta(mazo.tomarCarta());
        } catch (MazoVacioException e) {
            // si aún está vacío, no pasa nada
        }
        return true;
    }

    private void reciclarCartasMesa() {
        if (cartasMesa.size() > 1) {
            List<Carta> recicladas = new ArrayList<>(cartasMesa.subList(0, cartasMesa.size() - 1));
            cartasMesa.clear();
            cartasMesa.add(recicladas.get(recicladas.size() - 1)); // dejar última
            mazo.agregarCartasAlFinal(recicladas);
        }
    }

    public boolean hayGanador() {
        return getJugadoresActivos().size() == 1;
    }

    public Jugador getGanador() {
        List<Jugador> activos = getJugadoresActivos();
        return activos.size() == 1 ? activos.get(0) : null;
    }

    private List<Jugador> getJugadoresActivos() {
        List<Jugador> activos = new ArrayList<>();
        for (Jugador j : jugadores) {
            if (!j.estaEliminado()) activos.add(j);
        }
        return activos;
    }

    public boolean tieneJugadasPosibles(Jugador jugador) {
        for (Carta c : jugador.getMano()) {
            if (sumaMesa + c.getValorReal(sumaMesa) <= 50) return true;
        }
        return false;
    }

}
