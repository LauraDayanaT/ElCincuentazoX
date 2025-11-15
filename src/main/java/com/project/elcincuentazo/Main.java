package com.project.elcincuentazo;
/* **/
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/project/elcincuentazo/MenuView.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Cincuentazo");
        stage.setScene(scene);
        stage.getIcons().add(new javafx.scene.image.Image(
                Objects.requireNonNull(getClass().getResource("/com/project/elcincuentazo/logo.png")).toExternalForm()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
