package model;

public class Cliente {
    int id;
    String nome, telefone, endereco;

    public Cliente(int id, String nome, String telefone){
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }
    public Cliente(){
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

}
