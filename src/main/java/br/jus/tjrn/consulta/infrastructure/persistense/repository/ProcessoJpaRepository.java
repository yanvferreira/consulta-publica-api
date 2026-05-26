package br.jus.tjrn.consulta.infrastructure.persistense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.jus.tjrn.consulta.infrastructure.persistense.entity.CabecalhoProcessoEntity;

public interface ProcessoJpaRepository extends JpaRepository<CabecalhoProcessoEntity, Integer>, JpaSpecificationExecutor<CabecalhoProcessoEntity> {

}
