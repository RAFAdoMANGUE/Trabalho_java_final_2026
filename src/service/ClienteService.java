package service;

import model.Cliente;
import repository.RepositoryMemoria;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

public class ClienteService {
    private RepositoryMemoria repositoryMemoria = new RepositoryMemoria();

    public void cadastrarCliente(String nome, String sobrenome, String telefone){
        validaCampos(nome, sobrenome, telefone);
        validarTelefoneDuplicado(telefone);
        Cliente cliente = new Cliente(nome, sobrenome, telefone);
        repositoryMemoria.salvaCliente(cliente); // Chama função que cadastra o cliente;
    }


    public void excluirCliente(int idCliente){
        repositoryMemoria.excluiCliente(idCliente);
    }


    public void editarCliente(int idCliente, String nome, String sobrenome, String telefone){
        validaCampos(nome, sobrenome, telefone);
        validarTelefoneDuplicado(telefone);
        repositoryMemoria.editarCliente(idCliente, nome, sobrenome, telefone);
    }

    public void validaCampos(String nome, String sobrenome, String telefone){
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        if(sobrenome == null || sobrenome.isBlank()){
            throw new IllegalArgumentException("Sobrenome não pode ser vazio");
        }

        if(telefone == null || telefone.isBlank()){
            throw new IllegalArgumentException("Telefone não pode ser nulo");
        }

        if(telefone.length() != 11){
            throw new IllegalArgumentException("Numero inválido");
        }
    }

    public void validarTelefoneDuplicado(String telefone){
        for(Cliente cliente : repositoryMemoria.retornaLista()){
            if(cliente.getTelefone().equals(telefone)){
                throw new IllegalArgumentException("Já existe um cliente com esse telefone");
            }
        }
    }

    public List<Cliente> mostrarClientes(){
        return repositoryMemoria.retornaLista();
    }

    public Cliente buscarPorId(int idCliente){
        return repositoryMemoria.buscarPorId(idCliente);
    }

    public List<Cliente> filtrarClientes(String sobrenome, String telefone) {
        sobrenome = sobrenome.trim().toLowerCase();
        telefone = telefone.trim();

        List<Cliente> resultado = new ArrayList<>();

        if((sobrenome == null || sobrenome.isBlank()) && (telefone == null || telefone.isBlank())){
            throw new IllegalArgumentException("Sobrenome e telefone não podem ser vazios");
        }

        for (Cliente cliente : repositoryMemoria.retornaLista()) {
            boolean combinaSobrenome = sobrenome.isBlank()
                    || cliente.getSobrenome().toLowerCase().contains(sobrenome);

            boolean combinaTelefone = telefone.isBlank()
                    || cliente.getTelefone().contains(telefone);

            if (combinaSobrenome && combinaTelefone) {
                resultado.add(cliente);
            }
        }

        if(resultado.isEmpty()){
            throw new ArrayIndexOutOfBoundsException("Nenhum cliente encontrado");
        }

        return resultado;
    }

}

