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
        SELECT tcp.id_processo_trf, tcp.nr_processo, tcp.cd_processo_status, tcp.in_segredo_justica, 
                tcp.ds_classe_judicial_sigla, tcp.cd_classe_judicial, tcp.ds_classe_judicial, tcp.ds_assunto_principal, 
                tcp.nm_pessoa_autor, tcp.nm_pessoa_reu, tcp.qt_autor, tcp.qt_reu, 
                tcp.ds_ultimo_movimento_externo, tcp.dt_ultimo_movimento_externo
            FROM tb_cabecalho_processo tcp 
            WHERE tcp.cd_processo_status = 'D' AND tcp.in_segredo_justica = false
            AND (:numero IS NULL OR tcp.nr_processo = :numero) 
            AND (:classeJudicial IS NULL OR tcp.ds_classe_judicial = :classeJudicial) 
            AND (:nomeParte IS NULL OR EXISTS (
                SELECT 1 FROM tb_processo_parte tpp 
                INNER JOIN tb_usuario_login tul on tpp.id_pessoa = tul.id_usuario 
                WHERE tpp.id_processo_trf = tcp.id_processo_trf
                AND tpp.in_parte_principal = true
                AND tpp.in_situacao = 'A'
                AND tpp.in_participacao IN ('A', 'P')
                AND tul.ds_nome ILIKE CONCAT(:nomeParte, '%')
            ))
        """,
        countQuery = """
        SELECT COUNT(tcp.id_processo_trf)
            FROM tb_cabecalho_processo tcp 
            WHERE tcp.cd_processo_status = 'D' AND tcp.in_segredo_justica = false
            AND (:numero IS NULL OR tcp.nr_processo = :numero) 
            AND (:classeJudicial IS NULL OR tcp.ds_classe_judicial = :classeJudicial) 
            AND (:nomeParte IS NULL OR EXISTS (
                SELECT 1 FROM tb_processo_parte tpp 
                INNER JOIN tb_usuario_login tul on tpp.id_pessoa = tul.id_usuario 
                WHERE tpp.id_processo_trf = tcp.id_processo_trf
                AND tpp.in_parte_principal = true
                AND tpp.in_situacao = 'A'
                AND tpp.in_participacao IN ('A', 'P')
                AND tul.ds_nome ILIKE CONCAT(:nomeParte, '%')
            ))
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
