package ed;

public class ArvoreAVL {
    private class No {
        Filme filme;
        No esquerda, direita;
        int altura;

        No(Filme filme) {
            this.filme = filme;
            this.altura = 1;
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
        } else {
            return no;
        }

        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));

        int balance = getBalance(no);

        if (balance > 1 && filme.getTitulo().compareToIgnoreCase(no.esquerda.filme.getTitulo()) < 0) {
            return rotacionarDireita(no);
        }

        if (balance < -1 && filme.getTitulo().compareToIgnoreCase(no.direita.filme.getTitulo()) > 0) {
            return rotacionarEsquerda(no);
        }

        if (balance > 1 && filme.getTitulo().compareToIgnoreCase(no.esquerda.filme.getTitulo()) > 0) {
            no.esquerda = rotacionarEsquerda(no.esquerda);
            return rotacionarDireita(no);
        }

        if (balance < -1 && filme.getTitulo().compareToIgnoreCase(no.direita.filme.getTitulo()) < 0) {
            no.direita = rotacionarDireita(no.direita);
            return rotacionarEsquerda(no);
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

    private int altura(No no) {
        return no == null ? 0 : no.altura;
    }

    private int getBalance(No no) {
        return no == null ? 0 : altura(no.esquerda) - altura(no.direita);
    }

    private No rotacionarDireita(No y) {
        No x = y.esquerda;
        No T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    private No rotacionarEsquerda(No x) {
        No y = x.direita;
        No T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    public int getComparacoes() {
        return comparacoes;
    }

    public int altura() {
        return altura(raiz);
    }
}