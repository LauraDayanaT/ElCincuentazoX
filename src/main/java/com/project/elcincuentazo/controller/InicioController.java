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

import java.io.IOException;

public class InicioController {

    @FXML private ChoiceBox<Integer> choiceNumCPU;

    @FXML private Label lblMensaje;

    @FXML private Button btnIniciar;

    @FXML
    public void initialize() {
        choiceNumCPU.getItems().addAll(1, 2, 3);
        choiceNumCPU.setValue(1);
        lblMensaje.setText("");
    }

    @FXML
    protected void onIniciarJuego(ActionEvent event) {
        int numCPU = choiceNumCPU.getValue();

        try {
            // Crear juego con la cantidad seleccionada de CPUs
            Juego juego = new Juego(numCPU);

            // Cargar la vista del juego
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/project/elcincuentazo/view/JuegoView.fxml"));
            Parent root = loader.load();


            // Obtener el controlador y pasarle la instancia del juego
            JuegoController controlador = loader.getController();
            controlador.iniciarJuego(juego);

            // Cambiar de escena
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("El Cincuentazo");

            stage.show();

        } catch (MazoVacioException e) {
            lblMensaje.setText("⚠️ El mazo está vacío. No se pudo iniciar el juego.");
            e.printStackTrace();
        } catch (IOException e) {
            lblMensaje.setText("❌ Error al cargar la vista del juego.");
            e.printStackTrace();
        } catch (Exception e) {
            lblMensaje.setText("❌ Error inesperado al iniciar el juego.");
            e.printStackTrace();
        }
    }
}
