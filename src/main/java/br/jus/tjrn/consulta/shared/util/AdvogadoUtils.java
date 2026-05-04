package br.jus.tjrn.consulta.shared.util;

public class AdvogadoUtils {

    public static String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }

        String nomeValido = NomeUtils.normalizarNome(nome);

        if (nomeValido == null) {
            throw new IllegalArgumentException("Informe nome e Sobrenome do(a) Advogado(a)");
        }

        return nomeValido;
    }

    public static String normalizarOab(String oab) {
        if (oab == null || oab.isBlank()) {
            return null;
        }

        // remove pontos, traços e espaços
        String limpo = oab.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();

        if (limpo.length() < 5) {
            throw new IllegalArgumentException("Número da OAB deve conter pelo menos 5 caracteres");
        }

        return limpo;
    }

}
