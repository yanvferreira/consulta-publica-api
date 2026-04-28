package br.jus.tjrn.consulta.domain.repository;

import java.util.List;

import br.jus.tjrn.consulta.domain.model.Processo;
import br.jus.tjrn.consulta.domain.model.ProcessoFiltro;

public interface ProcessoRepository {
    
    List<Processo> consultar(ProcessoFiltro filtro);
}
