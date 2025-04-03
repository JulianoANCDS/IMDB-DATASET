package ed;

import java.util.List;

public class Ordenacao {
    private static int operacoes;

    public static void quickSort(List<Filme> filmes) {
        operacoes = 0;
        quickSort(filmes, 0, filmes.size() - 1);
    }

    private static void quickSort(List<Filme> filmes, int inicio, int fim) {
        if (inicio < fim) {
            int indicePivo = particionar(filmes, inicio, fim);
            quickSort(filmes, inicio, indicePivo - 1);
            quickSort(filmes, indicePivo + 1, fim);
        }
    }

    private static int particionar(List<Filme> filmes, int inicio, int fim) {
        String pivo = filmes.get(fim).getTitulo();
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            operacoes++;
            if (filmes.get(j).getTitulo().compareToIgnoreCase(pivo) <= 0) {
                i++;
                trocar(filmes, i, j);
            }
        }
        trocar(filmes, i + 1, fim);
        return i + 1;
    }

    public static void mergeSort(List<Filme> filmes) {
        operacoes = 0;
        mergeSort(filmes, 0, filmes.size() - 1);
    }

    private static void mergeSort(List<Filme> filmes, int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            mergeSort(filmes, inicio, meio);
            mergeSort(filmes, meio + 1, fim);
            merge(filmes, inicio, meio, fim);
        }
    }

    private static void merge(List<Filme> filmes, int inicio, int meio, int fim) {
        List<Filme> temp = new java.util.ArrayList<>(fim - inicio + 1);
        int i = inicio, j = meio + 1;

        while (i <= meio && j <= fim) {
            operacoes++;
            if (filmes.get(i).getAvaliacao() >= filmes.get(j).getAvaliacao()) {
                temp.add(filmes.get(i++));
            } else {
                temp.add(filmes.get(j++));
            }
        }

        while (i <= meio) temp.add(filmes.get(i++));
        while (j <= fim) temp.add(filmes.get(j++));

        for (int k = 0; k < temp.size(); k++) {
            filmes.set(inicio + k, temp.get(k));
        }
    }

    public static void heapSort(List<Filme> filmes) {
        operacoes = 0;
        int n = filmes.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(filmes, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            trocar(filmes, 0, i);
            heapify(filmes, i, 0);
        }
    }

    private static void heapify(List<Filme> filmes, int n, int i) {
        int maior = i;
        int esquerda = 2 * i + 1;
        int direita = 2 * i + 2;

        operacoes++;
        if (esquerda < n && filmes.get(esquerda).getVotos() > filmes.get(maior).getVotos()) {
            maior = esquerda;
        }

        operacoes++;
        if (direita < n && filmes.get(direita).getVotos() > filmes.get(maior).getVotos()) {
            maior = direita;
        }

        if (maior != i) {
            trocar(filmes, i, maior);
            heapify(filmes, n, maior);
        }
    }

    public static void insertionSort(List<Filme> filmes) {
        operacoes = 0;
        for (int i = 1; i < filmes.size(); i++) {
            Filme chave = filmes.get(i);
            int j = i - 1;

            while (j >= 0 && filmes.get(j).getAvaliacao() < chave.getAvaliacao()) {
                operacoes++;
                filmes.set(j + 1, filmes.get(j));
                j--;
            }
            filmes.set(j + 1, chave);
        }
    }

    public static void selectionSort(List<Filme> filmes) {
        operacoes = 0;
        for (int i = 0; i < filmes.size() - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < filmes.size(); j++) {
                operacoes++;
                if (filmes.get(j).getTitulo().compareToIgnoreCase(filmes.get(minIdx).getTitulo()) < 0) {
                    minIdx = j;
                }
            }
            trocar(filmes, i, minIdx);
        }
    }

    public static void bubbleSort(List<Filme> filmes) {
        operacoes = 0;
        int n = filmes.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                operacoes++;
                if (filmes.get(j).getAvaliacao() < filmes.get(j + 1).getAvaliacao()) {
                    trocar(filmes, j, j + 1);
                }
            }
        }
    }

    private static void trocar(List<Filme> filmes, int i, int j) {
        Filme temp = filmes.get(i);
        filmes.set(i, filmes.get(j));
        filmes.set(j, temp);
    }

    public static int getOperacoes() {
        return operacoes;
    }
}