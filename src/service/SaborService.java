package service;

import exception.CampoInvalidoException;
import exception.SaborInvalidoException;
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
        Sabor sabor = new Sabor(nome, tipo);
        repositoryMemoria.salvaSabor(sabor);
    }

    public void excluirSabor(int idSabor) {
        repositoryMemoria.excluiSabor(idSabor);
    }

    public void editarSabor(int idSabor, String nome, TipoPizza tipo) {
        validarCampos(nome, tipo);

        nome = nome.trim();

        validarSaborDuplicado(nome, idSabor);

        repositoryMemoria.editarSabor(idSabor, nome, tipo);
    }

    public List<Sabor> listarSabores() {
        return repositoryMemoria.retornaListaSabor();
    }


    public List<Sabor> listarPorTipo(TipoPizza tipo) {
        if (tipo == null) {
            throw new CampoInvalidoException("Tipo da pizza é obrigatório.");
        }

        List<Sabor> resultado = new ArrayList<>();

        for (Sabor sabor : repositoryMemoria.retornaListaSabor()) {
            if (sabor.getTipoPizza() == tipo) {
                resultado.add(sabor);
            }
        }

        return resultado;
    }
//
//    public Sabor buscarSaborPorId(int idSabor) {
//        return repositoryMemoria.buscarSaborPorId(idSabor);
//    }


    public void validarCampos(String nome, TipoPizza tipo) {
        nome = nome.trim();

        if (nome == null || nome.isBlank()) {
            throw new CampoInvalidoException("Nome não pode ser nulo");
        }
        if (tipo == null) {
            throw new CampoInvalidoException("O Tipo da pizza é obrigatorio");
        }
    }

    private void validarSaborDuplicado(String nome, int idIgnorado) {
        for (Sabor sabor : repositoryMemoria.retornaListaSabor()) {
            boolean mesmoNome = sabor.getNome().equalsIgnoreCase(nome);

            if (mesmoNome) {
                throw new SaborInvalidoException("Já existe um sabor cadastrado com esse nome.");
            }
        }

    }
}
