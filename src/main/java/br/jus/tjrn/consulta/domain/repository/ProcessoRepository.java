package br.jus.tjrn.consulta.domain.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.jus.tjrn.consulta.domain.model.Processo;
import br.jus.tjrn.consulta.domain.model.ProcessoFiltro;

public interface ProcessoRepository {
    
    Page<Processo> consultarCabecalhoProcesso(ProcessoFiltro filtro, Pageable pageable);

    Page<Processo> consultaJPANativa(ProcessoFiltro filtro, Pageable pageable);

}
