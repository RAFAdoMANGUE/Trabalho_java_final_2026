package model;

public class Cliente {
    int id;
    String nome, sobrenome, telefone;

    public Cliente(String nome, String sobrenome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
        this.sobrenome = sobrenome;
    }

    public Integer getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setId(int id) {
        this.id = id;
    }

}
