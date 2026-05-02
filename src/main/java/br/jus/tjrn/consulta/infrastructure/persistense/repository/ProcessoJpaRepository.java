package br.jus.tjrn.consulta.infrastructure.persistense.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.jus.tjrn.consulta.infrastructure.persistense.entity.CabecalhoProcessoEntity;
import br.jus.tjrn.consulta.infrastructure.persistense.entity.ProcessoEntity;

public interface ProcessoJpaRepository extends JpaRepository<ProcessoEntity, Integer> {

    @Query(
        value = "SELECT cp FROM CabecalhoProcessoEntity cp " +
            "WHERE (:numero IS NULL OR cp.numero = :numero) " +
            "AND cp.status = 'D' AND cp.segredoJustica = false "
        )
    Page<CabecalhoProcessoEntity> consultar(
        @Param("numero") String numero,
        /* @Param("numeroReferencia") String numeroReferencia,
        @Param("cpfCnpj") String cpfCnpj,
        @Param("classeJudicial") String classeJudicial,
        @Param("nomeParte") String nomeParte,
        @Param("nomeAdvogado") String nomeAdvogado,
        @Param("dataInicio") LocalDate dataInicio,
        @Param("dataFim") LocalDate dataFim, */
        Pageable pageable
    );
}
