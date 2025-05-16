package br.ifma.dcomp.campeonato;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JogadorTest {
    @Test
    public void idadeCorreta(){
        LocalDate nascimento = LocalDate.of(2000, 5,15);
        Jogador jogador = new Jogador("Reinaldo",nascimento,"Masculino",1.81);

        int idadeEsperada = LocalDate.now().getYear() - nascimento.getYear();
        assertEquals(idadeEsperada, jogador.getIdade()); //Verificar se a idade coincide
    }
    @Test
    public void dataNoFuturo(){
        LocalDate nascimento = LocalDate.now().plusDays(1);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->{
            new Jogador("Reinaldo",nascimento,"Masculino",1.81);
        });

        assertEquals("Data de nascimento inválida", exception.getMessage());
    }

    @Test
    public void alturaNegativa(){
        int altura = 0;
        Exception exception = assertThrows(IllegalArgumentException.class, () ->{
            new Jogador("Reinaldo",LocalDate.now(),"Masculino",altura);
        });

        assertEquals("Altura inválida", exception.getMessage());
    }

}
