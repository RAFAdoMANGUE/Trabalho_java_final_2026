package model;

public class Cliente {
    int id;
    String nome, telefone;

    public Cliente(String nome, String telefone){
        this.nome = nome;
        this.telefone = telefone;
    }
    public Integer getId(){
        return this.id;
    }
    public String getNome(){
        return this.nome;
    }
    public String getTelefone(){
        return this.telefone;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }
    public void setId(int id){
        this.id = id;
    }

}
