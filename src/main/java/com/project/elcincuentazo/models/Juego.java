package com.project.elcincuentazo.models;

import com.project.elcincuentazo.exceptions.MazoVacioException;
import com.project.elcincuentazo.interfaces.IAConservadora;
import com.project.elcincuentazo.interfaces.IASeleccionCarta;

import java.util.ArrayList;
import java.util.List;
/**
 * Representa la lógica principal del juego El Cincuentazo.
 *
 * Administra jugadores, turnos, la mesa, el mazo y las reglas del juego.
 * Se encarga de repartir cartas, validar jugadas y determinar el ganador.
 */
public class Juego {

    private final List<Jugador> jugadores;
    private final List<Carta> cartasMesa;
    private final Mazo mazo;

    private int turnoActual;
    private int sumaMesa;
    /**
     * Crea una nueva partida con el número indicado de CPUs.
     *
     * @param numCPU número de jugadores IA que participarán.
     * @throws MazoVacioException si el mazo no tiene suficientes cartas para iniciar la partida.
     */
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
    /**
     * Crea el jugador humano y los jugadores CPU.
     *
     * @param numCPU cantidad de jugadores IA.
     */
    private void inicializarJugadores(int numCPU) {
        jugadores.add(new Jugador("Jugador", false)); // humano
        IASeleccionCarta estrategia = new IAConservadora(); // CPU
        for (int i = 1; i <= numCPU; i++) {
            jugadores.add(new Jugador("CPU " + i, true));
        }
    }
    /**
     * Entrega cuatro cartas a cada jugador.
     *
     * @throws MazoVacioException si el mazo se agota antes de repartir.
     */
    private void repartirCartasIniciales() throws MazoVacioException {
        for (Jugador jugador : jugadores) {
            for (int i = 0; i < 4; i++) {
                jugador.agregarCarta(mazo.tomarCarta());
            }
        }
    }

    /**
     * Coloca la primera carta en la mesa e inicializa la suma.
     * @throws MazoVacioException si no hay cartas para iniciar.
     */
    private void iniciarMesa() throws MazoVacioException {
        Carta cartaInicial = mazo.tomarCarta();
        cartasMesa.add(cartaInicial);
        sumaMesa = cartaInicial.getValorReal(0);
    }

    /** @return jugador cuyo turno está activo. */
    public Jugador getJugadorActual() {
        return jugadores.get(turnoActual);
    }

    /** @return lista completa de jugadores. */
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    /** @return cartas actualmente en la mesa. */
    public List<Carta> getCartasMesa() {
        return cartasMesa;
    }

    /** @return suma actual de la mesa. */
    public int getSumaMesa() {
        return sumaMesa;
    }

    /** @return mazo del juego. */
    public Mazo getMazo() {
        return mazo;
    }

    /**
     * Avanza al siguiente turno, saltando jugadores eliminados.
     */
    public void siguienteTurno() {
        do {
            turnoActual = (turnoActual + 1) % jugadores.size();
        } while (jugadores.get(turnoActual).estaEliminado());
    }

    /**
     * Envía todas las cartas del jugador al mazo.
     *
     * @param jugador jugador eliminado o penalizado.
     */
    private void enviarCartasAlMazo(Jugador jugador) {
        List<Carta> cartas = new ArrayList<>(jugador.getMano());
        jugador.limpiarMano();
        mazo.agregarCartasAlFinal(cartas);
    }

    /**
     * Procesa la jugada de un jugador.
     *
     * @param jugador jugador que realiza la jugada.
     * @param carta carta que jugará.
     * @return true si la jugada fue válida, false si el jugador se elimina.
     */
    public boolean jugarCarta(Jugador jugador, Carta carta) {
        int valor = carta.getValorReal(sumaMesa);
        int nuevoTotal = sumaMesa + valor;

        if (nuevoTotal > 50) {
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

    /**
     * Recicla todas las cartas de la mesa excepto la última.
     */
    private void reciclarCartasMesa() {
        if (cartasMesa.size() > 1) {
            List<Carta> recicladas = new ArrayList<>(cartasMesa.subList(0, cartasMesa.size() - 1));
            cartasMesa.clear();
            cartasMesa.add(recicladas.get(recicladas.size() - 1)); // dejar última
            mazo.agregarCartasAlFinal(recicladas);
        }
    }

    /**
     * @return true si solo queda un jugador activo.
     */
    public boolean hayGanador() {
        return getJugadoresActivos().size() == 1;
    }

    /**
     * @return jugador ganador o null si aún no hay uno.
     */
    public Jugador getGanador() {
        List<Jugador> activos = getJugadoresActivos();
        return activos.size() == 1 ? activos.get(0) : null;
    }

    /**
     * Obtiene la lista de jugadores que siguen en la partida.
     *
     * @return lista de jugadores activos.
     */
    private List<Jugador> getJugadoresActivos() {
        List<Jugador> activos = new ArrayList<>();
        for (Jugador j : jugadores) {
            if (!j.estaEliminado()) activos.add(j);
        }
        return activos;
    }

    /**
     * Determina si un jugador puede realizar alguna jugada válida.
     *
     * @param jugador jugador que se quiere evaluar.
     * @return true si tiene al menos una carta que no supere 50.
     */
    public boolean tieneJugadasPosibles(Jugador jugador) {
        for (Carta c : jugador.getMano()) {
            if (sumaMesa + c.getValorReal(sumaMesa) <= 50) return true;
        }
        return false;
    }

}
