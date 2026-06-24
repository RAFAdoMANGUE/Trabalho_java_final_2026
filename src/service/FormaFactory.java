package service;

import exception.CampoInvalidoException;
import model.Circulo;
import model.Quadrado;
import model.Triangulo;
import repository.RepositoryMemoria;

public class FormaFactory {

    private RepositoryMemoria repositoryMemoria;

    public FormaFactory(RepositoryMemoria repositoryMemoria) {
        this.repositoryMemoria = repositoryMemoria;
    }

    public Quadrado criarQuadradoPorLado(double lado) {
        validaDimensao(lado, "Lado do quadrado");
        Quadrado quadrado = new Quadrado();
        quadrado.setLado(lado);
        return quadrado;
    }

    public Circulo criarCirculoPorRaio(double raio) {
        validaDimensao(raio, "Raio do circulo");
        Circulo circulo = new Circulo();
        circulo.setRaio(raio);
        return circulo;
    }

    public Triangulo criarTrianguloPorLado(double lado) {
        validaDimensao(lado, "Lado do triangulo");
        Triangulo triangulo = new Triangulo();
        triangulo.setLado(lado);
        return triangulo;
    }

    public Quadrado criarQuadradoPorArea(double area) {
        validaDimensao(area, "Area do quadrado");
        double lado = Math.sqrt(area);
        return criarQuadradoPorLado(lado);
    }

    public Circulo criarCirculoPorArea(double area) {
        validaDimensao(area, "Area do circulo");
        double raio = Math.sqrt(area / Math.PI);
        return criarCirculoPorRaio(raio);
    }

    public Triangulo criarTrianguloPorArea(double area) {
        validaDimensao(area, "Area do triangulo");
        double lado = Math.sqrt((4 * area) / Math.sqrt(3));
        return criarTrianguloPorLado(lado);
    }

    public void validaDimensao(double valor, String nomeCampo) {
        if (valor <= 0) {
            throw new CampoInvalidoException(nomeCampo + " deve ser maior que zero");
        }
    }
}