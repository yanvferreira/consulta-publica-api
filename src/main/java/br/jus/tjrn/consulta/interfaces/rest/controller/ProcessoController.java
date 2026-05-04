package br.jus.tjrn.consulta.interfaces.rest.controller;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.jus.tjrn.consulta.application.usecase.ConsultarProcessoUseCase;
import br.jus.tjrn.consulta.domain.model.Processo;
import br.jus.tjrn.consulta.domain.model.ProcessoFiltro;
import br.jus.tjrn.consulta.shared.response.ApiResponse;
import br.jus.tjrn.consulta.shared.response.PageResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/processos")
@RequiredArgsConstructor
public class ProcessoController {

    private final ConsultarProcessoUseCase consultarProcessoUseCase;

    @GetMapping
    public ApiResponse<PageResponse<Processo>> consultar(
        @RequestParam(required = false) String numero,
        @RequestParam(required = false) String numeroReferencia,
        @RequestParam(required = false) String nomeParte,
        @RequestParam(required = false) String nomeAdvogado,
        @RequestParam(required = false) String classeJudicial,
        @RequestParam(required = false) String cpfCnpj,
        @RequestParam(required = false) String oab,
        @RequestParam(required = false) String ufoab,
        @RequestParam(required = false) LocalDate dataInicio,
        @RequestParam(required = false) LocalDate dataFim,
        @PageableDefault(size = 20) Pageable pageable
    ) {

        ProcessoFiltro filtro = ProcessoFiltro.builder()
            .numero(numero)
            .numeroReferencia(numeroReferencia)
            .nomeParte(nomeParte)
            .nomeAdvogado(nomeAdvogado)
            .classeJudicial(classeJudicial)
            .cpfCnpj(cpfCnpj)
            .oab(oab)
            .ufOab(ufoab)
            .dataInicio(dataInicio)
            .dataFim(dataFim)
            .build();

        
        Page<Processo> page = consultarProcessoUseCase.executar(filtro, pageable);

        return ApiResponse.<PageResponse<Processo>>builder()
            .success(true)
            .message("Consulta realizada com sucesso")
            .data(new PageResponse<>(page))
            .build();
    }


}
