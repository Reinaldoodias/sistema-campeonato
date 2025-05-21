package br.ifma.dcomp.campeonato;

import java.util.ArrayList;
import java.util.List;

public class Time {
    private String nome;
    private Estadio estadioSede;
    private List<Jogador> jogadores;

    public Time(String nome, Estadio estadioSede) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do time inválido");
        }
        this.nome = nome;
        this.estadioSede = estadioSede;
        this.jogadores = new ArrayList<>();
    }

    public void adicionarJogador(Jogador jogador) {
        if (jogador == null) {
            throw new IllegalArgumentException("Jogador inválido");
        }
        if (!jogador.settime(this)) {
            throw new IllegalArgumentException("Jogador já está no time");
        }
        jogadores.add(jogador);
    }

    public void removerJogador(Jogador jogador) {
        if (!jogadores.remove(jogador)) {
            throw new IllegalArgumentException("Jogador não encontrado no time");
        }
    }

    public double getAlturaMedia() {
        if (jogadores.isEmpty()) {
            return 0.0;
        }
        return jogadores.stream()
                .mapToDouble(Jogador::getAltura)
                .average()
                .orElse(0.0);
    }

    public String getNome() {
        return nome;
    }

    public Estadio getEstadioSede() {
        return estadioSede;
    }

    public List<Jogador> getJogadores() {
        return new ArrayList<>(jogadores); // Retorna cópia para encapsulamento
    }
}