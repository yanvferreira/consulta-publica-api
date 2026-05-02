package br.jus.tjrn.consulta.infrastructure.persistense.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.jus.tjrn.consulta.infrastructure.persistense.entity.ProcessoEntity;

public interface ProcessoJpaRepository extends JpaRepository<ProcessoEntity, Integer> {

    @Query(value = """
            SELECT p.* FROM tb_processo p
            INNER JOIN tb_processo_trf tpt
                ON p.id_processo = tpt.id_processo_trf
            WHERE
                (:numero IS NULL OR p.nr_processo = :numero)
            AND tpt.cd_processo_status = 'D'
            AND tpt.in_segredo_justica = false
            """,
            countQuery = """
                SELECT COUNT(*) FROM processo p
                INNER JOIN tb_processo_trf tpt
                    ON p.id_processo = tpt.id_processo_trf
                WHERE
                    (:numero IS NULL OR p.nr_processo = :numero)
                AND tpt.cd_processo_status = 'D'
                AND tpt.in_segredo_justica = false
                """,
            nativeQuery = true
        )
    Page<ProcessoEntity> consultar(
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
