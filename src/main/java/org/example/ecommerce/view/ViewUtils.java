package org.example.ecommerce.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.ecommerce.App;

import java.io.IOException;

public class ViewUtils {

    public static void showInfoMessage(String headerText, String message) {
        showMessage("Informação", headerText, message, Alert.AlertType.INFORMATION);
    }

    public static void showErrorMessage(String headerText, String message) {
        showMessage("Erro", headerText, message, Alert.AlertType.ERROR);
    }

    public static void showMessage(String title, String headerText, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showNewScreen(String fxml) {
        showNewScreen(fxml, null);
    }

    public static void showNewScreen(String fxml, Object controller) {
        try {
            tryShowNewScreen(fxml, controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void tryShowNewScreen(String fxml, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));

        if (controller != null) {
            fxmlLoader.setController(controller);
        }

        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();
    }

    public static void hideScreen(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }
}
