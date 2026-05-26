package br.jus.tjrn.consulta.infrastructure.persistense.query;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.jus.tjrn.consulta.domain.model.ProcessoFiltro;

@Component
public class ProcessoQueryBuilder {

    public QueryData build(ProcessoFiltro filtro, boolean isCount) {
        StringBuilder query = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (isCount) {
            query.append("""
                SELECT COUNT(DISTINCT tcp.id_processo_trf)
            """);
        } else {
            query.append("""
                SELECT DISTINCT tcp.id_processo_trf, tcp.nr_processo, tcp.cd_processo_status, tcp.in_segredo_justica, 
                    tcp.ds_classe_judicial_sigla, tcp.cd_classe_judicial, tcp.ds_classe_judicial, tcp.ds_assunto_principal, 
                    tcp.nm_pessoa_autor, tcp.nm_pessoa_reu, tcp.qt_autor, tcp.qt_reu, 
                    tcp.ds_ultimo_movimento_externo, tcp.dt_ultimo_movimento_externo
            """);
        }

        query.append("""
            FROM tb_cabecalho_processo tcp 
        """);

        if (filtro.getNomeParte() != null) {
            query.append("""
                INNER JOIN tb_processo_parte tpp
                    ON tpp.id_processo_trf = tcp.id_processo_trf

                INNER JOIN tb_pess_doc_identificacao tpdi
                    ON tpdi.id_pessoa = tpp.id_pessoa 
            """);
        }

        if (filtro.getNomeAdvogado() != null || filtro.getOab() != null) {
            query.append("""
                INNER JOIN tb_processo_parte tpp_adv
                    ON tpp_adv.id_processo_trf = tcp.id_processo_trf

                INNER JOIN tb_proc_parte_represntante tppr
                    ON tppr.id_processo_parte = tpp_adv.id_processo_parte

                INNER JOIN tb_pess_doc_identificacao tpdi_adv
                    ON tpdi_adv.id_pessoa = tppr.id_representante
            """);            
        }

        if (filtro.getOab() != null && filtro.getUfOab() != null) {
            query.append("""
                INNER JOIN tb_estado te
                    ON tpdi_adv.id_estado_expedidor = te.id_estado
            """); 
        }

        query.append("""
            WHERE tcp.cd_processo_status = 'D'
            AND tcp.in_segredo_justica = false
        """);

        if (filtro.getNumero() != null) {
            query.append(" AND tcp.nr_processo = :numero ");
            params.put("numero", filtro.getNumero());
        }

        if (filtro.getClasseJudicial() != null) {
            query.append(" AND tcp.ds_classe_judicial = :classeJudicial ");
            params.put("classeJudicial", filtro.getClasseJudicial());
        }

        if (filtro.getNomeParte() != null) {
            query.append("""
                AND tpp.in_parte_principal = true
                AND tpp.in_situacao = 'A'
                AND tpp.in_participacao IN ('A', 'P')
                AND tpdi.in_ativo = true
                AND tpdi.ds_nome_pessoa ILIKE :nomeParte
            """);

            params.put("nomeParte", filtro.getNomeParte());
        }

        if (filtro.getNomeAdvogado() != null) {
            query.append("""
                AND tpp_adv.in_parte_principal = false
                AND tpp_adv.in_situacao = 'A'
                AND tppr.id_tipo_representante = 7
                AND tppr.in_situacao = 'A'
                AND tpdi_adv.in_ativo = true
                AND tpdi_adv.ds_nome_pessoa = :nomeAdvogado
            """);

            params.put("nomeAdvogado", filtro.getNomeAdvogado());
        }
        
        if (filtro.getOab() != null) {
            query.append("""
                AND tpdi_adv.cd_tp_documento_identificacao = 'OAB'
                AND tpdi_adv.nr_documento_identificacao = :oab
            """);

            params.put("oab", filtro.getOab());
        }

        if (filtro.getOab() != null && filtro.getUfOab() != null) {
            query.append(" AND te.cd_estado = :estado ");
            params.put("estado", filtro.getUfOab());
        }

        return QueryData.builder()
            .query(query.toString())
            .params(params)
            .build();
    }

}
