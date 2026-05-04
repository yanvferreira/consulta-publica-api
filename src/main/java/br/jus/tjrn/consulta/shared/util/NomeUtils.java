package br.jus.tjrn.consulta.shared.util;

import java.text.Normalizer;

public class NomeUtils {

    public static String normalizarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }

        String[] partes = nome.trim().split(" ");

        if (partes.length < 2) {
            return null;
        }

        // remove acentos e caracteres especiais
        String temp = Normalizer.normalize(nome, Normalizer.Form.NFD);
        String limpo = temp.replaceAll("\\p{M}", "")
            .replaceAll("[^a-zA-Z0-9 ]", "")
            .replaceAll("\\s+", " ")
            .toUpperCase();

        return limpo;
    }

}
