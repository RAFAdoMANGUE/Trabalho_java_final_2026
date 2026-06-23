package model;

public class Quadrado extends Forma{
    private double lado;
    public Quadrado(){
    }

    public double getLado() {
        return lado;
    }

    public void setLado(double lado) {
        this.lado = lado;
    }

    @Override
    public double calculaArea(){
        return lado*lado;
    }

    @Override
    public String descricao(){
        return "Lado de: " + lado;
    }

}
