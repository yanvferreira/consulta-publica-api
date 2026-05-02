package br.jus.tjrn.consulta.infrastructure.persistense.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_cabecalho_processo")
@Getter @Setter
public class CabecalhoProcessoEntity {

    @Id
    @Column(name = "id_processo_trf")
    private Integer idProcesso;

    @Column(name = "nr_processo")
    private String numero;

    @Column(name = "cd_processo_status")
    private String status;

    // @Column(name = "dt_autuacao", columnDefinition = "TIMESTAMP")
    // private LocalDateTime dataAutuacao;
    
    // @Column(name = "nr_processo_origem")
    // private String numeroProcessoOrigem;

    @Column(name = "in_segredo_justica")
    private boolean segredoJustica;

    @Column(name = "cd_classe_judicial")
    private String codigoClasseJudicial;

    @Column(name = "ds_classe_judicial_sigla")
    private String siglaClasseJudicial;

    @Column(name = "ds_classe_judicial")
    private String classeJudicial;

    @Column(name = "ds_assunto_principal")
    private String assuntoPrincipal;

    @Column(name = "nm_pessoa_autor")
    private String nomeAutor;

    @Column(name = "nm_pessoa_reu")
    private String nomeReu;

    @Column(name = "qt_autor")
    private Integer quantidadeAutores;

    @Column(name = "qt_reu")
    private Integer quantidadeReus;

    @Column(name = "ds_ultimo_movimento_externo")
    private String ultimoMovimentoExterno;

    @Column(name = "dt_ultimo_movimento_externo", columnDefinition = "TIMESTAMP")
    private String dataUltimoMovimentoExterno;

}
