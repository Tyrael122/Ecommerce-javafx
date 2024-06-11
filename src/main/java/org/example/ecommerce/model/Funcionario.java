package org.example.ecommerce.model;

import lombok.Data;

@Data
public class Funcionario {
    private long id;
    private String cpf;

    private String senha;
    private Cargo cargo;

    private String nome;

    private String email;

    private String endereco;
}
