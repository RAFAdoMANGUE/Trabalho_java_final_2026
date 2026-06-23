package repository;

import model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class RepositoryMemoria {

    // Salvar cliente ---------------------------------------------------------------------------
    private List<Cliente> clientes = new ArrayList<>();
    int contadorCliente = 1;

    public void salvaCliente(Cliente cliente){
        cliente.setId(contadorCliente);
        this.clientes.add(cliente);
        contadorCliente++;
    }

    //--------------------------------------------------------------------------------------------
    // Excluir cliente ---------------------------------------------------------------------------
    public void excluiCliente(int idCliente){
        boolean removeu = clientes.removeIf(c -> c.getId() == idCliente);

        if (!removeu) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
    }
    //--------------------------------------------------------------------------------------------

    public void editarCliente(int idCliente, String nome, String sobrenome, String telefone){
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(idCliente)) {
                cliente.setNome(nome);
                cliente.setSobrenome(sobrenome);
                cliente.setTelefone(telefone);
                return;
            }
        }
        throw new IllegalArgumentException("Cliente não encontrado");
    }
    //--------------------------------------------------------------------------------------------

    public List<Cliente> retornaLista() {
        return clientes;
    }

    public Cliente buscarPorId(int idCliente){

        for(Cliente cliente : clientes){

            if(cliente.getId() == idCliente){
                return cliente;
            }
        }

        throw new IllegalArgumentException("Cliente não encontrado");
    }

}