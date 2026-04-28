package br.jus.tjrn.consulta.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import br.jus.tjrn.consulta.domain.model.Processo;
import br.jus.tjrn.consulta.domain.model.ProcessoFiltro;
import br.jus.tjrn.consulta.domain.repository.ProcessoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultarProcessoUseCase {

    private final ProcessoRepository repository;

    public List<Processo> executar(ProcessoFiltro filtro) {
        validarFiltro(filtro);
        return repository.consultar(filtro);
    }

    private void validarFiltro(ProcessoFiltro filtro) {
        if (filtro.getNomeAdvogado() != null) {
            String[] partes = filtro.getNomeAdvogado().trim().split(" ");

            if (partes.length < 2) {
                throw new IllegalArgumentException("Informe nome e sobrenome do advogado");
            }

        }
    }

}
