package model;

public class Circulo extends Forma{
    private double raio;
    public Circulo(){

    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    @Override
    public double calculaArea(){
        return Math.PI*(Math.pow(this.raio,2));
    }

    @Override
    public String descricao(){
        return "Circulo - Raio de: " + raio;
    }
}
