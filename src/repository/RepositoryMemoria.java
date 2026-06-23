package repository;

import model.Cliente;
import model.Sabor;

import java.util.ArrayList;
import java.util.List;

public class RepositoryMemoria {

    //==== CLIENTE ==============================================================================


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


    // Editar cliente ---------------------------------------------------------------------------
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


    // retorna clientes  -------------------------------------------------------------------------
    public List<Cliente> retornaListaCliente() {
        return clientes;
    }
    //--------------------------------------------------------------------------------------------


    // Buscar cliente por Id ---------------------------------------------------------------------
    public Cliente buscarPorIdCliente(int idCliente){

        for(Cliente cliente : clientes){

            if(cliente.getId() == idCliente){
                return cliente;
            }
        }

        throw new IllegalArgumentException("Cliente não encontrado");
    }
    //--------------------------------------------------------------------------------------------


    // ==============================================================================================




    //==== SABORES DA PIZZA ==========================================================================


    // Salvar sabores ---------------------------------------------------------------------------
    private List<Sabor> sabor = new ArrayList<>();
    int contadorSabor = 1;

    public void salvaSabor(Sabor sabor){
        sabor.setId(contadorSabor);
        this.sabor.add(sabor);
        contadorSabor++;
    }
    //--------------------------------------------------------------------------------------------


    // Excluir cliente ---------------------------------------------------------------------------
    public void excluiCientel(int idCliente){
        boolean removeu = clientes.removeIf(c -> c.getId() == idCliente);

        if (!removeu) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
    }
    //--------------------------------------------------------------------------------------------


    // Editar cliente ---------------------------------------------------------------------------
    public void editarSabor(int idCliente, String nome, String sobrenome, String telefone){
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


    // retorna clientes  -------------------------------------------------------------------------
    public List<Cliente> retornaListaSabor() {
        return clientes;
    }
    //--------------------------------------------------------------------------------------------


    // Buscar cliente por Id ---------------------------------------------------------------------
    public Cliente buscarPorId(int idCliente){

        for(Cliente cliente : clientes){

            if(cliente.getId() == idCliente){
                return cliente;
            }
        }

        throw new IllegalArgumentException("Cliente não encontrado");
    }
    //--------------------------------------------------------------------------------------------
















    // ==============================================================================================
}