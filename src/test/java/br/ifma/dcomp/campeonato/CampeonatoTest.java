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
    void deveCalcularIdadeJogadorCorretamente() {
        assertEquals(30, jogador1.getIdade()); // 2025 - 1995 = 30
    }

    @Test
    void deveLancarExcecaoParaDataNascimentoInvalida() {
        assertThrows(IllegalArgumentException.class, () ->
                new Jogador("Teste", LocalDate.now().plusDays(1), "M", 1.80));
    }

    @Test
    void deveLancarExcecaoParaAlturaNegativa() {
        assertThrows(IllegalArgumentException.class, () ->
                new Jogador("Teste", LocalDate.of(1990, 1, 1), "M", -1.0));
    }

    @Test
    void deveCalcularAlturaMediaDoTime() {
        time1.adicionarJogador(jogador1); // 1.80
        time1.adicionarJogador(jogador2); // 1.65
        assertEquals(1.725, time1.getAlturaMedia(), 0.001); // (1.80 + 1.65) / 2
    }

    @Test
    void deveCriarPartidaValida() {
        campeonatoService.adicionarTime(time1);
        campeonatoService.adicionarTime(time2);
        Partida partida = new Partida(time1, time2, estadio1, hoje);
        assertDoesNotThrow(() -> campeonatoService.adicionarPartida(partida));
    }

    @Test
    void deveLancarExcecaoAoAdicionarTimeDuplicado() {
        campeonatoService.adicionarTime(time1);
        assertThrows(IllegalArgumentException.class, () -> campeonatoService.adicionarTime(time1));
    }

    @Test
    void deveFiltrarPartidasPorData() {
        campeonatoService.adicionarTime(time1);
        campeonatoService.adicionarTime(time2);
        Partida partida = new Partida(time1, time2, estadio1, hoje);
        campeonatoService.adicionarPartida(partida);
        List<Partida> partidas = campeonatoService.filtrarPartidasPorData(hoje);
        assertEquals(1, partidas.size());
        assertEquals(partida, partidas.get(0));
    }

    @Test
    void deveLancarExcecaoParaEstadioInvalido() {
        campeonatoService.adicionarTime(time1);
        campeonatoService.adicionarTime(time2);
        Estadio estadioInvalido = new Estadio("Arena", "São Paulo");
        Partida partida = new Partida(time1, time2, estadioInvalido, hoje);
        assertThrows(IllegalArgumentException.class, () -> campeonatoService.adicionarPartida(partida));
    }

    @Test
    void deveLancarExcecaoParaGolsNegativos() {
        Partida partida = new Partida(time1, time2, estadio1, hoje);
        assertThrows(IllegalArgumentException.class, () -> partida.setResultado(-1, 0));
    }

    @Test
    void deveAdicionarERemoverJogadores() {
        time1.adicionarJogador(jogador1);
        assertEquals(1, time1.getJogadores().size());
        time1.removerJogador(jogador1);
        assertEquals(0, time1.getJogadores().size());
    }

    @Test
    void deveLancarExcecaoAoAdicionarJogadorEmOutroTime() {
        time1.adicionarJogador(jogador1);
        assertThrows(IllegalArgumentException.class, () -> time2.adicionarJogador(jogador1));
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

    @Test
    void deveLancarExcecaoAoAdicionarPartidaAposInicio() {
        campeonatoService.adicionarTime(time1);
        campeonatoService.adicionarTime(time2);
        campeonatoService.iniciarCampeonato();
        Partida partida = new Partida(time1, time2, estadio1, hoje);
        assertThrows(IllegalStateException.class, () -> campeonatoService.adicionarPartida(partida));
    }
}