package com.project.elcincuentazo.controller;

import com.project.elcincuentazo.exceptions.MazoVacioException;
import com.project.elcincuentazo.models.Juego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Screen;

import java.io.IOException;

public class MenuController {

    @FXML
    private ChoiceBox<Integer> choiceNumCPU;

    @FXML
    private Label lblMensaje;

    @FXML
    private Button btnIniciarJuego;

    @FXML
    public void initialize() {
        choiceNumCPU.getItems().addAll(1, 2, 3);
        choiceNumCPU.setValue(1);
    }

    @FXML
    protected void onIniciarJuego(ActionEvent event) {
        int numCPU = choiceNumCPU.getValue();

        double anchoDeseado = 650;
        double altoDeseado = 750;

        Screen screen = Screen.getPrimary();
        javafx.geometry.Rectangle2D visualBounds = screen.getVisualBounds();
        double altoFinal = Math.min(altoDeseado, visualBounds.getHeight());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/elcincuentazo/JuegoView.fxml"));
            Parent root = loader.load();

            JuegoController controller = loader.getController();
            controller.iniciarJuego(new Juego(numCPU));

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, anchoDeseado, altoFinal));

            stage.setX(visualBounds.getMinX() + (visualBounds.getWidth() - anchoDeseado) / 2);
            stage.setY(visualBounds.getMinY() + (visualBounds.getHeight() - altoFinal) / 2);

            stage.setTitle("El Cincuentazo");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (MazoVacioException e) {
            throw new RuntimeException(e);
        }
    }

    // 🔹 Efecto hover (verde más brillante)
    @FXML
    private void hoverBoton() {
        btnIniciarJuego.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-color: #27ae60;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-padding: 6 18 6 18;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 6, 0.4, 0, 2);" +
                        "-fx-background-color: linear-gradient(to bottom, #58d68d, #2ecc71);"
        );
    }


    // 🔹 Al salir del hover (verde normal)
    @FXML
    private void salirHoverBoton() {
        btnIniciarJuego.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-color: #1e8449;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-padding: 6 18 6 18;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 4, 0.3, 0, 2);" +
                        "-fx-background-color: linear-gradient(to bottom, #2ecc71, #27ae60);"
        );
    }}

