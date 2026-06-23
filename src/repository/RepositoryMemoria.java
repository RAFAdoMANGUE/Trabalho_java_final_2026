package repository;

import exception.CampoInvalidoException;
import exception.ClienteNaoEncontradoException;
import exception.SaborInvalidoException;
import model.Cliente;
import model.Sabor;
import model.TipoPizza;

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
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }
    }
    //--------------------------------------------------------------------------------------------


    // Editar cliente ---------------------------------------------------------------------------
    public void editarCliente(int idCliente, String nome, String sobrenome, String telefone){
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(idCliente)) {
                if(!nome.isBlank()){
                    cliente.setNome(nome);
                }
                if(!sobrenome.isBlank()){
                    cliente.setSobrenome(sobrenome);
                }
                if(!telefone.isBlank()){
                cliente.setTelefone(telefone);
                }
                return;
            }
        }
        throw new ClienteNaoEncontradoException("Cliente não encontrado");
    }
    //--------------------------------------------------------------------------------------------


    // retorna clientes  -------------------------------------------------------------------------
    public List<Cliente> retornaListaCliente() {
        return clientes;
    }
    //--------------------------------------------------------------------------------------------



    // ==============================================================================================






    //==== SABORES DA PIZZA ==========================================================================


    // Salvar sabores ---------------------------------------------------------------------------
    private List<Sabor> sabores = new ArrayList<>();
    int contadorSabor = 1;

    public void salvaSabor(Sabor sabor){
        sabor.setIdSabor(contadorSabor);
        this.sabores.add(sabor);
        contadorSabor++;
    }
    //--------------------------------------------------------------------------------------------


    // Excluir sabor ---------------------------------------------------------------------------
    public void excluiSabor(int idSabor){
        boolean removeu = sabores.removeIf(s -> s.getIdSabor() == idSabor);

        if (!removeu) {
            throw new SaborInvalidoException("Sabor não encontrado");
        }
    }
    //--------------------------------------------------------------------------------------------


    // Editar sabor ---------------------------------------------------------------------------
    public void editarSabor(int idSabor, String nome, TipoPizza tipo){
        for (Sabor sabor: sabores) {
            if (sabor.getIdSabor().equals(idSabor)) {
                if(!nome.isBlank()){
                    sabor.setNome(nome);
                }
                sabor.setTipoPizza(tipo);
                return;
            }
        }
        throw new SaborInvalidoException("Sabor não encontrado");
    }
    //--------------------------------------------------------------------------------------------


    // retorna sabor  -------------------------------------------------------------------------
    public List<Sabor> retornaListaSabor() {
        return sabores;
    }
    //--------------------------------------------------------------------------------------------





    // ==============================================================================================
}