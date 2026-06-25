package service;

import exception.CampoInvalidoException;
import model.*;
import repository.RepositoryMemoria;

import java.util.List;

public class PedidoService {

    private RepositoryMemoria repositoryMemoria;
    private FormaFactory formaFactory;

    public PedidoService(RepositoryMemoria repositoryMemoria) {
        this.repositoryMemoria = repositoryMemoria;
        this.formaFactory = new FormaFactory();
    }


    public Pedido buscarOuCriarPedidoAberto(Cliente cliente) {
        validarCliente(cliente);
        Pedido pedido = repositoryMemoria.buscaPedidoAbertoPorCliente(cliente);

        if (pedido == null) {
            pedido = new Pedido(cliente);
            pedido.atualizarPrecoTotal(repositoryMemoria.retornaTabelaPrecos());
            repositoryMemoria.salvaPedido(pedido);
        }

        return pedido;
    }

    public void adicionarPizzaPorDimensao(Pedido pedido, String tipoForma, double dimensao, Sabor sabor1, Sabor sabor2, int quantidade) {
        validarPedidoAberto(pedido);

        ItemPedido item = criarItemPedido(tipoForma, dimensao, false, sabor1, sabor2, quantidade);

        pedido.adicionaItem(item);
        pedido.atualizarPrecoTotal(repositoryMemoria.retornaTabelaPrecos());
    }

    public void adicionarPizzaPorArea(Pedido pedido, String tipoForma, double area, Sabor sabor1, Sabor sabor2, int quantidade) {
        validarPedidoAberto(pedido);

        ItemPedido item = criarItemPedido(tipoForma, area, true, sabor1, sabor2, quantidade);

        pedido.adicionaItem(item);
        pedido.atualizarPrecoTotal(repositoryMemoria.retornaTabelaPrecos());
    }

    private ItemPedido criarItemPedido(String tipoForma, double valor, boolean porArea, Sabor sabor1, Sabor sabor2, int quantidade) {
        validarTipoForma(tipoForma);
        validarSabores(sabor1, sabor2);
        validarQuantidade(quantidade);

        Forma forma;

        if (porArea) {
            forma = criarFormaPorArea(tipoForma, valor);
        } else {
            forma = criarFormaPorDimensao(tipoForma, valor);
        }

        Pizza pizza = new Pizza(forma);
        pizza.adicionaSabor(sabor1);

        if (sabor2 != null) {
            pizza.adicionaSabor(sabor2);
        }

        return new ItemPedido(pizza, quantidade);
    }

    public void removerItem(Pedido pedido, int indiceItem){
        validarPedidoAberto(pedido);

        if(indiceItem < 0 || indiceItem >= pedido.getItens().size()){
            throw new CampoInvalidoException("Item do pedido invalido");
        }

        ItemPedido itemPedido = pedido.getItens().get(indiceItem);
        pedido.removeItem(itemPedido);
        pedido.atualizarPrecoTotal(repositoryMemoria.retornaTabelaPrecos());
    }

    public void atualizarItem(Pedido pedido, int indiceItem, String tipoForma, double valor, boolean porArea,
                              Sabor sabor1, Sabor sabor2, int quantidade) {
        validarPedidoAberto(pedido);

        if (indiceItem < 0 || indiceItem >= pedido.getItens().size()) {
            throw new CampoInvalidoException("Item do pedido inválido");
        }

        ItemPedido novoItem = criarItemPedido(tipoForma, valor, porArea, sabor1, sabor2, quantidade);

        pedido.getItens().set(indiceItem, novoItem);
        pedido.atualizarPrecoTotal(repositoryMemoria.retornaTabelaPrecos());
    }

    public String calcularResultadoMedida(String tipoForma, double valor, boolean porArea) {
        validarTipoForma(tipoForma);

        Forma forma;

        if (porArea) {
            forma = criarFormaPorArea(tipoForma, valor);

            if (forma instanceof Quadrado) {
                Quadrado quadrado = (Quadrado) forma;
                return "Resultado: lado calculado: " + String.format("%.2f", quadrado.getLado()) + " cm";
            }

            if (forma instanceof Circulo) {
                Circulo circulo = (Circulo) forma;
                return "Resultado: raio calculado: " + String.format("%.2f", circulo.getRaio()) + " cm";
            }

            if (forma instanceof Triangulo) {
                Triangulo triangulo = (Triangulo) forma;
                return "Resultado: lado calculado: " + String.format("%.2f", triangulo.getLado()) + " cm";
            }
        }

        forma = criarFormaPorDimensao(tipoForma, valor);

        return "Resultado: área calculada: " + String.format("%.2f", forma.calculaArea()) + " cm²";
    }

    public TabelaPreco getTabelaPreco() {
        return repositoryMemoria.retornaTabelaPrecos();
    }

    public double calcularTotalPedido(Pedido pedido){
        validarPedido(pedido);

        return pedido.getPrecoTotal();
    }

