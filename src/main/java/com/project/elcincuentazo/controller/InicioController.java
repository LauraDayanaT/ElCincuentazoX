package com.project.elcincuentazo.controller;

import com.project.elcincuentazo.model.Juego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {

    @FXML
    private ChoiceBox<Integer> choiceNumCPU;

    @FXML
    private Label lblMensaje;

    @FXML
    private Button btnIniciar;

    @FXML
    public void initialize() {
        choiceNumCPU.getItems().addAll(1, 2, 3);
        choiceNumCPU.setValue(1);
    }

    @FXML
    protected void onIniciarJuego(ActionEvent event) {
        int numCPU = choiceNumCPU.getValue();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/elcincuentazo/view/JuegoView.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del juego y pasarle la partida
            JuegoController controlador = loader.getController();
            controlador.iniciarJuego(new Juego(numCPU));

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("El Cincuentazo");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            lblMensaje.setText("Error al iniciar el juego.");
        }
    }
}
