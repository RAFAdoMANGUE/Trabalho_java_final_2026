package service;

import exception.CampoInvalidoException;
import exception.SaborInvalidoException;
import model.Cliente;
import model.Sabor;
import model.TipoPizza;
import repository.RepositoryMemoria;

import java.util.ArrayList;
import java.util.List;

public class SaborService {

    RepositoryMemoria repositoryMemoria;

    public SaborService(RepositoryMemoria repositoryMemoria) {
        this.repositoryMemoria = repositoryMemoria;
    }

    public void cadastrarSabor(String nome, TipoPizza tipo) {
        validarCampos(nome, tipo);
        validarSaborDuplicado(nome);
        Sabor sabor = new Sabor(nome, tipo);
        repositoryMemoria.salvaSabor(sabor);
    }

    public void excluirSabor(int idSabor) {
        repositoryMemoria.excluiSabor(idSabor);
    }

    public void editarSabor(int idSabor, String nome, TipoPizza tipo) {
        if (tipo == null) {
            throw new CampoInvalidoException("O Tipo da pizza é obrigatorio");
        }

        nome = nome.trim();

        validarSaboreDuplicadoEditar(idSabor, nome);

        repositoryMemoria.editarSabor(idSabor, nome, tipo);
    }

    public List<Sabor> listarSabores() {
        return repositoryMemoria.retornaListaSabor();
    }


    public List<Sabor> listarPorSabor(String nomeSabor) {

        List<Sabor> resultado = new ArrayList<>();

        for (Sabor saborAtual : repositoryMemoria.retornaListaSabor()) {
            if (nomeSabor.isBlank() || saborAtual.getNome().toLowerCase().contains(nomeSabor)) {
                resultado.add(saborAtual);
            }
        }

        return resultado;
    }



    public void validarCampos(String nome, TipoPizza tipo) {
        nome = nome.trim();

        if (nome == null || nome.isBlank()) {
            throw new CampoInvalidoException("Nome não pode ser nulo");
        }
        if (tipo == null) {
            throw new CampoInvalidoException("O Tipo da pizza é obrigatorio");
        }
    }

    private void validarSaborDuplicado(String nome) {
        for (Sabor sabor : repositoryMemoria.retornaListaSabor()) {
            boolean mesmoNome = sabor.getNome().equalsIgnoreCase(nome);

            if (mesmoNome) {
                throw new SaborInvalidoException("Já existe um sabor cadastrado com esse nome.");
            }
        }

    }

    public void validarSaboreDuplicadoEditar(int idSabor, String nome) {
        for (Sabor sabor : repositoryMemoria.retornaListaSabor()) {
            if (sabor.getNome().equalsIgnoreCase(nome)) {
                if (sabor.getIdSabor().equals(idSabor)) {
                    return;
                }
                throw new CampoInvalidoException("Já existe outro sabor com esse nome");
            }
        }
    }
}
