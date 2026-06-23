package model;

public class Sabor {
    int id;
    String nome;
    TipoPizza tipoPizza;

    public Sabor(String nome, TipoPizza tipoPizza){
        this.nome = nome;
        this.tipoPizza = tipoPizza;
    }
    public int getIdPedido(){
        return this.id;
    }
    public String getNome(){
        return this.nome;
    }
    public TipoPizza getTipoPizza(){
        return this.tipoPizza;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public void setTipoPizza(TipoPizza tipoPizza){
        this.tipoPizza = tipoPizza;
    }

}
