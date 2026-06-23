package service;

import exception.ClienteNaoEncontradoException;
import exception.CampoInvalidoException;
import model.Cliente;
import repository.RepositoryMemoria;

import java.util.ArrayList;
import java.util.List;

public class ClienteService {

    private RepositoryMemoria repositoryMemoria;

    public ClienteService(RepositoryMemoria repositoryMemoria) {
        this.repositoryMemoria = repositoryMemoria;
    }

    public void cadastrarCliente(String nome, String sobrenome, String telefone) {
        validaCampos(nome, sobrenome, telefone);
        validarTelefoneDuplicado(telefone);
        Cliente cliente = new Cliente(nome, sobrenome, telefone);
        repositoryMemoria.salvaCliente(cliente); // Chama função que cadastra o cliente;
    }


    public void excluirCliente(int idCliente) {
        repositoryMemoria.excluiCliente(idCliente);
    }


    public void editarCliente(int idCliente, String nome, String sobrenome, String telefone) {
        validaCampos(nome, sobrenome, telefone);
        validarTelefoneDuplicadoEditar(idCliente, telefone);
        repositoryMemoria.editarCliente(idCliente, nome, sobrenome, telefone);
    }

    public void validaCampos(String nome, String sobrenome, String telefone) {
        if (nome == null || nome.isBlank()) {
            throw new CampoInvalidoException("Nome não pode ser vazio");
        }

        if (sobrenome == null || sobrenome.isBlank()) {
            throw new CampoInvalidoException("Sobrenome não pode ser vazio");
        }

        if (telefone == null || telefone.isBlank()) {
            throw new CampoInvalidoException("Telefone não pode ser nulo");
        }

        if (telefone.length() != 11) {
            throw new CampoInvalidoException("Numero inválido");
        }
    }

    public void validarTelefoneDuplicadoEditar(int id, String telefone) {
        for (Cliente cliente : repositoryMemoria.retornaListaCliente()) {
            if (cliente.getTelefone().equals(telefone)) {
                if (cliente.getId().equals(id)) {
                    return;
                }
                throw new CampoInvalidoException("Já existe um cliente com esse telefone");
            }
        }
    }

    public void validarTelefoneDuplicado(String telefone) {
        for (Cliente cliente : repositoryMemoria.retornaListaCliente()) {
            if (cliente.getTelefone().equals(telefone)) {
                throw new CampoInvalidoException("Já existe um cliente com esse telefone");
            }
        }
    }

    public List<Cliente> mostrarClientes() {
        return repositoryMemoria.retornaListaCliente();
    }

    public Cliente buscarPorId(int idCliente) {
        return repositoryMemoria.buscarPorIdCliente(idCliente);
    }

    public List<Cliente> filtrarClientes(String sobrenome, String telefone) {
        sobrenome = sobrenome.trim().toLowerCase();
        telefone = telefone.trim();

        List<Cliente> resultado = new ArrayList<>();

        if ((sobrenome == null || sobrenome.isBlank()) && (telefone == null || telefone.isBlank())) {
            throw new CampoInvalidoException("Sobrenome e telefone não podem ser vazios");
        }

        for (Cliente cliente : repositoryMemoria.retornaListaCliente()) {
            boolean combinaSobrenome = sobrenome.isBlank()
                    || cliente.getSobrenome().toLowerCase().contains(sobrenome);

            boolean combinaTelefone = telefone.isBlank()
                    || cliente.getTelefone().contains(telefone);

            if (combinaSobrenome && combinaTelefone) {
                resultado.add(cliente);
            }
        }

        if (resultado.isEmpty()) {
            throw new ClienteNaoEncontradoException("Nenhum cliente encontrado");
        }

        return resultado;
    }

    public List<Cliente> buscarPorTelefone(String telefone){
        telefone = telefone.trim();

        List<Cliente> resultado = new ArrayList<>();

        if (telefone == null || telefone.isBlank()) {
            throw new CampoInvalidoException("Telefone não podem ser vazios");
        }

        for (Cliente cliente : repositoryMemoria.retornaListaCliente()) {
            boolean combinaTelefone = telefone.isBlank()
                    || cliente.getTelefone().contains(telefone);

            if (combinaTelefone) {
                resultado.add(cliente);
            }
        }

        if (resultado.isEmpty()) {
            throw new ClienteNaoEncontradoException("Nenhum cliente encontrado");
        }

        return resultado;
    }

}

