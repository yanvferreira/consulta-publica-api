package br.jus.tjrn.consulta.shared.util;

import java.time.LocalDate;
import java.util.Objects;

public class ProcessoDataUtils {

    public static void verificaInicioFim(LocalDate dataInicio, LocalDate dataFim) {
        LocalDate inicio = Objects.requireNonNull(dataInicio, "Data de início é obrigatória");
        LocalDate fim = Objects.requireNonNull(dataFim, "Data de fim é obrigatória");

        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("Data de início deve ser anterior à data de fim");
        }
    }

}
