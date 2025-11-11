package com.project.elcincuentazo.controller;

import com.project.elcincuentazo.model.Carta;
import com.project.elcincuentazo.model.Juego;
import com.project.elcincuentazo.model.Jugador;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Random;

public class JuegoController {

    @FXML
    private Label lblTurno, lblSumaMesa, lblGanador;

    @FXML
    private ImageView imgCartaMesa;

    @FXML
    private HBox contenedorCartasJugador;

    private Juego juego;

    public void iniciarJuego(Juego juego) {
        this.juego = juego;
        actualizarInterfaz();
    }

    private void actualizarInterfaz() {
        Jugador jugadorActual = juego.getJugadorActual();
        lblTurno.setText("Turno de: " + jugadorActual.getNombre());
        lblSumaMesa.setText("Suma en mesa: " + juego.getSumaMesa());

        mostrarCartaMesa();
        mostrarCartasJugador();

        if (juego.hayGanador()) {
            lblGanador.setText("Ganador: " + juego.getGanador().getNombre());
            deshabilitarInterfaz();
        } else if (jugadorActual.esCPU()) {
            new Thread(()->jugarTurnoCPU(jugadorActual)).start();
        }
    }

    private void mostrarCartaMesa() {
        Carta cartaMesa = juego.getCartasMesa().get(juego.getCartasMesa().size() - 1);
        imgCartaMesa.setImage(new Image(getRutaCarta(cartaMesa)));
    }

    private void mostrarCartasJugador() {
        contenedorCartasJugador.getChildren().clear();
        Jugador jugador = juego.getJugadores().get(0); // humano

        for (Carta carta : jugador.getMano()) {
            Button btnCarta = new Button(carta.getSimbolo());
            btnCarta.setPrefSize(90, 120);
            btnCarta.setOnAction(e -> {
                if (juego.jugarCarta(jugador, carta)) {
                    juego.siguienteTurno();
                    actualizarInterfaz();
                } else {
                    mostrarAlerta("Has excedido 50 y quedas eliminado.");
                    juego.siguienteTurno();
                    actualizarInterfaz();
                }
            });
            contenedorCartasJugador.getChildren().add(btnCarta);
        }
    }

    private void jugarTurnoCPU(Jugador cpu) {
        Platform.runLater(() -> {
            try {
                Thread.sleep(1000); // pequeña pausa para simular jugada
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Carta cartaElegida = elegirCartaCPU(cpu);
            if (cartaElegida != null) {
                juego.jugarCarta(cpu, cartaElegida);
            } else {
                cpu.eliminar();
            }

            juego.siguienteTurno();
            actualizarInterfaz();
        });
    }

    private Carta elegirCartaCPU(Jugador cpu) {
        for (Carta carta : cpu.getMano()) {
            int nuevaSuma = juego.getSumaMesa() + carta.getValorReal(juego.getSumaMesa());

            if (nuevaSuma <= 50) return carta;
        }
        return null; // no puede jugar
    }

    private void deshabilitarInterfaz() {
        contenedorCartasJugador.setDisable(true);
    }

    private String getRutaCarta(Carta carta) {
        String texto = carta.getSimbolo(); // Ejemplo: "10 de Corazon"
        String palo = texto.substring(texto.lastIndexOf(" ") + 1); // obtiene "Corazon"
        String ruta = "/com/project/elcincuentazo/" + palo + ".png";
        var recurso = getClass().getResource(ruta);

        if (recurso == null) {
            System.out.println("⚠️ No se encontró la imagen: " + ruta);
            return null;
        }

        return recurso.toExternalForm();
    }



    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
