package model;

public class Sabor {
    int idSabor;
    String nome;
    TipoPizza tipoPizza;

    public Sabor(String nome, TipoPizza tipoPizza){
        this.nome = nome;
        this.tipoPizza = tipoPizza;
    }
    public Integer getIdSabor(){
        return this.idSabor;
    }
    public String getNome(){
        return this.nome;
    }
    public TipoPizza getTipoPizza(){
        return this.tipoPizza;
    }

    public void setIdSabor(int id) {
        this.idSabor = idSabor;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public void setTipoPizza(TipoPizza tipoPizza){
        this.tipoPizza = tipoPizza;
    }

}
