package com.project.elcincuentazo.controller;

import com.project.elcincuentazo.models.Carta;
import com.project.elcincuentazo.models.Juego;
import com.project.elcincuentazo.models.Jugador;
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

    @FXML private Label lblTurno, lblSumaMesa, lblGanador, lblCartaMesa; // 👈 añadimos label para la carta en mesa
    @FXML private ImageView imgCartaMesa; // se puede eliminar del FXML si ya no se usará
    @FXML private HBox contenedorCartasJugador; // humano (abajo)

    @FXML private HBox contenedorCPU1; // arriba
    @FXML private VBox contenedorCPU2; // izquierda
    @FXML private VBox contenedorCPU3; // derecha

    @FXML private Label lblCPU1, lblCPU2, lblCPU3;

    private Juego juego;
    private Image imgDorso;

    public void iniciarJuego(Juego juego) {
        this.juego = juego;

        // Cargar dorso desde resources
        URL recurso = getClass().getResource("/com/project/elcincuentazo/imgDorso.png");
        if (recurso != null) {
            imgDorso = new Image(recurso.toExternalForm());
        } else {
            System.err.println("⚠️ No se encontró Dorso.png en resources/com/project/elcincuentazo/");
        }

        actualizarInterfaz();
    }

    private void actualizarInterfaz() {
        lblSumaMesa.setText("Suma en mesa: " + juego.getSumaMesa());
        lblGanador.setText("");

        if (juego.hayGanador()){
            lblGanador.setText("Ganador: " + juego.getGanador().getNombre());
            deshabilitarInterfaz();
            return;
        }

        Jugador jugadorActual = juego.getJugadorActual();
        lblTurno.setText("Turno de: " + jugadorActual.getNombre());

        mostrarCartaMesa();
        mostrarCartasJugador();
        mostrarCartasCPU();

        if (jugadorActual.esCPU() && !jugadorActual.estaEliminado()) {
            jugarTurnoCPU(jugadorActual);
        }

    }

    private void mostrarCartaMesa() {
        Carta cartaMesa = juego.getCartasMesa().get(juego.getCartasMesa().size() - 1);
        String simbolo = obtenerSimboloCarta(cartaMesa);

        lblCartaMesa.setText(simbolo);
        lblCartaMesa.setFont(Font.font("Segoe UI Emoji", FontWeight.BOLD, 32));
        lblCartaMesa.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #cccccc; -fx-border-radius: 12; -fx-border-width: 2;");

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

    private String obtenerSimboloCarta(Carta carta) {
        String valor = carta.getSimboloCompleto().split(" ")[0];
        String palo = carta.getSimboloCompleto().split(" ")[2].toLowerCase();

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
                    agregarDorsoCartas(cpu.getMano().size(), contenedorCPU1);
                }
                case 2 -> {
                    lblCPU2.setText(cpu.getNombre());
                    agregarDorsoCartas(cpu.getMano().size(), contenedorCPU2);
                }
                case 3 -> {
                    lblCPU3.setText(cpu.getNombre());
                    agregarDorsoCartas(cpu.getMano().size(), contenedorCPU3);
                }
            }
        }
    }

    private void agregarDorsoCartas(int cantidad, javafx.scene.layout.Pane contenedor) {
        for (int j = 0; j < cantidad; j++) {
            ImageView cartaDorso = new ImageView(imgDorso);
            cartaDorso.setFitWidth(60);
            cartaDorso.setFitHeight(80);
            contenedor.getChildren().add(cartaDorso);
        }
    }

    private void jugarTurnoCPU(Jugador cpu) {
        new Thread(() -> {
            try {
                int delay = 2000 + (int) (Math.random() * 2000); // 2–4 seg
                Thread.sleep(delay);
            } catch (InterruptedException ignored) {}

            Carta cartaElegida = elegirCartaCPU(cpu);

            Platform.runLater(() -> {
                if (cartaElegida != null) {
                    juego.jugarCarta(cpu, cartaElegida);
                } else {
                    cpu.eliminar();
                    mostrarAlerta(cpu.getNombre() + " no puede jugar y queda eliminado.");
                }

                if (juego.hayGanador()) {
                    lblGanador.setText("Ganador: " + juego.getGanador().getNombre());
                    deshabilitarInterfaz();
                } else {
                    juego.siguienteTurno();
                    actualizarInterfaz();
                }
            });
        }).start();
    }


    private Carta elegirCartaCPU(Jugador cpu) {
        for (Carta carta : cpu.getMano()) {
            int nuevaSuma = juego.getSumaMesa() + carta.getValorReal(juego.getSumaMesa());
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
