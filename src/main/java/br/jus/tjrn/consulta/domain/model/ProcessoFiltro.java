package br.jus.tjrn.consulta.domain.model;

import java.time.LocalDate;

import br.jus.tjrn.consulta.shared.util.AdvogadoUtils;
import br.jus.tjrn.consulta.shared.util.ProcessoNumeroUtils;
import br.jus.tjrn.consulta.shared.util.ProcessoParteUtils;
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
            .numeroReferencia(numeroReferencia != null ? ProcessoNumeroUtils.normalizar(numeroReferencia) : null)
            .nomeParte(nomeParte != null ? ProcessoParteUtils.validarNome(nomeParte) : null)
            .nomeAdvogado(nomeAdvogado != null ? AdvogadoUtils.validarNome(nomeAdvogado) : null)
            .cpfCnpj(cpfCnpj != null ? ProcessoParteUtils.validarCpfCnpj(cpfCnpj) : null)
            .oab(oab != null ? AdvogadoUtils.normalizarOab(oab) : null)
            .ufOab(ufOab != null ? AdvogadoUtils.normalizarUF(ufOab) : null)
            .build();
    }

    public boolean isVazio() {
        return (numero == null || numero.isBlank()) &&
               (numeroReferencia == null || numeroReferencia.isBlank()) &&
               (nomeParte == null || nomeParte.isBlank()) &&
               (nomeAdvogado == null || nomeAdvogado.isBlank()) &&
               (classeJudicial == null || classeJudicial.isBlank()) &&
               (cpfCnpj == null || cpfCnpj.isBlank()) &&
               (oab == null || oab.isBlank()) &&
               (ufOab == null || ufOab.isBlank()) &&
               dataInicio == null &&
               dataFim == null;
    }
}
