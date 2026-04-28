package br.jus.tjrn.consulta.infrastructure.persistense.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Processo> consultar(ProcessoFiltro filtro, Pageable pageable) {

        return repository.consultar(
            filtro.getNumero(),
            filtro.getNumeroReferencia(),
            filtro.getCpfCnpj(),
            filtro.getClasseJudicial(),
            filtro.getNomeParte(),
            filtro.getNomeAdvogado(),
            filtro.getDataInicio(),
            filtro.getDataFim(),
            pageable
        ).map(this::toDomain);
    }

    private Processo toDomain(ProcessoEntity entity) {
        return Processo.builder()
                .numero(entity.getNumero())
                .build();    
    }


}
