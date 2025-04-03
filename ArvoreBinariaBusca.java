package ed;

public class ArvoreBinariaBusca {
    private class No {
        Filme filme;
        No esquerda, direita;

        No(Filme filme) {
            this.filme = filme;
        }
    }

    private No raiz;
    private int comparacoes;

    public void inserir(Filme filme) {
        raiz = inserir(raiz, filme);
    }

    private No inserir(No no, Filme filme) {
        if (no == null) {
            return new No(filme);
        }

        int cmp = filme.getTitulo().compareToIgnoreCase(no.filme.getTitulo());
        if (cmp < 0) {
            no.esquerda = inserir(no.esquerda, filme);
        } else if (cmp > 0) {
            no.direita = inserir(no.direita, filme);
        }

        return no;
    }

    public Filme buscar(String titulo) {
        comparacoes = 0;
        return buscar(raiz, titulo);
    }

    private Filme buscar(No no, String titulo) {
        if (no == null) {
            return null;
        }

        comparacoes++;
        int cmp = titulo.compareToIgnoreCase(no.filme.getTitulo());
        if (cmp == 0) {
            return no.filme;
        } else if (cmp < 0) {
            return buscar(no.esquerda, titulo);
        } else {
            return buscar(no.direita, titulo);
        }
    }

    public int getComparacoes() {
        return comparacoes;
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(No no) {
        if (no == null) {
            return 0;
        }
        return 1 + Math.max(altura(no.esquerda), altura(no.direita));
    }
}