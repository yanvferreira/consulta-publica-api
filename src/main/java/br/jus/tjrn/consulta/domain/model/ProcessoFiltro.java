package br.jus.tjrn.consulta.domain.model;

import java.time.LocalDate;

import br.jus.tjrn.consulta.shared.util.ProcessoNumeroUtils;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
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

    public ProcessoFiltro normalizar() {
        return this.toBuilder()
            .numero(numero != null ? ProcessoNumeroUtils.normalizar(numero) : null)
            .build();
    }
}
