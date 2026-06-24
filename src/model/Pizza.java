package model;

import exception.NenhumSabor;
import exception.PizzaComMaisDoisSabores;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
    private Forma forma;

    List<Sabor> sabor = new ArrayList<>();

    public Pizza(Forma forma){
        this.forma = forma;
    }

    public Forma getForma() {
        return forma;
    }

    public void setForma(Forma forma) {
        this.forma = forma;
    }

    public List<Sabor> getSabor() {
        return sabor;
    }


    public void adicionaSabor(Sabor sabor) {
        this.sabor.add(sabor);
    }

    public void removeSabor(Sabor sabor){
        this.sabor.remove(sabor);
    }

    public double calculaArea(){
        return forma.calculaArea();
    }

    public double calculaPreco(TabelaPreco tabelaPreco, TipoPizza tipo){
        return  calculaMediaPrecoSabores(tabelaPreco) * calculaArea();
    }
    public double calculaMediaPrecoSabores(TabelaPreco tabelaPreco){
        double soma = 0;
        if(this.sabor.isEmpty())
            throw new NenhumSabor("Sem nenhum sabor");
        if(sabor.size() >= 3)
            throw new PizzaComMaisDoisSabores("Pizza não pode ter mais de dois sabores");
        for(Sabor sabor : this.sabor){
            soma += tabelaPreco.getPrecoPorTipo(sabor.getTipoPizza());
        }
        return soma / this.sabor.size();
    }
}
