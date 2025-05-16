package br.ifma.dcomp.campeonato;

import java.time.LocalDate;
import java.time.Period;

public class Jogador {
    private int id;
    private String nome;
    private LocalDate nascimento;
    private String genero;
    private float altura;

    public Jogador(String nome, LocalDate nascimento, String genero, double altura){
        if(nascimento.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Data de nascimento inválida");
        }
        if(altura<=0){
            throw new IllegalArgumentException("Altura inválida");
        }

        this.nome = nome;
        this.nascimento=nascimento;
        this.genero = genero;
        this.altura = (float) altura;
    }

    public int getId() {
        return id;
    }

    public int getIdade(){
        return Period.between(nascimento, LocalDate.now()).getYears(); //Retorna a idade do jogador
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }
}
