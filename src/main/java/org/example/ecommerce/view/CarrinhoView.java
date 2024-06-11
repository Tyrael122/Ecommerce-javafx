package org.example.ecommerce.view;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.ecommerce.controller.VendaController;
import org.example.ecommerce.dal.ProdutoDAL;
import org.example.ecommerce.model.Carrinho;
import org.example.ecommerce.model.Funcionario;
import org.example.ecommerce.model.Produto;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarrinhoView {

    public ComboBox<Produto> cmbProdutos;
    public TextField txtQuantidade;

    public Button btnAdicionar;
    public Button btnFinalizarVenda;

    public TableView tableViewCarrinho;

    private final Carrinho carrinho = new Carrinho();
    private final VendaController vendaController = new VendaController();
    private final ProdutoDAL produtoDAL = new ProdutoDAL();
    private final Funcionario funcionario;

    public CarrinhoView(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void initialize() {
        cmbProdutos.getItems().setAll(produtoDAL.buscar());

        configurarMapeamentoDeColunas();
    }

    public void btnAdicionarOnClick(ActionEvent actionEvent) {
        if (!validarCampos()) {
            return;
        }

        try {
            adicionarProduto();
        } catch (Exception e) {
            ViewUtils.showErrorMessage("Erro ao adicionar produto", e.getMessage());
        }
    }

    public void btnFinalizarVendaOnClick(ActionEvent actionEvent) {
        try {
            vendaController.finalizarVenda(carrinho, funcionario);

            showPopUpWithPixQRCode(vendaController.obterCaminhoQrCodePix(), carrinho.calcularTotal());

            carrinho.limpar();
            tableViewCarrinho.getItems().clear();
            cmbProdutos.getItems().clear();

        } catch (Exception e) {
            ViewUtils.showErrorMessage("Erro ao finalizar venda", e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (cmbProdutos.getValue() == null) {
            ViewUtils.showErrorMessage("Erro ao adicionar produto", "Selecione um produto.");
            return false;
        }

        if (txtQuantidade.getText().isEmpty()) {
            ViewUtils.showErrorMessage("Erro ao adicionar produto", "Informe a quantidade.");
            return false;
        }

        try {
            Integer.parseInt(txtQuantidade.getText());
        } catch (NumberFormatException e) {
            ViewUtils.showErrorMessage("Erro ao adicionar produto", "Quantidade inválida.");
            return false;
        }

        return true;
    }

    private void adicionarProduto() {
        Produto produto = cmbProdutos.getValue();
        int quantidade = Integer.parseInt(txtQuantidade.getText());

        carrinho.adicionarProduto(produto, quantidade);

        atualizarTableView();
    }

    private void showPopUpWithPixQRCode(Path path, double price) {
        Image image = new Image(path.toUri().toString());
        ImageView imageView = new ImageView(image);

        Label title = new Label("QRCode PIX");
        title.setFont(new Font(20));

        Label priceTag = new Label("O valor da compra foi de: R$ " + price);
        priceTag.setFont(new Font(15));

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);
        layout.getChildren().addAll(title, imageView, priceTag);

        VBox.setMargin(title, new Insets(10, 0, 0, 0));
        VBox.setMargin(imageView, new Insets(0, 10, 0, 10));
        VBox.setMargin(priceTag, new Insets(0, 0, 10, 0));

        Scene scene = new Scene(layout);

        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setScene(scene);

        popupWindow.showAndWait();
    }

    private void configurarMapeamentoDeColunas() {
        var nomeColumn = new TableColumn<Map, Integer>("NOME");
        nomeColumn.setCellValueFactory(new MapValueFactory<>("nome"));

        var precoColumn = new TableColumn<Map, Integer>("PREÇO");
        precoColumn.setCellValueFactory(new MapValueFactory<>("preco"));

        var quantidadeColumn = new TableColumn<Map, Integer>("QUANTIDADE");
        quantidadeColumn.setCellValueFactory(new MapValueFactory<>("quantidade"));

        var columns = List.of(nomeColumn, precoColumn, quantidadeColumn);
        tableViewCarrinho.getColumns().setAll(columns);
    }

    private void atualizarTableView() {
        List<Map<String, Object>> produtos = transformarProdutosEmMap(carrinho.calcularQuantidadePorProduto());
        tableViewCarrinho.getItems().setAll(produtos);
    }

    private List<Map<String, Object>> transformarProdutosEmMap(Map<Produto, Integer> produtosQuantidade) {
        List<Map<String, Object>> produtos = new ArrayList<>();

        for (Map.Entry<Produto, Integer> entry : produtosQuantidade.entrySet()) {
            Map<String, Object> produto = new HashMap<>();
            produto.put("nome", entry.getKey().getNome());
            produto.put("preco", entry.getKey().getPreco());
            produto.put("quantidade", entry.getValue());

            produtos.add(produto);
        }

        return produtos;
    }
}
