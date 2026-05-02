package br.jus.tjrn.consulta.infrastructure.persistense.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.jus.tjrn.consulta.infrastructure.persistense.entity.CabecalhoProcessoEntity;

public interface ProcessoJpaRepository extends JpaRepository<CabecalhoProcessoEntity, Integer>, JpaSpecificationExecutor<CabecalhoProcessoEntity> {

    @Query(value = """
        SELECT DISTINCT tcp.* 
            FROM tb_cabecalho_processo tcp 
            INNER JOIN tb_processo_parte tpp ON tpp.id_processo = tcp.id_processo 
            INNER JOIN tb_pessoa tp ON tp.id_pessoa = tpp.id_pessoa 
            WHERE tcp.status = 'D' AND tcp.segredoJustica = false 
            AND (:numero IS NULL OR tcp.numero = :numero) 
            AND (:classeJudicial IS NULL OR tcp.classeJudicial = :classeJudicial) 
            AND (:nomeParte IS NULL OR EXISTS (
                SELECT 1 FROM tb_processo_parte tpp2 JOIN tb_pessoa tp2 ON tp2.id_pessoa = tpp2.id_pessoa 
                WHERE tpp2.id_processo = tcp.id_processo 
                AND tpp2.in_ativo = true
                AND tpp2.tipo_parte IN ('A', 'P')
                AND UPPER(tp2.nome) LIKE UPPER(CONCAT('%', :nomeParte, '%'))
            ))
            AND (:nomeAdvogado IS NULL OR EXISTS (
                SELECT 1 FROM tb_processo_advogado tpa JOIN tb_pessoa tp3 ON tp3.id_pessoa = tpa.id_pessoa 
                WHERE tpa.id_processo = tcp.id_processo 
                AND UPPER(tp3.nome) LIKE UPPER(CONCAT('%', :nomeAdvogado, '%'))
            ))
            AND (:cpfCnpj IS NULL OR EXISTS (
                SELECT 1 FROM tb_processo_parte tpp3 JOIN tb_pessoa tp4 ON tp4.id_pessoa = tpp3.id_pessoa 
                WHERE tpp3.id_processo = tcp.id_processo 
                AND tpp3.in_ativo = true
                AND tpp3.tipo_parte IN ('A', 'P')
                AND (tp4.cpf = :cpfCnpj OR tp4.cnpj = :cpfCnpj)
            ))
            AND (:dataInicio IS NULL OR tcp.dataDistribuicao >= :dataInicio)
            AND (:dataFim IS NULL OR tcp.dataDistribuicao <= :dataFim)
        """,
        countQuery = """
        SELECT COUNT(DISTINCT tcp.id_processo)
            FROM tb_cabecalho_processo tcp 
            INNER JOIN tb_processo_parte tpp ON tpp.id_processo = tcp.id_processo 
            INNER JOIN tb_pessoa tp ON tp.id_pessoa = tpp.id_pessoa 
            WHERE tcp.status = 'D' AND tcp.segredoJustica = false 
            AND (:numero IS NULL OR tcp.numero = :numero) 
            AND (:classeJudicial IS NULL OR tcp.classeJudicial = :classeJudicial) 
            AND (:nomeParte IS NULL OR EXISTS (
                SELECT 1 FROM tb_processo_parte tpp2 JOIN tb_pessoa tp2 ON tp2.id_pessoa = tpp2.id_pessoa 
                WHERE tpp2.id_processo = tcp.id_processo 
                AND tpp2.in_ativo = true
                AND tpp2.tipo_parte IN ('A', 'P')
                AND UPPER(tp2.nome) LIKE UPPER(CONCAT('%', :nomeParte, '%'))
            ))
            AND (:nomeAdvogado IS NULL OR EXISTS (
                SELECT 1 FROM tb_processo_advogado tpa JOIN tb_pessoa tp3 ON tp3.id_pessoa = tpa.id_pessoa 
                WHERE tpa.id_processo = tcp.id_processo 
                AND UPPER(tp3.nome) LIKE UPPER(CONCAT('%', :nomeAdvogado, '%'))
            ))
            AND (:cpfCnpj IS NULL OR EXISTS (
                SELECT 1 FROM tb_processo_parte tpp3 JOIN tb_pessoa tp4 ON tp4.id_pessoa = tpp3.id_pessoa 
                WHERE tpp3.id_processo = tcp.id_processo 
                AND tpp3.in_ativo = true
                AND tpp3.tipo_parte IN ('A', 'P')
                AND (tp4.cpf = :cpfCnpj OR tp4.cnpj = :cpfCnpj)
            ))
            AND (:dataInicio IS NULL OR tcp.dataDistribuicao >= :dataInicio)
            AND (:dataFim IS NULL OR tcp.dataDistribuicao <= :dataFim)
        """,
        nativeQuery = true
    )
    Page<CabecalhoProcessoEntity> consultar(
        @Param("numero") String numero,
        @Param("classeJudicial") String classeJudicial,
        @Param("numeroReferencia") String numeroReferencia,
        @Param("cpfCnpj") String cpfCnpj,
        @Param("nomeParte") String nomeParte,
        @Param("nomeAdvogado") String nomeAdvogado,
        @Param("dataInicio") LocalDate dataInicio,
        @Param("dataFim") LocalDate dataFim,
        Pageable pageable
    );
}
