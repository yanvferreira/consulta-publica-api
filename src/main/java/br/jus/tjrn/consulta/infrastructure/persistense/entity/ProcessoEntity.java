package br.jus.tjrn.consulta.infrastructure.persistense.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_processo")
@Getter @Setter
public class ProcessoEntity {

    @Id
    @Column(name = "id_processo")
    private Integer id;

    @Column(name = "nr_processo")
    private String numero;
    
    @Column(name = "nr_processo_origem")
    private String numeroProcessoOrigem;
    
    @Column(name = "ds_complemento")
    private String descricaoComplemento;
}
