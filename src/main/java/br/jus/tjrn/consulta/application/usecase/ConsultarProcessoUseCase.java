package br.jus.tjrn.consulta.application.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.jus.tjrn.consulta.domain.model.Processo;
import br.jus.tjrn.consulta.domain.model.ProcessoFiltro;
import br.jus.tjrn.consulta.domain.repository.ProcessoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultarProcessoUseCase {

    private final ProcessoRepository repository;
    private Boolean executarConsultaNativa = Boolean.FALSE;

    public Page<Processo> executar(ProcessoFiltro filtro, Pageable pageable) {
        ProcessoFiltro filtroNormalizado = filtro.normalizar();
        validarFiltro(filtroNormalizado);

        if (executarConsultaNativa) {
            return repository.consultaJPANativa(filtroNormalizado, pageable);
        }

        return repository.consultarCabecalhoProcesso(filtroNormalizado, pageable);
    }

    /**
     * Valida os filtros e define se a consulta deve ser feita via JPA Specification ou JPQL nativo.
     * 
     * @param filtro
     */
    private void validarFiltro(ProcessoFiltro filtro) {
        if (filtro.getNomeAdvogado() != null) {
            String[] partes = filtro.getNomeAdvogado().trim().split(" ");

            if (partes.length < 2) {
                throw new IllegalArgumentException("Informe nome e sobrenome do advogado");
            }

            this.executarConsultaNativa = true;
        }

        if (filtro.getNomeParte() != null) {
            String[] partes = filtro.getNomeParte().trim().split(" ");

            if (partes.length < 2) {
                throw new IllegalArgumentException("Informe nome e sobrenome da parte");
            }

            this.executarConsultaNativa = true;
        }

        if (filtro.getDataInicio() != null && filtro.getDataFim() != null) {
            if (filtro.getDataInicio().isAfter(filtro.getDataFim())) {
                throw new IllegalArgumentException("Data de início deve ser anterior à data de fim");
            }
        }

        if (filtro.getCpfCnpj() != null) {
            // Validar CPF/CNPJ
            this.executarConsultaNativa = true;
        }

        if (filtro.getOab() != null) {
            // Validar OAB
            this.executarConsultaNativa = true;
        }
    }

}