    public void alterarEstadoPedido(Pedido pedido, EstadoPedido estadoPedido){
        validarPedido(pedido);
        if(estadoPedido == null){
            throw new CampoInvalidoException("Estado do pedido é obrigatorio");
        }

        if(estadoPedido != EstadoPedido.ABERTO && pedido.getItens().isEmpty()){
            throw new CampoInvalidoException("Não é possível alterar o estado de um pedido vazio");
        }

        pedido.setEstadoPedido(estadoPedido);
    }

    public List<Pedido> listarPedidos(){
        return repositoryMemoria.retornaListaPedido();
    }

    public Pedido buscaPedidoPorId(int idPedido){
        if(idPedido <= 0){
            throw new CampoInvalidoException("Id do pedido inválido");
        }

        return repositoryMemoria.buscaPedidoPorId(idPedido);
    }

    public List<Pedido> listarPedidosPorCliente(Cliente cliente) {
        validarCliente(cliente);

        return repositoryMemoria.retornaPedidosPorCliente(cliente.getId());
    }

    public void excluirPedido(int idPedido){
        if(idPedido <= 0){
            throw new CampoInvalidoException("Id do pedido inválido");
        }

        repositoryMemoria.excluiPedido(idPedido);
    }

    public void excluirPedidosCliente(int idCliente){
        if(idCliente <= 0){
            throw new CampoInvalidoException("Id do pedido inválido");
        }

        repositoryMemoria.excluiPedidosCliente(idCliente);
    }

    public Pedido criarNovoPedido(Cliente cliente) {
        validarCliente(cliente);

        Pedido pedidoAberto = repositoryMemoria.buscaPedidoAbertoPorCliente(cliente);

        if (pedidoAberto != null) {
            throw new CampoInvalidoException("Este cliente já possui um pedido em aberto. Atualize o pedido existente.");
        }

        Pedido pedido = new Pedido(cliente);
        pedido.atualizarPrecoTotal(repositoryMemoria.retornaTabelaPrecos());
        repositoryMemoria.salvaPedido(pedido);

        return pedido;
    }

    public Forma criarFormaPorDimensao(String tipoForma, double dimensao) {
        if (tipoForma.equalsIgnoreCase("QUADRADO")) {
            return formaFactory.criarQuadradoPorLado(dimensao);
        }

        if (tipoForma.equalsIgnoreCase("TRIANGULO")) {
            return formaFactory.criarTrianguloPorLado(dimensao);
        }

        if (tipoForma.equalsIgnoreCase("CIRCULO")) {
            return formaFactory.criarCirculoPorRaio(dimensao);
        }

        throw new CampoInvalidoException("Forma inválida");
    }

    public Forma criarFormaPorArea(String tipoForma, double dimensao) {
        if (tipoForma.equalsIgnoreCase("QUADRADO")) {
            return formaFactory.criarQuadradoPorArea(dimensao);
        }

        if (tipoForma.equalsIgnoreCase("TRIANGULO")) {
            return formaFactory.criarTrianguloPorArea(dimensao);
        }

        if (tipoForma.equalsIgnoreCase("CIRCULO")) {
            return formaFactory.criarCirculoPorArea(dimensao);
        }

        throw new CampoInvalidoException("Forma inválida");
    }


    // validacoes --------------------------------------------------------------------

    public void validarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new CampoInvalidoException("O cliente é obrigatorio");
        }
    }

    public void validarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new CampoInvalidoException("O pedido é obrigatorio");
        }
    }

    public void validarPedidoAberto(Pedido pedido) {
        validarPedido(pedido);

        if (pedido.getEstadoPedido() != EstadoPedido.ABERTO) {
            throw new CampoInvalidoException("Só é possível alterar pedidos em aberto");
        }
    }

    public void validarTipoForma(String tipoForma) {
        if (tipoForma == null || tipoForma.isBlank()) {
            throw new CampoInvalidoException("Forma da pizza é obrigatoria");
        }

        if (!tipoForma.equalsIgnoreCase("QUADRADO") && !tipoForma.equalsIgnoreCase("CIRCULO")
                && !tipoForma.equalsIgnoreCase("TRIANGULO")
        ) {
            throw new CampoInvalidoException("Forma inválida");
        }

    }

    public void validarQuantidade(int quantidade) {

        if (quantidade <= 0) {
            throw new CampoInvalidoException("Quantidade deve ser maior que zero");
        }

    }

    public void validarSabores(Sabor sabor1, Sabor sabor2) {
        if (sabor1 == null) {
            throw new CampoInvalidoException("O primeiro sabor é obrigatorio");
        }

        if (sabor2 != null && sabor1.getIdSabor().equals(sabor2.getIdSabor())) {
            throw new CampoInvalidoException("Os dois sabores não podem ser iguais");
        }
    }


}
