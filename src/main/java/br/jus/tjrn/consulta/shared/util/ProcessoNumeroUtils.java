package br.jus.tjrn.consulta.shared.util;

public class ProcessoNumeroUtils {

    private static final int TAMANHO = 20;

    public static String normalizar(String numero) {
        if (numero == null || numero.isBlank()) {
            return null;
        }

        // remove tudo que não é alfanumérico
        String limpo = numero.replaceAll("[^a-zA-Z0-9]", "");

        if (!limpo.matches("\\d+")) {
            throw new IllegalArgumentException("Número do processo deve conter apenas dígitos");
        }

        if (limpo.length() != TAMANHO) {
            throw new IllegalArgumentException("Número do processo deve conter 20 dígitos");
        }

        return aplicarMascara(limpo);
    }

    private static String aplicarMascara(String numero) {
        return String.format("%s-%s.%s.%s.%s.%s",
            numero.substring(0, 7),
            numero.substring(7, 9),
            numero.substring(9, 13),
            numero.substring(13, 14),
            numero.substring(14, 16),
            numero.substring(16,20)
        );
    }

}
