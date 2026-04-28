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
            SELECT * FROM tb_processo p
            WHERE
                (:numero IS NULL OR p.numero = :numero)
            AND (:numeroReferencia IS NULL OR p.numero_referencia = :numeroReferencia)
            AND (:cpfCnpj IS NULL OR p.cpf_cnpj = :cpfCnpj)
            AND (:classeJudicial IS NULL OR LOWER(p.classe_judicial) LIKE LOWER(CONCAT('%', :classeJudicial, '%')))
            AND (:nomeParte IS NULL OR LOWER(p.nome_parte) LIKE LOWER(CONCAT('%', :nomeParte, '%')))
            AND (:nomeAdvogado IS NULL OR LOWER(p.nome_advogado) LIKE LOWER(CONCAT('%', :nomeAdvogado, '%')))
            AND (:dataInicio IS NULL OR p.data_autuacao >= :dataInicio)
            AND (:dataFim IS NULL OR p.data_autuacao <= :dataFim)
            """,
            countQuery = """
                SELECT COUNT(*) FROM processo p
                WHERE
                    (:numero IS NULL OR p.numero = :numero)
                AND (:numeroReferencia IS NULL OR p.numero_referencia = :numeroReferencia)
                AND (:cpfCnpj IS NULL OR p.cpf_cnpj = :cpfCnpj)
                """,
            nativeQuery = true
        )
        Page<ProcessoEntity> consultar(
            @Param("numero") String numero,
            @Param("numeroReferencia") String numeroReferencia,
            @Param("cpfCnpj") String cpfCnpj,
            @Param("classeJudicial") String classeJudicial,
            @Param("nomeParte") String nomeParte,
            @Param("nomeAdvogado") String nomeAdvogado,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            Pageable pageable
        );
}
