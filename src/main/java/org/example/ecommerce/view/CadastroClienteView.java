package org.example.ecommerce.view;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.example.ecommerce.dal.ClienteDAL;
import org.example.ecommerce.model.Cliente;

import java.util.List;

public class CadastroClienteView {

    public TextField txtNome;
    public TextField txtEmail;
    public TextField txtCpf;
    public TextField txtEndereco;
    public TextField txtTelefone;

    public ComboBox<String> cmbOpcoesPesquisa;
    public TextField txtPesquisa;

    public Button btnCadastrar;

    public TableView<Cliente> tableViewClientes;

    // TODO: Cortar comunição direta com a DAL, e usar camada de controller.
    private final ClienteDAL clienteDAL = new ClienteDAL();

    public void initialize() {
        configurarMapeamentoDeColunas();

        // TODO: Passar isso aqui para a camada de controller.
        cmbOpcoesPesquisa.getItems().addAll("Nome", "CPF", "Email", "Endereco", "Telefone");
        cmbOpcoesPesquisa.setValue("Nome");

        try {
            tableViewClientes.getItems().setAll(clienteDAL.buscar());

        } catch (Exception e) {
            ViewUtils.showErrorMessage("Erro ao listar clientes", e.getMessage());
        }
    }

    public void txtPesquisaOnKeyReleased(KeyEvent keyEvent) {
        String opcaoPesquisa = cmbOpcoesPesquisa.getValue();
        String textoPesquisa = txtPesquisa.getText();

        String criterioPesquisa = montarCriterioPesquisa(opcaoPesquisa, textoPesquisa);

        try {
            tableViewClientes.getItems().setAll(clienteDAL.buscar(criterioPesquisa));

        } catch (Exception e) {
            ViewUtils.showErrorMessage("Erro ao pesquisar clientes", e.getMessage());
        }
    }

    public void btnCadastrarOnClick(ActionEvent actionEvent) {
        if (!validarCampos()) {
            return;
        }

        Cliente cliente = criarClienteComCamposDaTela();

        try {
            clienteDAL.inserir(cliente);

            ViewUtils.showInfoMessage("Cliente cadastrado com sucesso", "O cliente foi cadastrado com sucesso");

            tableViewClientes.getItems().setAll(clienteDAL.buscar());

        } catch (Exception e) {
            ViewUtils.showErrorMessage("Erro ao cadastrar cliente", e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || txtCpf.getText().isEmpty() || txtEndereco.getText().isEmpty() || txtTelefone.getText().isEmpty()) {
            ViewUtils.showErrorMessage("Erro ao cadastrar cliente", "Preencha todos os campos");
            return false;
        }

        return true;
    }

    private Cliente criarClienteComCamposDaTela() {
        Cliente cliente = new Cliente();

        cliente.setNome(txtNome.getText());
        cliente.setEmail(txtEmail.getText());
        cliente.setCpf(txtCpf.getText());
        cliente.setEndereco(txtEndereco.getText());
        cliente.setTelefone(txtTelefone.getText());

        return cliente;
    }

    // TODO: Passar isso aqui para a camada de controller.
    private String montarCriterioPesquisa(String opcaoPesquisa, String textoPesquisa) {
        if (textoPesquisa.isEmpty()) return "1 = 1";

        return switch (opcaoPesquisa) {
            case "Nome" -> "nome LIKE '%" + textoPesquisa + "%'";
            case "CPF" -> "cpf LIKE '%" + textoPesquisa + "%'";
            case "Email" -> "email LIKE '%" + textoPesquisa + "%'";
            case "Endereco" -> "endereco LIKE '%" + textoPesquisa + "%'";
            case "Telefone" -> "telefone LIKE '%" + textoPesquisa + "%'";
            default -> "1 = 1";
        };
    }

    private void configurarMapeamentoDeColunas() {
        var columnName = new TableColumn<Cliente, String>("NOME");
        columnName.setCellValueFactory(new PropertyValueFactory<>("nome"));

        var columnEmail = new TableColumn<Cliente, String>("EMAIL");
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        var columnCpf = new TableColumn<Cliente, String>("CPF");
        columnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        var columnEndereco = new TableColumn<Cliente, String>("ENDERECO");
        columnEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));

        var columnTelefone = new TableColumn<Cliente, String>("TELEFONE");
        columnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        var colunas = List.of(columnName, columnEmail, columnCpf, columnEndereco, columnTelefone);

        tableViewClientes.getColumns().setAll(colunas);
    }
}
