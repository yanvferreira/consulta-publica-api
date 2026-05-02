package br.jus.tjrn.consulta.infrastructure.persistense.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.jus.tjrn.consulta.domain.model.Processo;
import br.jus.tjrn.consulta.domain.model.ProcessoFiltro;
import br.jus.tjrn.consulta.domain.repository.ProcessoRepository;
import br.jus.tjrn.consulta.infrastructure.persistense.entity.CabecalhoProcessoEntity;
import br.jus.tjrn.consulta.infrastructure.persistense.entity.ProcessoEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProcessoRepositoryImpl implements ProcessoRepository {

    private final ProcessoJpaRepository jpaRepository;

    @Override
    public Page<Processo> consultar(ProcessoFiltro filtro, Pageable pageable) {

        return jpaRepository.consultar(
            filtro.getNumero(),
            /* filtro.getNumeroReferencia(),
            filtro.getCpfCnpj(),
            filtro.getClasseJudicial(),
            filtro.getNomeParte(),
            filtro.getNomeAdvogado(),
            filtro.getDataInicio(),
            filtro.getDataFim(), */
            pageable
        ).map(this::toDomain);
    }

    private Processo toDomain(CabecalhoProcessoEntity entity) {
        return Processo.builder()
                .idProcesso(entity.getIdProcesso())
                .numero(entity.getNumero())
                .build();    
    }


}
