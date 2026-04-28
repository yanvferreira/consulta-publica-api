package br.jus.tjrn.consulta.infrastructure.persistense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.jus.tjrn.consulta.infrastructure.persistense.entity.ProcessoEntity;

public interface ProcessoJpaRepository extends JpaRepository<ProcessoEntity, Integer> {

}
