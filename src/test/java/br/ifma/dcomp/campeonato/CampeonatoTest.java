package br.ifma.dcomp.campeonato;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class CampeonatoTest {
    private CampeonatoService campeonatoService;
    private Time time1, time2;
    private Estadio estadio1, estadio2;
    private Jogador jogador1, jogador2;
    private LocalDate hoje;

    @BeforeEach
    void setUp() {
        estadio1 = new Estadio("Maracanã", "Rio de Janeiro");
        estadio2 = new Estadio("Mineirão", "Belo Horizonte");
        time1 = new Time("Flamengo", estadio1);
        time2 = new Time("Cruzeiro", estadio2);
        jogador1 = new Jogador("João", LocalDate.of(1995, 5, 20), "M", 1.80);
        jogador2 = new Jogador("Maria", LocalDate.of(1997, 3, 15), "F", 1.65);
        Campeonato campeonato = new Campeonato("Brasileirão");
        campeonatoService = new CampeonatoService(campeonato);
        hoje = LocalDate.now();
    }

    @Test
    void deveLancarExcecaoAoAdicionarTimeDuplicado() {
        campeonatoService.adicionarTime(time1);
        assertThrows(IllegalArgumentException.class, () -> campeonatoService.adicionarTime(time1));
    }

    @Test
    void deveCalcularClassificacaoCorretamente() {
        campeonatoService.adicionarTime(time1);
        campeonatoService.adicionarTime(time2);
        Partida partida = new Partida(time1, time2, estadio1, hoje);
        partida.setResultado(2, 1);
        campeonatoService.adicionarPartida(partida);
        Map<Time, Integer> classificacao = campeonatoService.getClassificacao();
        assertEquals(3, classificacao.get(time1));
        assertEquals(0, classificacao.get(time2));
    }

    @Test
    void devePermitirEmpateEAtualizarClassificacao() {
        campeonatoService.adicionarTime(time1);
        campeonatoService.adicionarTime(time2);
        Partida partida = new Partida(time1, time2, estadio1, hoje);
        partida.setResultado(0, 0);
        campeonatoService.adicionarPartida(partida);
        Map<Time, Integer> classificacao = campeonatoService.getClassificacao();
        assertEquals(1, classificacao.get(time1));
        assertEquals(1, classificacao.get(time2));
    }

}