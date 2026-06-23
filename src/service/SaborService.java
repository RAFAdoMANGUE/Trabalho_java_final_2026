package service;

import model.Sabor;
import model.TipoPizza;
import repository.RepositoryMemoria;

public class SaborService {

    RepositoryMemoria repositoryMemoria = new RepositoryMemoria();

    public void cadastrarSabor(String nome, TipoPizza tipo){

        Sabor sabor = new Sabor(nome, tipo);


    }
//
//    public void validaCampos(String nome, TipoPizza tipo){
//        if(nome == null || nome.isBlank()){
//            th
//        }
//    }
//
}
