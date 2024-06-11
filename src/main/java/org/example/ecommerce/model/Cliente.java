package org.example.ecommerce.model;

import lombok.Data;

@Data
public class Cliente {
    private long id;
    private String cpf;

    private String nome;

    private String email;
    private String telefone;

    private String endereco;
}
