package service;

import exception.CampoInvalidoException;
import model.Circulo;
import model.Forma;
import model.Quadrado;
import model.Triangulo;

public class FormaFactory {

    public Forma criarQuadradoPorLado(double lado) {
        validarLadoQuadrado(lado);
        return new Quadrado(lado);
    }

    public Forma criarCirculoPorRaio(double raio) {
        validarRaioCirculo(raio);
        return new Circulo(raio);
    }

    public Forma criarTrianguloPorLado(double lado) {
        validarLadoTriangulo(lado);
        return new Triangulo(lado);
    }

    public Forma criarQuadradoPorArea(double area) {
        validarArea(area);
        double lado = Math.sqrt(area);
        return criarQuadradoPorLado(lado);
    }

    public Forma criarCirculoPorArea(double area) {
        validarArea(area);
        double raio = Math.sqrt(area / Math.PI);
        return criarCirculoPorRaio(raio);
    }

    public Forma criarTrianguloPorArea(double area) {
        validarArea(area);
        double lado = Math.sqrt((4 * area) / Math.sqrt(3));
        return criarTrianguloPorLado(lado);
    }

    private void validarArea(double area) {
        if (area < 100 || area > 1600) {
            throw new CampoInvalidoException("A área deve estar entre 100 e 1600 cm².");
        }
    }

    private void validarLadoQuadrado(double lado) {
        if (lado < 10 || lado > 40) {
            throw new CampoInvalidoException("O lado do quadrado deve estar entre 10 e 40 cm.");
        }
    }

    private void validarLadoTriangulo(double lado) {
        if (lado < 20 || lado > 60) {
            throw new CampoInvalidoException("O lado do triângulo deve estar entre 20 e 60 cm.");
        }
    }

    private void validarRaioCirculo(double raio) {
        if (raio < 7 || raio > 23) {
            throw new CampoInvalidoException("O raio do círculo deve estar entre 7 e 23 cm.");
        }
    }
}