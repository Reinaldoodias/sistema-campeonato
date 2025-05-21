package br.ifma.dcomp.campeonato;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PartidaTest {
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
    public void filtrarPartidaPorData(){
        campeonatoService.adicionarTime(time1);
        campeonatoService.adicionarTime(time2);
        Partida partida = new Partida(time1, time2, estadio1, hoje);
        campeonatoService.adicionarPartida(partida);
        List<Partida> partidas = campeonatoService.filtrarPartidasPorData(hoje);
        assertEquals(1, partidas.size());
        assertEquals(partida, partidas.get(0));
    }

    @Test
    void deveLancarExcecaoParaGolsNegativos() {
        Partida partida = new Partida(time1, time2, estadio1, hoje);
        assertThrows(IllegalArgumentException.class, () -> partida.setResultado(-1, 0));
    }

    @Test
    void deveLancarExcecaoAoAdicionarPartidaAposInicio() {
        campeonatoService.adicionarTime(time1);
        campeonatoService.adicionarTime(time2);
        campeonatoService.iniciarCampeonato();
        Partida partida = new Partida(time1, time2, estadio1, hoje);
        assertThrows(IllegalStateException.class, () -> campeonatoService.adicionarPartida(partida));
    }

}
