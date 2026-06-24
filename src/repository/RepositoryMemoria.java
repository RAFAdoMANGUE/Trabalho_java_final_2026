package repository;

import exception.CampoInvalidoException;
import exception.ClienteNaoEncontradoException;
import exception.SaborInvalidoException;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class RepositoryMemoria {

    //=== ATRIBUTOS ============================================================================

    //---CLIENTES-----------------------------------------
    private List<Cliente> clientes = new ArrayList<>();
    int contadorCliente = 1;

    //---SABORES------------------------------------------
    private List<Sabor> sabores = new ArrayList<>();
    int contadorSabor = 1;

    //---PRECOS--------------------------------------------
    private TabelaPreco tabelaPreco = new TabelaPreco();

    //---PEDIDO---------------------------------------------
    private List<Pedido> pedidos = new ArrayList<>();
    int contadorPedido = 1;
    //==========================================================================================


    //=== GETs =================================================================================

    // --- não sei se vai precisar colocar coisa aqui


    //==========================================================================================


    //==== CLIENTE ==============================================================================

    // Salvar cliente ---------------------------------------------------------------------------

    public void salvaCliente(Cliente cliente) {
        cliente.setId(contadorCliente);
        this.clientes.add(cliente);
        contadorCliente++;
    }


    // Excluir cliente ---------------------------------------------------------------------------
    public void excluiCliente(int idCliente) {
        boolean removeu = clientes.removeIf(c -> c.getId() == idCliente);

        if (!removeu) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }
    }


    // Editar cliente ---------------------------------------------------------------------------
    public void editarCliente(int idCliente, String nome, String sobrenome, String telefone) {
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(idCliente)) {
                if (!nome.isBlank()) {
                    cliente.setNome(nome);
                }
                if (!sobrenome.isBlank()) {
                    cliente.setSobrenome(sobrenome);
                }
                if (!telefone.isBlank()) {
                    cliente.setTelefone(telefone);
                }
                return;
            }
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado");
    }


    // retorna clientes  -------------------------------------------------------------------------
    public List<Cliente> retornaListaCliente() {
        return clientes;
    }

    // ==============================================================================================


    //==== SABORES DA PIZZA ==========================================================================

    // Salvar sabores ---------------------------------------------------------------------------
    public void salvaSabor(Sabor sabor) {
        sabor.setIdSabor(contadorSabor);
        this.sabores.add(sabor);
        contadorSabor++;
    }


    // Excluir sabor ---------------------------------------------------------------------------
    public void excluiSabor(int idSabor) {
        boolean removeu = sabores.removeIf(s -> s.getIdSabor() == idSabor);

        if (!removeu) {
            throw new SaborInvalidoException("Sabor não encontrado");
        }
    }


    // Editar sabor ---------------------------------------------------------------------------
    public void editarSabor(int idSabor, String nome, TipoPizza tipo) {
        for (Sabor sabor : sabores) {
            if (sabor.getIdSabor().equals(idSabor)) {
                if (!nome.isBlank()) {
                    sabor.setNome(nome);
                }
                sabor.setTipoPizza(tipo);
                return;
            }
        }
        throw new SaborInvalidoException("Sabor não encontrado");
    }


    // retorna sabor  -------------------------------------------------------------------------
    public List<Sabor> retornaListaSabor() {
        return sabores;
    }

    // ==============================================================================================


    // === TABELA PRECOS =========================================================================================

    // retorna tebela preco  -------------------------------------------------------------------------
    public TabelaPreco retornaTabelaPrecos() {
        return this.tabelaPreco;
    }

    // retorna preco por tipo  -------------------------------------------------------------------------
    public double retornaPrecoPorTipo(TipoPizza tipo) {
        return this.tabelaPreco.getPrecoPorTipo(tipo);
    }

    // editar preco  -------------------------------------------------------------------------
    public void editarPreco(TipoPizza tipo, double valor) {
        this.tabelaPreco.alterarPreco(tipo, valor);
    }

    // ==============================================================================================




    // === PEDIDOS =====================================================================================

    // Salvar pedido ---------------------------------------------------------------------------
    public void salvaPedido(Pedido pedido) {
        pedido.setIdPedido(contadorPedido);
        this.pedidos.add(pedido);
        contadorPedido++;
    }


    // Excluir pedido ---------------------------------------------------------------------------
    public void excluiPedido(int idPedido) {
        boolean removeu = pedidos.removeIf(pedido -> pedido.getIdPedido() == idPedido);

        if (!removeu) {
            throw new CampoInvalidoException("Pedido não encontrado");
        }
    }


    // Excluir pedidos de um cliente ---------------------------------------------------------------------------
    public void excluiPedidosCliente(int idCliente) {
        pedidos.removeIf(pedido -> pedido.getCliente().getId() == idCliente);
    }


    // Buscar pedido por ID ---------------------------------------------------------------------------
    public Pedido buscaPedidoPorId(int idPedido) {
        for (Pedido pedido : pedidos) {
            if (pedido.getIdPedido() == idPedido) {
                return pedido;
            }
        }

        throw new CampoInvalidoException("Pedido não encontrado");
    }


    // Buscar pedido aberto por cliente ---------------------------------------------------------------------------
    public Pedido buscaPedidoAbertoPorCliente(Cliente cliente) {
        for (Pedido pedido : pedidos) {
            if (pedido.getCliente().getId().equals(cliente.getId()) && pedido.getEstadoPedido() == EstadoPedido.ABERTO) {
                return pedido;
            }
        }

        return null;
    }


    // Retorna pedidos ---------------------------------------------------------------------------
    public List<Pedido> retornaListaPedido() {
        return pedidos;
    }

    // ==============================================================================================



}


















