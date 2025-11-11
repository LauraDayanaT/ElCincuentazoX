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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

import java.net.URL;

public class JuegoController {

    @FXML
    private Label lblTurno, lblSumaMesa, lblGanador, lblCartaMesa; // 👈 añadimos label para la carta en mesa

    @FXML
    private ImageView imgCartaMesa; // se puede eliminar del FXML si ya no se usará

    @FXML
    private HBox contenedorCartasJugador; // humano (abajo)

    @FXML
    private HBox contenedorCPU1; // arriba
    @FXML
    private VBox contenedorCPU2; // izquierda
    @FXML
    private VBox contenedorCPU3; // derecha

    @FXML
    private Label lblCPU1, lblCPU2, lblCPU3;

    private Juego juego;
    private Image imgDorso;

    public void iniciarJuego(Juego juego) {
        this.juego = juego;

        // Cargar dorso desde resources
        URL recurso = getClass().getResource("/com/project/elcincuentazo/Dorso.png");
        if (recurso != null) {
            imgDorso = new Image(recurso.toExternalForm());
        } else {
            System.err.println("⚠️ No se encontró Dorso.png en resources/com/project/elcincuentazo/");
        }

        actualizarInterfaz();
    }

    private void actualizarInterfaz() {
        Jugador jugadorActual = juego.getJugadorActual();
        lblTurno.setText("Turno de: " + jugadorActual.getNombre());
        lblSumaMesa.setText("Suma en mesa: " + juego.getSumaMesa());
        lblGanador.setText("");

        mostrarCartaMesa();
        mostrarCartasJugador();
        mostrarCartasCPU();

        if (juego.hayGanador()) {
            lblGanador.setText("Ganador: " + juego.getGanador().getNombre());
            deshabilitarInterfaz();
        } else if (jugadorActual.esCPU()) {
            jugarTurnoCPU(jugadorActual);
        }
    }

    // ✅ ahora muestra la carta con texto y emoji, no imagen
    private void mostrarCartaMesa() {
        Carta cartaMesa = juego.getCartasMesa().get(juego.getCartasMesa().size() - 1);
        String simbolo = obtenerSimboloCarta(cartaMesa);

        lblCartaMesa.setText(simbolo);
        lblCartaMesa.setFont(Font.font("Segoe UI Emoji", FontWeight.BOLD, 64));

        if (simbolo.contains("♥") || simbolo.contains("♦")) {
            lblCartaMesa.setTextFill(Color.RED);
        } else {
            lblCartaMesa.setTextFill(Color.BLACK);
        }
    }

    private void mostrarCartasJugador() {
        contenedorCartasJugador.getChildren().clear();
        Jugador jugador = juego.getJugadores().get(0); // humano

        for (Carta carta : jugador.getMano()) {
            String simbolo = obtenerSimboloCarta(carta);

            Button btnCarta = new Button(simbolo);
            btnCarta.setPrefSize(96, 126);
            btnCarta.setFont(Font.font("Segoe UI Emoji", FontWeight.BOLD, 24));
            btnCarta.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: #cccccc; -fx-border-width: 2; -fx-background-color: white;");


            if (simbolo.contains("♥") || simbolo.contains("♦")) {
                btnCarta.setTextFill(Color.RED);
            } else {
                btnCarta.setTextFill(Color.BLACK);
            }

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
    //obtener simbolos
    private String obtenerSimboloCarta(Carta carta) {
        String valor = carta.getSimbolo().split(" ")[0];
        String palo = carta.getSimbolo().split(" ")[2].toLowerCase();

        return switch (palo) {
            case "corazon" -> valor + "♥";
            case "diamante" -> valor + "♦";
            case "trebol" -> valor + "♣";
            case "pica" -> valor + "♠";
            default -> valor;
        };
    }

    private void mostrarCartasCPU() {
        contenedorCPU1.getChildren().clear();
        contenedorCPU2.getChildren().clear();
        contenedorCPU3.getChildren().clear();
        lblCPU1.setText("");
        lblCPU2.setText("");
        lblCPU3.setText("");

        int totalJugadores = juego.getJugadores().size();

        for (int i = 1; i < totalJugadores; i++) {
            Jugador cpu = juego.getJugadores().get(i);
            switch (i) {
                case 1 -> {
                    lblCPU1.setText(cpu.getNombre());
                    for (int j = 0; j < cpu.getMano().size(); j++) {
                        ImageView cartaDorso = new ImageView(imgDorso);
                        cartaDorso.setFitWidth(60);
                        cartaDorso.setFitHeight(80);
                        contenedorCPU1.getChildren().add(cartaDorso);
                    }
                }
                case 2 -> {
                    lblCPU2.setText(cpu.getNombre());
                    for (int j = 0; j < cpu.getMano().size(); j++) {
                        ImageView cartaDorso = new ImageView(imgDorso);
                        cartaDorso.setFitWidth(60);
                        cartaDorso.setFitHeight(80);
                        contenedorCPU2.getChildren().add(cartaDorso);
                    }
                }
                case 3 -> {
                    lblCPU3.setText(cpu.getNombre());
                    for (int j = 0; j < cpu.getMano().size(); j++) {
                        ImageView cartaDorso = new ImageView(imgDorso);
                        cartaDorso.setFitWidth(60);
                        cartaDorso.setFitHeight(80);
                        contenedorCPU3.getChildren().add(cartaDorso);
                    }
                }
            }
        }
    }

    private void jugarTurnoCPU(Jugador cpu) {
        Platform.runLater(() -> {
            try {
                Thread.sleep(1000);
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
            int nuevaSuma = juego.getSumaMesa() + carta.getValor();
            if (carta.getSimbolo().startsWith("A")) {
                if (juego.getSumaMesa() + 10 <= 50) nuevaSuma = juego.getSumaMesa() + 10;
                else nuevaSuma = juego.getSumaMesa() + 1;
            }
            if (nuevaSuma <= 50) return carta;
        }
        return null;
    }

    private void deshabilitarInterfaz() {
        contenedorCartasJugador.setDisable(true);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
