package model;

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

}
