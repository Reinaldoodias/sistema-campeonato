package br.ifma.dcomp.campeonato;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Inicialização
        Campeonato campeonato = new Campeonato("Brasileirão 2025");
        CampeonatoService campeonatoService = new CampeonatoService(campeonato);
        Estadio estadio1 = new Estadio("Maracanã", "Rio de Janeiro");
        Estadio estadio2 = new Estadio("Mineirão", "Belo Horizonte");
        Time time1 = new Time("Flamengo", estadio1);
        Time time2 = new Time("Cruzeiro", estadio2);

        // 1. Cálculo da idade do jogador
        System.out.println("=== Teste 1: Cálculo da idade do jogador ===");
        Jogador jogador1 = new Jogador("João", LocalDate.of(1995, 5, 20), "M", 1.80);
        System.out.println("Idade de João: " + jogador1.getIdade() + " anos");

        // 2. Entradas inválidas (data de nascimento futura e altura negativa)
        System.out.println("\n=== Teste 2: Entradas inválidas ===");
        try {
            new Jogador("Teste", LocalDate.now().plusDays(1), "M", 1.80);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado (data futura): " + e.getMessage());
        }
        try {
            new Jogador("Teste", LocalDate.of(1990, 1, 1), "M", -1.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado (altura negativa): " + e.getMessage());
        }

        // 3. Cálculo da altura média do time
        System.out.println("\n=== Teste 3: Altura média do time ===");
        time1.adicionarJogador(jogador1);
        Jogador jogador2 = new Jogador("Maria", LocalDate.of(1997, 3, 15), "F", 1.65);
        time1.adicionarJogador(jogador2);
        System.out.println("Altura média do Flamengo: " + time1.getAlturaMedia() + " metros");

        // 4. Criar partida válida
        System.out.println("\n=== Teste 4: Criar partida válida ===");
        campeonatoService.adicionarTime(time1);
        campeonatoService.adicionarTime(time2);
        Partida partida1 = new Partida(time1, time2, estadio1, LocalDate.now());
        try {
            campeonatoService.adicionarPartida(partida1);
            System.out.println("Partida criada: Flamengo vs Cruzeiro no Maracanã");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }

        // 5. Exceção ao adicionar time duplicado
        System.out.println("\n=== Teste 5: Adicionar time duplicado ===");
        try {
            campeonatoService.adicionarTime(time1);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado (time duplicado): " + e.getMessage());
        }

        // 6. Filtrar partidas por data, estádio e mandante
        System.out.println("\n=== Teste 6: Filtrar partidas ===");
        List<Partida> partidasPorData = campeonatoService.filtrarPartidasPorData(LocalDate.now());
        System.out.println("Partidas hoje: " + partidasPorData.size());
        List<Partida> partidasPorEstadio = campeonatoService.filtrarPartidasPorEstadio(estadio1);
        System.out.println("Partidas no Maracanã: " + partidasPorEstadio.size());
        List<Partida> partidasPorMandante = campeonatoService.filtrarPartidasPorMandante(time1);
        System.out.println("Partidas com Flamengo como mandante: " + partidasPorMandante.size());

        // 7. Verificar estádio da partida
        System.out.println("\n=== Teste 7: Estádio corresponde ao time ===");
        try {
            Estadio estadioInvalido = new Estadio("Arena", "São Paulo");
            Partida partidaInvalida = new Partida(time1, time2, estadioInvalido, LocalDate.now());
            campeonatoService.adicionarPartida(partidaInvalida);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado (estádio inválido): " + e.getMessage());
        }

        // 8. Exceção para resultado inválido
        System.out.println("\n=== Teste 8: Resultado inválido ===");
        try {
            partida1.setResultado(-1, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado (gols negativos): " + e.getMessage());
        }

        // 9. Adicionar e remover jogadores
        System.out.println("\n=== Teste 9: Adicionar e remover jogadores ===");
        Jogador jogador3 = new Jogador("Pedro", LocalDate.of(1998, 7, 10), "M", 1.85);
        time2.adicionarJogador(jogador3);
        System.out.println("Jogadores no Cruzeiro: " + time2.getJogadores().size());
        time2.removerJogador(jogador3);
        System.out.println("Jogadores no Cruzeiro após remoção: " + time2.getJogadores().size());

        // 10. Listar partidas de um dia
        System.out.println("\n=== Teste 10: Listar partidas de um dia ===");
        Partida partida2 = new Partida(time2, time1, estadio2, LocalDate.now());
        campeonatoService.adicionarPartida(partida2);
        partidasPorData = campeonatoService.filtrarPartidasPorData(LocalDate.now());
        System.out.println("Partidas hoje (após adicionar outra): " + partidasPorData.size());

        // 11. Exceção ao adicionar jogador em outro time
        System.out.println("\n=== Teste 11: Jogador em outro time ===");
        try {
            time2.adicionarJogador(jogador1);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado (jogador em outro time): " + e.getMessage());
        }

        // 12. Calcular classificação
        System.out.println("\n=== Teste 12: Calcular classificação ===");
        partida1.setResultado(2, 1);
        Map<Time, Integer> classificacao = campeonatoService.getClassificacao();
        System.out.println("Pontos do Flamengo: " + classificacao.get(time1));
        System.out.println("Pontos do Cruzeiro: " + classificacao.get(time2));

        // 13. Definir empate e atualizar classificação
        System.out.println("\n=== Teste 13: Empate e classificação ===");
        Partida partida3 = new Partida(time1, time2, estadio1, LocalDate.now().minusDays(1));
        campeonatoService.adicionarPartida(partida3);
        partida3.setResultado(0, 0);
        classificacao = campeonatoService.getClassificacao();
        System.out.println("Pontos do Flamengo após empate: " + classificacao.get(time1));
        System.out.println("Pontos do Cruzeiro após empate: " + classificacao.get(time2));

        // 14. Exceção ao adicionar partida após início
        System.out.println("\n=== Teste 14: Adicionar partida após início ===");
        campeonatoService.iniciarCampeonato();
        try {
            Partida partida4 = new Partida(time1, time2, estadio1, LocalDate.now());
            campeonatoService.adicionarPartida(partida4);
        } catch (IllegalStateException e) {
            System.out.println("Erro esperado (partida após início): " + e.getMessage());
        }
    }
}