package ed;

import java.util.List;

public class Busca {
    private static int comparacoes;

    public static Filme pesquisaSequencial(List<Filme> filmes, String titulo) {
        comparacoes = 0;
        for (Filme filme : filmes) {
            comparacoes++;
            if (filme.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                return filme;
            }
        }
        return null;
    }

    public static Filme pesquisaBinaria(List<Filme> filmes, String titulo) {
        comparacoes = 0;
        int esquerda = 0;
        int direita = filmes.size() - 1;

        while (esquerda <= direita) {
            comparacoes++;
            int meio = esquerda + (direita - esquerda) / 2;
            int comparacao = titulo.compareToIgnoreCase(filmes.get(meio).getTitulo());

            if (comparacao == 0) {
                return filmes.get(meio);
            } else if (comparacao < 0) {
                direita = meio - 1;
            } else {
                esquerda = meio + 1;
            }
        }
        return null;
    }

    public static int getComparacoes() {
        return comparacoes;
    }
}