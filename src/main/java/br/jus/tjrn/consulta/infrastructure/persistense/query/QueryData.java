package br.jus.tjrn.consulta.infrastructure.persistense.query;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryData {

    private String query;
    private Map<String, Object> params;

}
