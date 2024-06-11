package org.example.ecommerce.view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.ecommerce.controller.LoginController;
import org.example.ecommerce.model.Funcionario;

public class LoginView {
    public TextField txtUsuario;
    public PasswordField txtSenha;
    public Button btnLogin;

    private final LoginController loginController = new LoginController();

    public void btnLoginOnClick(ActionEvent actionEvent) {
        try {
//            Funcionario funcionario = loginController.login(txtUsuario.getText(), txtSenha.getText());
            Funcionario funcionario = loginController.login("123", "admin");
            MenuPrincipalView menuPrincipalView = new MenuPrincipalView(funcionario);

            ViewUtils.showNewScreen("menuPrincipal.fxml", menuPrincipalView);
            ViewUtils.hideScreen(actionEvent);

        } catch (IllegalArgumentException e) {
            ViewUtils.showErrorMessage("Erro ao fazer login", e.getMessage());
        }
    }}
