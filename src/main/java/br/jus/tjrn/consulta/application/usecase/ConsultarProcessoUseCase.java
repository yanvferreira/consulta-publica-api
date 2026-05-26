package br.jus.tjrn.consulta.application.usecase;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.jus.tjrn.consulta.domain.model.Processo;
import br.jus.tjrn.consulta.domain.model.ProcessoFiltro;
import br.jus.tjrn.consulta.domain.repository.ProcessoRepository;
import br.jus.tjrn.consulta.shared.util.ProcessoDataUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultarProcessoUseCase {

    private final ProcessoRepository repository;

    public Page<Processo> executar(ProcessoFiltro filtro, Pageable pageable) {

        ProcessoFiltro filtroNormalizado = filtro.normalizar();

        this.validarFiltro(filtroNormalizado);

        return this.consultaNativa(filtroNormalizado)
            ? repository.consultaJPANativa(filtroNormalizado, pageable)
            : repository.consultarCabecalhoProcesso(filtroNormalizado, pageable);
    }

    /**
     * Valida os filtros.
     * 
     * @param filtro
     * @return boolean indicando se a consulta nativa deve ser executada
     */
    private void validarFiltro(ProcessoFiltro filtro) { 
        if (filtro.isVazio()) {
            throw new IllegalArgumentException("Ao menos um campo deve ser informado.");
        }

        if (filtro.getDataInicio() != null && filtro.getDataFim() != null) {
            ProcessoDataUtils.verificaInicioFim(filtro.getDataInicio(), filtro.getDataFim());
        }       
    }

    /**
     * Verifica se a consulta deve ser executada com quuery nativa
     * 
     * @param filtro
     */
    private boolean consultaNativa(ProcessoFiltro filtro) {
        return Objects.nonNull(filtro.getNomeAdvogado()) ||
               Objects.nonNull(filtro.getNomeParte()) ||
               Objects.nonNull(filtro.getCpfCnpj()) ||
               Objects.nonNull(filtro.getOab()) ||
               Objects.nonNull(filtro.getUfOab()) ||
               Objects.nonNull(filtro.getNumeroReferencia());
    }

}
