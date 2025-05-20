package br.ifma.dcomp.campeonato;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CampeonatoService {
    private final Campeonato campeonato;

    public CampeonatoService(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public void adicionarTime(Time time) {
        campeonato.adicionarTime(time);
    }

    public void adicionarPartida(Partida partida) {
        if (!isEstadioValidoParaPartida(partida)) {
            throw new IllegalArgumentException("Estádio deve ser o estádio-sede do mandante ou visitante");
        }
        campeonato.adicionarPartida(partida);
    }

    public void iniciarCampeonato() {
        campeonato.iniciarCampeonato();
    }

    public boolean isEstadioValidoParaPartida(Partida partida) {
        return partida.getEstadio().equals(partida.getMandante().getEstadioSede()) ||
                partida.getEstadio().equals(partida.getVisitante().getEstadioSede());
    }

    public List<Partida> filtrarPartidasPorData(LocalDate data) {
        return campeonato.filtrarPartidasPorData(data);
    }

    public List<Partida> filtrarPartidasPorEstadio(Estadio estadio) {
        return campeonato.filtrarPartidasPorEstadio(estadio);
    }

    public List<Partida> filtrarPartidasPorMandante(Time mandante) {
        return campeonato.filtrarPartidasPorMandante(mandante);
    }

    public Map<Time, Integer> getClassificacao() {
        return campeonato.getClassificacao();
    }
}