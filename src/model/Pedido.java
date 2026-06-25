package model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int idPedido;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private EstadoPedido estadoPedido;

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.estadoPedido = EstadoPedido.ABERTO;
    }

    public int getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItens() {
        return this.itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public EstadoPedido getEstadoPedido() {
        return this.estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public void adicionaItem(ItemPedido itemPedido) {
        this.itens.add(itemPedido);
    }

    public void removeItem(ItemPedido itemPedido) {
        this.itens.remove(itemPedido);
    }

    public double calculaTotal(TabelaPreco tabelaPreco) {
        double total = 0;

        for (ItemPedido itemAtual : this.itens) {
            total += itemAtual.calculaSubTotal(tabelaPreco);
        }

        return total;
    }
}