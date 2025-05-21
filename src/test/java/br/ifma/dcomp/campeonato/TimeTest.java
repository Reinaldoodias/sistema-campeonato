package br.ifma.dcomp.campeonato;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TimeTest {
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
        jogador1 = new Jogador("João", LocalDate.of(1995, 5, 20), "M", 1.76);
        jogador2 = new Jogador("Reinaldo", LocalDate.of(2004, 5, 4), "M", 1.81);
    }

    @Test
    public void CalcularAlturaMediaDoTime() {
        time1.adicionarJogador(jogador1); // 1.80
        time1.adicionarJogador(jogador2); // 1.65
        assertEquals(1.785, time1.getAlturaMedia(), 0.001); // (1.80 + 1.65) / 2
    }

    @Test
    void deveAdicionarERemoverJogadores() {
        time1.adicionarJogador(jogador1);
        assertEquals(1, time1.getJogadores().size());
        time1.removerJogador(jogador1);
        assertEquals(0, time1.getJogadores().size());
    }

    @Test
    void exececaoAoAdicionarJogadorEmOutroTime(){
        time1.adicionarJogador(jogador1);
        Exception e = assertThrows(IllegalArgumentException.class, () -> {time2.adicionarJogador(jogador1);});
        assertEquals("Jogador já está no time", e.getMessage());

    }
}
