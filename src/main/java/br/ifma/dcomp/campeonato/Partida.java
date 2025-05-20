package br.ifma.dcomp.campeonato;

import java.time.LocalDate;

public class Partida {
    private Time mandante;
    private Time visitante;
    private Estadio estadio;
    private LocalDate data;
    private Integer golsMandante;
    private Integer golsVisitante;

    public Partida(Time mandante, Time visitante, Estadio estadio, LocalDate data) {
        if (mandante == null || visitante == null || estadio == null || data == null) {
            throw new IllegalArgumentException("Parâmetros da partida inválidos");
        }
        if (mandante.equals(visitante)) {
            throw new IllegalArgumentException("Mandante e visitante não podem ser o mesmo time");
        }
        if (data.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data da partida não pode ser futura");
        }
        this.mandante = mandante;
        this.visitante = visitante;
        this.estadio = estadio;
        this.data = data;
    }

    public void setResultado(int golsMandante, int golsVisitante) {
        if (golsMandante < 0 || golsVisitante < 0) {
            throw new IllegalArgumentException("Número de gols não pode ser negativo");
        }
        this.golsMandante = golsMandante;
        this.golsVisitante = golsVisitante;
    }

    public Time getMandante() {
        return mandante;
    }

    public Time getVisitante() {
        return visitante;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public LocalDate getData() {
        return data;
    }

    public Integer getGolsMandante() {
        return golsMandante;
    }

    public Integer getGolsVisitante() {
        return golsVisitante;
    }
}