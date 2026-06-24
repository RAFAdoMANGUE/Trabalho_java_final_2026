package service;

import exception.CampoInvalidoException;
import exception.PrecoInvalidoException;
import model.TabelaPreco;
import model.TipoPizza;
import repository.RepositoryMemoria;

public class PrecoService {

    RepositoryMemoria repositoryMemoria;

    public PrecoService(RepositoryMemoria repositoryMemoria) {
        this.repositoryMemoria = repositoryMemoria;
    }

    public double getPrecoSimples(){
        return repositoryMemoria.retornaTabelaPrecos().getPrecoSimples();
    }

    public double getPrecoEspecial(){
        return repositoryMemoria.retornaTabelaPrecos().getPrecoEspecial();
    }

    public double getPrecoPremium(){
        return repositoryMemoria.retornaTabelaPrecos().getPrecoPremium();
    }

    public double getPrecoPorTipo(TipoPizza tipo){
        validarTipo(tipo);
        return repositoryMemoria.retornaPrecoPorTipo(tipo);
    }

    public void alterarPreco(TipoPizza tipo, double valor) {
        validarTipo(tipo);
        validarPreco(valor);

        repositoryMemoria.editarPreco(tipo, valor);
    }

    public void validarTipo(TipoPizza tipo) {
        if (tipo == null) {
            throw new CampoInvalidoException("Tipo de pizza é obrigatório.");
        }
    }

    public void validarPreco(double valor){
        if (valor <= 0) {
            throw new PrecoInvalidoException("O preço deve ser maior que zero.");
        }
    }

}
