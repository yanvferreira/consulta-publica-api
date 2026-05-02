package br.jus.tjrn.consulta.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Processo {
    int idProcesso;
    String numero;
}
