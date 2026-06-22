package service;

import model.Cliente;
import repository.RepositoryMemoria;

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
            repositoryMemoria.salvaCliente(cliente);

    }
}
