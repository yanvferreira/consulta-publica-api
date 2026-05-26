package br.jus.tjrn.consulta.infrastructure.persistense.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import br.jus.tjrn.consulta.domain.model.Processo;
import br.jus.tjrn.consulta.domain.model.ProcessoFiltro;
import br.jus.tjrn.consulta.domain.repository.ProcessoRepository;
import br.jus.tjrn.consulta.infrastructure.persistense.entity.CabecalhoProcessoEntity;
import br.jus.tjrn.consulta.infrastructure.persistense.query.ProcessoQueryBuilder;
import br.jus.tjrn.consulta.infrastructure.persistense.query.QueryData;
import br.jus.tjrn.consulta.infrastructure.persistense.specification.ProcessoSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProcessoRepositoryImpl implements ProcessoRepository {

    private final ProcessoJpaRepository jpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final ProcessoQueryBuilder queryBuilder;

    @Override
    public Page<Processo> consultarCabecalhoProcesso(ProcessoFiltro filtro, Pageable pageable) {

        Specification<CabecalhoProcessoEntity> spec = ProcessoSpecification.filtro(filtro);

        return jpaRepository.findAll(
            spec,
            pageable
        ).map(this::toDomain);
    }

    @Override
    public Page<Processo> consultaJPANativa(ProcessoFiltro filtro, Pageable pageable) {
        
        Long total = this.contar(filtro);

        if (total == 0) {
            return new PageImpl<>(List.of(), pageable, 0);
        }        

        List<CabecalhoProcessoEntity> resultList = consultarProcessos(filtro, pageable);        

        return new PageImpl<>(
            resultList.stream()
                .map(this::toDomain)
                .toList(), 
            pageable, 
            total
        );

    }

    /**
     * Realiza o count na consulta
     * 
     * @param filtro
     * @return Long com a quantidade de registros
     */
    private Long contar(ProcessoFiltro filtro) {
        QueryData queryData = queryBuilder.build(filtro, true);
        Query countQuery = entityManager.createNativeQuery(queryData.getQuery());
        queryData.getParams().forEach(countQuery::setParameter);

        return (Long) countQuery.getSingleResult();
    }

    /**
     * Recupera os dados dos processos na consulta
     * 
     * @param filtro
     * @param pageable
     * @return List {@link CabecalhoProcessoEntity}
    */
    @SuppressWarnings("unchecked")
    private List<CabecalhoProcessoEntity> consultarProcessos (ProcessoFiltro filtro, Pageable pageable) {
        QueryData queryData = queryBuilder.build(filtro, false);

        Query query = entityManager.createNativeQuery(queryData.getQuery(), CabecalhoProcessoEntity.class);
        queryData.getParams().forEach(query::setParameter);

        // Paginação
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }

    /**
     * Converte uma entidade de cabeçalho de processo para um domínio.
     * 
     * @param entity
     * @return Processo
     */
    private Processo toDomain(CabecalhoProcessoEntity entity) {
        return Processo.builder()
                .idProcesso(entity.getIdProcesso())
                .numero(entity.getNumero())
                .classeJudicial(entity.getClasseJudicial())
                .siglaClasseJudicial(entity.getSiglaClasseJudicial())
                .assuntoPrincipal(entity.getAssuntoPrincipal())
                .nomeAutor(entity.getNomeAutor())
                .quantidadeAutores(entity.getQuantidadeAutores())
                .nomeReu(entity.getNomeReu())
                .quantidadeReus(entity.getQuantidadeReus())
                .ultimoMovimentoExterno(entity.getUltimoMovimentoExterno())
                .dataUltimoMovimentoExterno(entity.getDataUltimoMovimentoExterno())
                .build();    
    }


}
