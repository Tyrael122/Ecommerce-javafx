package org.example.ecommerce;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.ecommerce.view.ViewUtils;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        ViewUtils.showNewScreen( "login.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}