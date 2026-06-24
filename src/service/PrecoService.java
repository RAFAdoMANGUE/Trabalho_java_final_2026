package service;

import repository.RepositoryMemoria;

public class PrecoService {
    RepositoryMemoria repositoryMemoria = new RepositoryMemoria();
    public void editaTabelaPreco(double precoSimples, double precoEspecial, double precoPremium){
        repositoryMemoria.cadastraTabelaPreco(precoSimples,precoEspecial,precoPremium);
    }

}
