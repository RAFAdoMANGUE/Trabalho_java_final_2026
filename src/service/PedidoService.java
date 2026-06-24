package service;

import exception.CampoInvalidoException;
import model.*;
import repository.RepositoryMemoria;

import java.text.Normalizer;
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
            repositoryMemoria.salvaPedido(pedido);
        }

        return pedido;
    }


    public void adicionarPizzaPorDimensao(Pedido pedido, String tipoForma, double dimensao, Sabor sabor1, Sabor sabor2, int quantidade) {
        validarPedido(pedido);
        validarTipoForma(tipoForma);
        validarSabores(sabor1, sabor2);
        validarQuantidade(quantidade);

        Forma forma = criarFormaPorDimensao(tipoForma, dimensao);

        Pizza pizza = new Pizza(forma);
        pizza.adicionaSabor(sabor1);
        if(sabor2 != null) {
            pizza.adicionaSabor(sabor2);
        }

        ItemPedido item = new ItemPedido(pizza, quantidade);
        pedido.adicionaItem(item);
    }

    public void removerItem(Pedido pedido, int indiceItem){
        validarPedidoAberto(pedido);

        if(indiceItem < 0 || indiceItem > pedido.getItens().size()){
            throw new CampoInvalidoException("Item do pedido invalido");
        }

        ItemPedido itemPedido = pedido.getItens().get(indiceItem);
        pedido.getItens().remove(itemPedido);
    }

    public double calculatotalPedido(Pedido pedido){
        validarPedido(pedido);

        return pedido.calculaTotal(repositoryMemoria.retornaTabelaPrecos());
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
            throw new CampoInvalidoException("Os dois sabores devem ser iguais");
        }
    }


}
