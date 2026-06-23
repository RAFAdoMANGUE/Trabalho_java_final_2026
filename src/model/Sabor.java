package model;

public class Sabor {
    int idPedido;
    String nome;
    TipoPizza tipoPizza;

    public void Sabor(int idPedido, String nome, TipoPizza tipoPizza){
        this.idPedido = idPedido;
        this.nome = nome;
        this.tipoPizza = tipoPizza;
    }
    public int getIdPedido(){
        return this.idPedido;
    }
    public String getNome(){
        return this.nome;
    }
    public TipoPizza getTipoPizza(){
        return this.tipoPizza;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public void setTipoPizza(TipoPizza tipoPizza){
        this.tipoPizza = tipoPizza;
    }

}
