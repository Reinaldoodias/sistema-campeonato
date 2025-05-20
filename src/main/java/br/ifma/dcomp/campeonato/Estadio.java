package br.ifma.dcomp.campeonato;

public class Estadio {
    private String nome;
    private String endereco;

    public Estadio(String nome, String endereco) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do estádio inválido");
        }
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço do estádio inválido");
        }
        this.nome = nome;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }
}