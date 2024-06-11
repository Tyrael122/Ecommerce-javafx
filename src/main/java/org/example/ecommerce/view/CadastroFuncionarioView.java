package org.example.ecommerce.view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.ecommerce.dal.FuncionarioDAL;
import org.example.ecommerce.model.Cargo;
import org.example.ecommerce.model.Funcionario;

public class CadastroFuncionarioView {

    public TextField txtNome;
    public TextField txtCpf;
    public TextField txtEmail;
    public TextField txtSenha;
    public TextField txtEndereco;
    public TextField txtConfirmarSenha;
    public Button btnCadastrar;

    public final FuncionarioDAL funcionarioDAL = new FuncionarioDAL();
    public ComboBox<Cargo> cmbCargo;

    public void initialize() {
        cmbCargo.getItems().setAll(Cargo.values());
    }

    public void btnCadastrarOnClick(ActionEvent actionEvent) {
        if (!validarCampos()) {
            return;
        }

        Funcionario funcionario = criarFuncionarioComCamposDaTela();

        try {
            funcionarioDAL.inserir(funcionario);

            ViewUtils.showInfoMessage("Funcionário cadastrado com sucesso", "O funcionário foi cadastrado com sucesso");

        } catch (Exception e) {
            ViewUtils.showErrorMessage("Erro ao cadastrar funcionário", e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (txtNome.getText().isEmpty() || txtCpf.getText().isEmpty() || cmbCargo.getValue() == null || txtEmail.getText().isEmpty() || txtSenha.getText().isEmpty() || txtEndereco.getText().isEmpty() || txtConfirmarSenha.getText().isEmpty()) {
            ViewUtils.showErrorMessage("Erro ao cadastrar funcionário", "Preencha todos os campos");
            return false;
        }

        if (!txtSenha.getText().equals(txtConfirmarSenha.getText())) {
            ViewUtils.showErrorMessage("Erro ao cadastrar funcionário", "As senhas não conferem");
            return false;
        }

        return true;
    }

    private Funcionario criarFuncionarioComCamposDaTela() {
        Funcionario funcionario = new Funcionario();

        funcionario.setNome(txtNome.getText());
        funcionario.setCpf(txtCpf.getText());

        funcionario.setCargo(cmbCargo.getValue());

        funcionario.setEmail(txtEmail.getText());
        funcionario.setEndereco(txtEndereco.getText());
        funcionario.setSenha(txtSenha.getText());

        return funcionario;
    }

    public void cmbCargoOnChanged(ActionEvent actionEvent) {

    }
}
