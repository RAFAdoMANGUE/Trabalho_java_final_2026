package model;

import model.TipoPizza;

public class TabelaPreco {
    private double precoSimples;
    private double precoEspecial;
    private double precoPremium;

    public TabelaPreco(){
    }
    public double getPrecoSimples(){
        return this.precoSimples;
    }

    public double getPrecoEspecial() {
        return precoEspecial;
    }

    public double getPrecoPremium() {
        return precoPremium;
    }
    public void setPrecoSimples(double precoSimples) {
        this.precoSimples = precoSimples;
    }

    public void setPrecoEspecial(double precoEspecial) {
        this.precoEspecial = precoEspecial;
    }

    public void setPrecoPremium(double precoPremium) {
        this.precoPremium = precoPremium;
    }
    public double getPrecoPorTipo(TipoPizza tipo) {
        switch (tipo) {
            case SIMPLES:
                return this.precoSimples;
            case ESPECIAL:
                return this.precoEspecial;
            case PREMIUM:
                return this.precoPremium;
            default:
                throw new IllegalArgumentException("Tipo inválido");
        }
    }
}
