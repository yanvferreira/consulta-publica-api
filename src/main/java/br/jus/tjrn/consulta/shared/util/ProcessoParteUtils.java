package br.jus.tjrn.consulta.shared.util;

public class ProcessoParteUtils {

    public static String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }

        String nomeValido = NomeUtils.normalizarNome(nome);

        if (nomeValido == null) {
            throw new IllegalArgumentException("Informe nome e Sobrenome da Parte");
        }

        return nomeValido;
    }

    public static String validarCpfCnpj(String cpfCnpj) {
        if (cpfCnpj == null || cpfCnpj.isBlank()) {
            return null;
        }

        // remove pontos, traços, barras e espaços
        String limpo = cpfCnpj.replaceAll("[^a-zA-Z0-9]", "");

        if (limpo.length() != 11 && limpo.length() != 14) {
            throw new IllegalArgumentException("CPF deve conter 11 caracteres e CNPJ deve conter 14 caracteres");
        }

        return limpo;
    }
}
