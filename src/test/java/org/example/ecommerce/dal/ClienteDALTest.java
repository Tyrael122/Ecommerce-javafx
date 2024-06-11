package org.example.ecommerce.dal;

import org.example.ecommerce.TestUtils;
import org.example.ecommerce.database.Database;
import org.example.ecommerce.model.Cliente;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteDALTest {

    private final String cpfPadrao = "12345678901";

    @BeforeEach
    void setUp() {
        try {
            Database.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        try {
            Database.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void inserir() {
        Cliente cliente = criarClientePadrao();

        ClienteDAL clienteDAL = new ClienteDAL();
        cliente = clienteDAL.inserir(cliente);

        assertNotNull(cliente);
    }

    @Test
    void atualizar() {
        Cliente cliente = criarClientePadrao();

        ClienteDAL clienteDAL = new ClienteDAL();
        cliente = clienteDAL.inserir(cliente);

        assertNotNull(cliente);

        cliente = clienteDAL.buscarPorCpf(cpfPadrao);
        cliente.setNome("Ciclano");

        boolean resultado = clienteDAL.atualizar(cliente);
        assertTrue(resultado);
    }

    @Test
    void deletar() {
        Cliente cliente = criarClientePadrao();

        ClienteDAL clienteDAL = new ClienteDAL();
        cliente = clienteDAL.inserir(cliente);

        assertNotNull(cliente);

        cliente = clienteDAL.buscarPorCpf(cpfPadrao);
        boolean resultado = clienteDAL.deletar(cliente.getId());

        assertTrue(resultado);

        cliente = clienteDAL.buscarPorCpf(cpfPadrao);
        assertNull(cliente);
    }

    @Test
    void buscar() {
        Cliente cliente = criarClientePadrao();

        ClienteDAL clienteDAL = new ClienteDAL();
        cliente = clienteDAL.inserir(cliente);

        assertNotNull(cliente);

        cliente = clienteDAL.buscarPorCpf(cpfPadrao);
        assertNotNull(cliente);
    }

    @Test
    void buscarColecao() {
        Cliente cliente = criarClientePadrao();

        ClienteDAL clienteDAL = new ClienteDAL();
        cliente = clienteDAL.inserir(cliente);

        assertNotNull(cliente);

        cliente = criarClientePadrao();
        cliente.setCpf("98765432109");
        cliente.setNome("Maria");

        cliente = clienteDAL.inserir(cliente);

        assertNotNull(cliente);

        List<Cliente> clientes = clienteDAL.buscar("nome LIKE '%a%'");
        assertEquals(2, clientes.size());
    }

    private Cliente criarClientePadrao() {
        return TestUtils.criarClientePadrao(cpfPadrao);
    }
}