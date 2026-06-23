package model;

public class Triangulo implements Forma{
    private double lado;
    public void Triangulo(double lado){
        this.lado = lado;
    }

    public double getLado() {
        return lado;
    }

    public void setLado(double lado) {
        this.lado = lado;
    }

    @Override
    public double calculaArea(){
        return (Math.pow(this.lado,2) * Math.sqrt(3))/4;
    }
    @Override
    public String descricao(){
        return "lado de: " + lado;
    }

}
