# Trabalho_java_final_2026

Trabalho final de Java desenvolvido para a disciplina de Programação Orientada a Objetos da Universidade Federal do Paraná.

O projeto consiste em um sistema desktop para gerenciamento de pedidos de pizza. A aplicação foi feita em Java, utilizando Swing para a interface gráfica, e organiza o código em camadas de model, view, service, repository e exception.

O sistema permite cadastrar e consultar clientes, sabores, preços, pedidos e status dos pedidos. Os dados são armazenados em memória durante a execução do programa, sem uso de banco de dados externo.

Participantes:

Calebe Rodrigues Carozzi  
Dancleve Rafael Peres de Oliveira Nascimento  
Rafael Blaskowski Demeterko  
Rafael Maldonado Caetano

Como executar:

Para compilar o projeto:

```bash
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name '*.java')
```

Para executar:

```bash
java -cp out view.TelaPrincipal
```
