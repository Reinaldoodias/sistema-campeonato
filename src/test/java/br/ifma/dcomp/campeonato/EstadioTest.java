package br.ifma.dcomp.campeonato;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EstadioTest {
    private CampeonatoService campeonatoService;
    private Time time1, time2;
    private Estadio estadio1, estadio2;
    private Jogador jogador1, jogador2;
    private LocalDate hoje;
    private Campeonato campeonato;


    @BeforeEach
    public void setup(){
        estadio1 = new Estadio("Maracanã", "Rio de Janeiro");
        estadio2 = new Estadio("Itaquera", "São paulo");
        time1 = new Time("Flamengo", estadio1);
        time2 = new Time("Cruzeiro", estadio2);
        campeonato = new Campeonato("Brasileirão");
        campeonatoService = new CampeonatoService(campeonato);
        hoje = LocalDate.now();
    }

    @Test
    public void ExcecaoParaEstadioInvalido() {
        campeonatoService.adicionarTime(time1);
        campeonatoService.adicionarTime(time2);
        Estadio estadioInvalido = new Estadio("Arena", "São Paulo");
        Partida partida = new Partida(time1, time2, estadioInvalido, hoje);
        assertThrows(IllegalArgumentException.class, () -> campeonatoService.adicionarPartida(partida));
    }
}
