package model;

import exception.CampoInvalidoException;
import model.TipoPizza;

public class TabelaPreco {
    private double precoSimples;
    private double precoEspecial;
    private double precoPremium;

    public TabelaPreco() {
        this.precoSimples = 0.05;
        this.precoEspecial = 0.07;
        this.precoPremium = 0.10;
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

    public double getPrecoPorTipo(TipoPizza tipo) {
        if (tipo == null) {
            throw new CampoInvalidoException("Tipo inválido");
        }

        switch (tipo) {
            case SIMPLES:
                return this.precoSimples;
            case ESPECIAL:
                return this.precoEspecial;
            case PREMIUM:
                return this.precoPremium;
            default:
                throw new CampoInvalidoException("Tipo inválido");
        }
    }

    public void alterarPreco(TipoPizza tipo, double valor) {
        if (tipo == null) {
            throw new CampoInvalidoException("Tipo inválido");
        }

        switch (tipo) {
            case SIMPLES:
                this.precoSimples = valor;
                break;
            case ESPECIAL:
                this.precoEspecial = valor;
                break;
            case PREMIUM:
                this.precoPremium = valor;
                break;
            default:
                throw new CampoInvalidoException("Tipo inválido");
        }
    }
}
