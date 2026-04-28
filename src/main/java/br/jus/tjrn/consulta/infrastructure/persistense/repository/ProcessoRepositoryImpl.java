package br.jus.tjrn.consulta.infrastructure.persistense.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.jus.tjrn.consulta.domain.model.Processo;
import br.jus.tjrn.consulta.domain.model.ProcessoFiltro;
import br.jus.tjrn.consulta.domain.repository.ProcessoRepository;
import br.jus.tjrn.consulta.infrastructure.persistense.entity.ProcessoEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProcessoRepositoryImpl implements ProcessoRepository {

    private final ProcessoJpaRepository repository;

    @Override
    public List<Processo> consultar(ProcessoFiltro filtro) {
        return repository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    private Processo toDomain(ProcessoEntity entity) {
        return Processo.builder()
                .numero(entity.getNumero())
                .build();    
    }


}
