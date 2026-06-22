package service;

import model.Cliente;
import repository.RepositoryMemoria;

import java.util.List;

public class ClienteService {
    private RepositoryMemoria repositoryMemoria = new RepositoryMemoria();
    public void cadastrarCliente(String nome, String telefone){
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if(telefone == null || telefone.isBlank()){
            throw new IllegalArgumentException("Telefone não pode ser nulo");
        }
        if(telefone.length() != 11){
            throw new IllegalArgumentException("Numero inválido");
        }
        Cliente cliente = new Cliente(nome, telefone);
        repositoryMemoria.salvaCliente(cliente); // Chama função que cadastra o cliente;
    }
    public void  excluirCliente(int idCliente){
        try {
            repositoryMemoria.excluiCliente(idCliente);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void editarCliente(int idCliente, String nome, String telefone){
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if(telefone == null || telefone.isBlank()){
            throw new IllegalArgumentException("Telefone não pode ser nulo");
        }
        if(telefone.length() != 11){
            throw new IllegalArgumentException("Numero inválido");
        }
        repositoryMemoria.editarCliente(idCliente, nome, telefone);
    }
    public List<Cliente> mostrarClientes(){
        return repositoryMemoria.retornaLista();
    }
}
