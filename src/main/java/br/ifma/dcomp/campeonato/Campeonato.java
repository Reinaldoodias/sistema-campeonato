package br.ifma.dcomp.campeonato;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Campeonato {
    private String nome;
    private List<Time> times;
    private List<Partida> partidas;
    private boolean iniciado;

    public Campeonato(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do campeonato inválido");
        }
        this.nome = nome;
        this.times = new ArrayList<>();
        this.partidas = new ArrayList<>();
        this.iniciado = false;
    }

    public void adicionarTime(Time time) {
        if (iniciado) {
            throw new IllegalStateException("Não é possível adicionar times após o início do campeonato");
        }
        if (times.contains(time)) {
            throw new IllegalArgumentException("Time já está no campeonato");
        }
        times.add(time);
    }

    public void adicionarPartida(Partida partida) {
        if (iniciado) {
            throw new IllegalStateException("Não é possível adicionar partidas após o início do campeonato");
        }
        if (!times.contains(partida.getMandante()) || !times.contains(partida.getVisitante())) {
            throw new IllegalArgumentException("Times da partida devem estar no campeonato");
        }
        partidas.add(partida);
    }

    public void iniciarCampeonato() {
        this.iniciado = true;
    }

    public List<Partida> filtrarPartidasPorData(LocalDate data) {
        return partidas.stream()
                .filter(p -> p.getData().equals(data))
                .collect(Collectors.toList());
    }

    public List<Partida> filtrarPartidasPorEstadio(Estadio estadio) {
        return partidas.stream()
                .filter(p -> p.getEstadio().equals(estadio))
                .collect(Collectors.toList());
    }

    public List<Partida> filtrarPartidasPorMandante(Time mandante) {
        return partidas.stream()
                .filter(p -> p.getMandante().equals(mandante))
                .collect(Collectors.toList());
    }

    public Map<Time, Integer> getClassificacao() {
        Map<Time, Integer> classificacao = new HashMap<>();
        for (Time time : times) {
            classificacao.put(time, 0);
        }
        for (Partida partida : partidas) {
            if (partida.getGolsMandante() != null && partida.getGolsVisitante() != null) {
                if (partida.getGolsMandante() > partida.getGolsVisitante()) {
                    classificacao.compute(partida.getMandante(), (k, v) -> v + 3);
                } else if (partida.getGolsMandante() < partida.getGolsVisitante()) {
                    classificacao.compute(partida.getVisitante(), (k, v) -> v + 3);
                } else {
                    classificacao.compute(partida.getMandante(), (k, v) -> v + 1);
                    classificacao.compute(partida.getVisitante(), (k, v) -> v + 1);
                }
            }
        }
        return classificacao;
    }
}