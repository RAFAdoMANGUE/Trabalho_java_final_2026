package repository;

import model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class RepositoryMemoria {

    // Salvar cliente ---------------------------------------------------------------------------
    private List<Cliente> clientes = new ArrayList<>();
    int contadorCliente;

    public void salvaCliente(Cliente cliente){
        cliente.setId(contadorCliente);
        this.clientes.add(cliente);
        contadorCliente++;
    }

    //--------------------------------------------------------------------------------------------
    // Excluir cliente ---------------------------------------------------------------------------
    public void excluiCliente(int idCliente){
        this.clientes.removeIf(c -> c.getId() == idCliente);
    }
    //--------------------------------------------------------------------------------------------

    public void editarCliente(int idCliente, String nome, String sobrenome, String telefone){
        for(int i = 0; i<clientes.size();i++){
            if(clientes.get(i).getId() == idCliente){
                clientes.get(i).setNome(nome);
                clientes.get(i).setSobrenome(sobrenome);
                clientes.get(i).setTelefone(telefone);
                return;
            }
        }
        throw new IllegalArgumentException("Cliente não encontrado");
    }
    //--------------------------------------------------------------------------------------------

    public List<Cliente> retornaLista() {
        return clientes;
    }
}