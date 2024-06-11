package org.example.ecommerce.view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.ecommerce.dal.FuncionarioDAL;
import org.example.ecommerce.model.Cargo;
import org.example.ecommerce.model.Funcionario;

public class NivelAcessoFuncionarioView {

    public TextField txtCpf;
    public ComboBox<Cargo> cmbNivelAcesso;
    public Button btnSalvar;

    private final FuncionarioDAL funcionarioDAL = new FuncionarioDAL();

    public void initialize() {
        cmbNivelAcesso.getItems().setAll(Cargo.values());
    }

    public void btnSalvarOnClick(ActionEvent actionEvent) {
        if (!validarCampos()) {
            return;
        }

        String cpf = txtCpf.getText();
        Cargo cargo = cmbNivelAcesso.getValue();

        try {
            Funcionario funcionario = funcionarioDAL.buscarPorCpf(cpf);
            if (funcionario == null) {
                ViewUtils.showErrorMessage("Erro ao atualizar nível de acesso", "Funcionário não encontrado.");
                return;
            }

            funcionario.setCargo(cargo);

            funcionarioDAL.atualizar(funcionario);
            ViewUtils.showInfoMessage("Nível de acesso atualizado", "Nível de acesso atualizado com sucesso.");

        } catch (Exception e) {
            ViewUtils.showErrorMessage("Erro ao atualizar nível de acesso", e.getMessage());
        }

    }

    private boolean validarCampos() {
        if (txtCpf.getText().isBlank()) {
            ViewUtils.showErrorMessage("Erro ao atualizar nível de acesso", "CPF não pode ser vazio.");
            return false;
        }

        if (cmbNivelAcesso.getValue() == null) {
            ViewUtils.showErrorMessage("Erro ao atualizar nível de acesso", "Nível de acesso não pode ser vazio.");
            return false;
        }

        return true;
    }
}