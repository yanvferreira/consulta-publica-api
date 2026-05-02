package br.jus.tjrn.consulta.infrastructure.persistense.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import br.jus.tjrn.consulta.domain.model.Processo;
import br.jus.tjrn.consulta.domain.model.ProcessoFiltro;
import br.jus.tjrn.consulta.domain.repository.ProcessoRepository;
import br.jus.tjrn.consulta.infrastructure.persistense.entity.CabecalhoProcessoEntity;
import br.jus.tjrn.consulta.infrastructure.persistense.specification.ProcessoSpecification;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProcessoRepositoryImpl implements ProcessoRepository {

    private final ProcessoJpaRepository jpaRepository;

    @Override
    public Page<Processo> consultarCabecalhoProcesso(ProcessoFiltro filtro, Pageable pageable) {

        Specification<CabecalhoProcessoEntity> spec = ProcessoSpecification.filtro(filtro);

        return jpaRepository.findAll(
            spec,
            pageable
        ).map(this::toDomain);
    }

    @Override
    public Page<Processo> consultaJPANativa(ProcessoFiltro filtro, Pageable pageable) {

        return jpaRepository.consultar(
            filtro.getNumero(),
            filtro.getClasseJudicial(),
            filtro.getNumeroReferencia(),
            filtro.getCpfCnpj(),
            filtro.getNomeParte(),
            filtro.getNomeAdvogado(),
            filtro.getDataInicio(),
            filtro.getDataFim(),
            pageable
        ).map(this::toDomain);
    }

    /**
     * Converte uma entidade de cabeçalho de processo para um domínio.
     * 
     * @param entity
     * @return Processo
     */
    private Processo toDomain(CabecalhoProcessoEntity entity) {
        return Processo.builder()
                .idProcesso(entity.getIdProcesso())
                .numero(entity.getNumero())
                .classeJudicial(entity.getClasseJudicial())
                .siglaClasseJudicial(entity.getSiglaClasseJudicial())
                .assuntoPrincipal(entity.getAssuntoPrincipal())
                .nomeAutor(entity.getNomeAutor())
                .quantidadeAutores(entity.getQuantidadeAutores())
                .nomeReu(entity.getNomeReu())
                .quantidadeReus(entity.getQuantidadeReus())
                .ultimoMovimentoExterno(entity.getUltimoMovimentoExterno())
                .dataUltimoMovimentoExterno(entity.getDataUltimoMovimentoExterno())
                .build();    
    }


}
