package org.example.ecommerce.view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.ecommerce.dal.ProdutoDAL;
import org.example.ecommerce.model.Produto;

public class CadastroProdutoView {
    public TextField txtNome;
    public TextField txtDescricao;
    public TextField txtPreco;
    public TextField txtQuantidade;
    public Button btnCadastrar;

    private final ProdutoDAL produtoDAL = new ProdutoDAL();

    public void btnCadastrarOnClick(ActionEvent actionEvent) {
        if (!validarCampos()) {
            return;
        }

        Produto produto = criarProdutosComCamposDaTela();

        try {
            produtoDAL.inserir(produto);

            ViewUtils.showInfoMessage("Produto cadastrado com sucesso", "O produto foi cadastrado com sucesso");

        } catch (Exception e) {
            ViewUtils.showErrorMessage("Erro ao cadastrar produto", e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (txtNome.getText().isEmpty() || txtDescricao.getText().isEmpty() || txtPreco.getText().isEmpty() || txtQuantidade.getText().isEmpty()) {
            ViewUtils.showErrorMessage("Erro ao cadastrar produto", "Preencha todos os campos");
            return false;
        }

        return true;
    }

    private Produto criarProdutosComCamposDaTela() {
        Produto produto = new Produto();
        produto.setNome(txtNome.getText());
        produto.setDescricao(txtDescricao.getText());
        produto.setPreco(Double.parseDouble(txtPreco.getText()));
        produto.setQuantidadeEstoque(Integer.parseInt(txtQuantidade.getText()));

        return produto;
    }
}
