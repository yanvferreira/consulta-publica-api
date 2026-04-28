package br.jus.tjrn.consulta.domain.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProcessoFiltro {

    String numero;
    String numeroReferencia;
    String nomeParte;
    String nomeAdvogado;
    String classeJudicial;
    String cpfCnpj;
    String oab;
    String ufOab;

    LocalDate dataInicio;
    LocalDate dataFim;
}
