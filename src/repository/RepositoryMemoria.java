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






}
