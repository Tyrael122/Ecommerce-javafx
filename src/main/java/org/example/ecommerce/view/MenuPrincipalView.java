package org.example.ecommerce.view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import org.example.ecommerce.model.Cargo;
import org.example.ecommerce.model.Funcionario;

public class MenuPrincipalView {
    private final Funcionario funcionario;

    public Button btnNivelAcesso;
    public Button btnCarrinhoCompras;
    public Button btnCadastroCliente;
    public Button btnCadastroProduto;
    public Button btnCadastroFuncionario;

    public MenuPrincipalView(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void initialize() {
        if (funcionario.getCargo() == Cargo.VENDEDOR) {
            btnCadastroFuncionario.setVisible(false);
            btnNivelAcesso.setVisible(false);
            btnCadastroProduto.setVisible(false);
        }

        if (funcionario.getCargo() == Cargo.GERENTE) {
            btnNivelAcesso.setVisible(false);
        }
    }

    public void btnCadastroFuncionarioOnClick(ActionEvent actionEvent) { // FUNCIONAL
        navigateToScreen("cadastroFuncionario.fxml", actionEvent);
    }

    public void btnNivelAcessoOnClick(ActionEvent actionEvent) { // FUNCIONAL
        navigateToScreen("perfilFuncionario.fxml", actionEvent);
    }

    public void btnCarrinhoComprasOnClick(ActionEvent actionEvent) { //
        navigateToScreen("carrinho.fxml", actionEvent, new CarrinhoView(funcionario));
    }

    public void btnCadastroClienteOnClick(ActionEvent actionEvent) { // FUNCIONAL
        navigateToScreen("cadastroCliente.fxml", actionEvent);
    }

    public void btnCadastroProdutoOnClick(ActionEvent actionEvent) { // FUNCIONAL
        navigateToScreen("cadastroProduto.fxml", actionEvent);
    }

    private void navigateToScreen(String fxml, ActionEvent actionEvent) {
        navigateToScreen(fxml, actionEvent, null);
    }

    private void navigateToScreen(String fxml, ActionEvent actionEvent, Object controller) {
        ViewUtils.showNewScreen(fxml, controller);
//        ViewUtils.hideScreen(actionEvent);
    }
}
