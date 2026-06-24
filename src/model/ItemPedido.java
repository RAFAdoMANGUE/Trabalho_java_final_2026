package model;

public class ItemPedido {
    private Pizza pizza;
    private int quantidade;
  
    public ItemPedido(Pizza pizza, int quantidade){
        this.pizza = pizza;
        this.quantidade = quantidade;
    }

    public Pizza getPizza() {
        return this.pizza;
    }
    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
    public int getQuantidade(){
        return this.quantidade;
    }
    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }

    public double calculaSubTotal(TabelaPreco tabelaPreco){
        return this.pizza.calculaPreco(tabelaPreco)*quantidade;
    }
}
