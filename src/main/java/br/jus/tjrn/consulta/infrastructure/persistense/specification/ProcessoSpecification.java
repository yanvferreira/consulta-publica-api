package br.jus.tjrn.consulta.infrastructure.persistense.specification;

import org.springframework.data.jpa.domain.Specification;

import br.jus.tjrn.consulta.domain.model.ProcessoFiltro;
import br.jus.tjrn.consulta.infrastructure.persistense.entity.CabecalhoProcessoEntity;
import jakarta.persistence.criteria.Predicate;

public class ProcessoSpecification {

    /**
     * Usar para consultas com filtros apenas na Entidade CabecalhoProcessoEntity.
     * Para filtros com relacionamentos, utilizar Native Query para performance otimizada.
     * 
     * @param filtro {@link ProcessoFiltro}
     * @return {@link Specification}
     * @see CabecalhoProcessoEntity
     */
    public static Specification<CabecalhoProcessoEntity> filtro(ProcessoFiltro filtro) {

        return (root, query, criteriaBuilder) -> {
            Predicate predicates = criteriaBuilder.conjunction();

            predicates = criteriaBuilder.and(predicates, 
                criteriaBuilder.equal(root.get("status"), "D"),
                criteriaBuilder.isFalse(root.get("segredoJustica"))
            );

            if (filtro.getNumero() != null) {
                predicates = criteriaBuilder.and(
                    predicates, criteriaBuilder.equal(root.get("numero"), 
                    filtro.getNumero())
                );
            }

            if (filtro.getClasseJudicial() != null) {
                predicates = criteriaBuilder.and(
                    predicates, criteriaBuilder.equal(root.get("classeJudicial"), 
                    filtro.getClasseJudicial())
                );
            }

            // if (filtro.getNomeParte() != null) {
            //     Join<CabecalhoProcessoEntity, ProcessoParteEntity> partesJoin = root.join("processoPartes", JoinType.INNER);
            //     Join<ProcessoParteEntity, PessoaEntity> pessoaJoin = partesJoin.join("pessoa", JoinType.INNER);

            //     predicates = criteriaBuilder.and(
            //         predicates, 
            //         criteriaBuilder.like(
            //             criteriaBuilder.upper(pessoaJoin.get("nome")), 
            //             "%" + filtro.getNomeParte().toUpperCase() + "%"
            //         )
            //     );
            // }

            return predicates;
        };
    }

}
