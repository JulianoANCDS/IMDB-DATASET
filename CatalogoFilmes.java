package ed;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CatalogoFilmes {
    private List<Filme> filmes;
    private ArvoreBinariaBusca arvoreTitulos;
    private ArvoreAVL arvoreAVL;

    public CatalogoFilmes() {
        this.filmes = new ArrayList<>();
        this.arvoreTitulos = new ArvoreBinariaBusca();
        this.arvoreAVL = new ArvoreAVL();
    }

    public void carregarDados(String caminhoBasico, String caminhoAvaliacoes) {
        Map<String, Double> avaliacoes = new HashMap<>();
        Map<String, Integer> votos = new HashMap<>();

        // Carrega avaliações
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoAvaliacoes))) {
            br.readLine(); // Pular cabeçalho
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split("\t");
                if (partes.length >= 3) {
                    avaliacoes.put(partes[0], Double.parseDouble(partes[1]));
                    votos.put(partes[0], Integer.parseInt(partes[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de avaliações: " + e.getMessage());
        }

        // Carrega filmes básicos
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoBasico))) {
            br.readLine(); // Pular cabeçalho
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split("\t");
                if (partes.length >= 9 && "movie".equals(partes[1]) && 
                    avaliacoes.containsKey(partes[0]) && 
                    votos.get(partes[0]) >= 1000) { // Filtra filmes com pelo menos 1000 votos
                    
                    String[] generos = partes[8].equals("\\N") ? new String[0] : partes[8].split(",");
                    Filme filme = new Filme(
                        partes[0], 
                        partes[2], 
                        partes[5].equals("\\N") ? -1 : Integer.parseInt(partes[5]), 
                        generos,
                        avaliacoes.get(partes[0]), 
                        votos.get(partes[0])
                    );
                    filmes.add(filme);
                    arvoreTitulos.inserir(filme);
                    arvoreAVL.inserir(filme);
                }
            }
            System.out.println("Carregados " + filmes.size() + " filmes com pelo menos 1000 votos.");
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo básico: " + e.getMessage());
        }
    }

    public Filme buscarPesquisaSequencial(String titulo) {
        return Busca.pesquisaSequencial(filmes, titulo);
    }

    public Filme buscarPesquisaBinaria(String titulo) {
        List<Filme> copia = new ArrayList<>(filmes);
        Ordenacao.quickSort(copia);
        return Busca.pesquisaBinaria(copia, titulo);
    }

    public Filme buscarBST(String titulo) {
        return arvoreTitulos.buscar(titulo);
    }

    public Filme buscarAVL(String titulo) {
        return arvoreAVL.buscar(titulo);
    }

    public List<Filme> recomendarTopFilmes(int quantidade) {
        List<Filme> copia = new ArrayList<>(filmes);
        Ordenacao.mergeSort(copia);
        return copia.stream()
                  .limit(quantidade)
                  .collect(Collectors.toList());
    }

    public void compararAlgoritmosBusca(String titulo) {
        System.out.println("\n=== COMPARAÇÃO DE ALGORITMOS DE BUSCA ===");
        System.out.printf("Buscando: '%s'\n", titulo);
        System.out.println("Método\t\tTempo (ms)\tComparações");
        System.out.println("---------------------------------------");
        
        testarBusca("Sequencial", () -> buscarPesquisaSequencial(titulo));
        testarBusca("Binária", () -> buscarPesquisaBinaria(titulo));
        testarBusca("BST", () -> buscarBST(titulo));
        testarBusca("AVL", () -> buscarAVL(titulo));
    }

    public void compararAlgoritmosOrdenacao() {
        System.out.println("\n=== COMPARAÇÃO DE ALGORITMOS DE ORDENAÇÃO ===");
        System.out.printf("Ordenando %,d filmes (mín. 1,000 votos)...\n", filmes.size());
        System.out.println("Algoritmo\t\tTempo (ms)\tOperações\tComplexidade");
        System.out.println("---------------------------------------------------------------");
        
        testarOrdenacao("QuickSort (Título)", () -> Ordenacao.quickSort(new ArrayList<>(filmes)));
        testarOrdenacao("MergeSort (Avaliação)", () -> Ordenacao.mergeSort(new ArrayList<>(filmes)));
        testarOrdenacao("HeapSort (Votos)", () -> Ordenacao.heapSort(new ArrayList<>(filmes)));
        
        List<Filme> subset = new ArrayList<>(filmes.subList(0, Math.min(1000, filmes.size())));
        testarOrdenacao("InsertionSort (1k)", () -> Ordenacao.insertionSort(subset));
        testarOrdenacao("SelectionSort (1k)", () -> Ordenacao.selectionSort(subset));
        testarOrdenacao("BubbleSort (1k)", () -> Ordenacao.bubbleSort(subset));
    }

    public void compararEstruturas() {
        System.out.println("\n=== COMPARAÇÃO DE ESTRUTURAS ===");
        System.out.println("BST vs AVL");
        System.out.println("Altura BST: " + arvoreTitulos.altura());
        System.out.println("Altura AVL: " + arvoreAVL.altura());
        System.out.println("\nQuanto menor a altura, mais eficiente são as buscas.");
    }

    private void testarBusca(String nome, Runnable metodo) {
        long inicio = System.currentTimeMillis();
        metodo.run();
        long tempo = System.currentTimeMillis() - inicio;
        
        int comparacoes = 0;
        if (nome.equals("Sequencial")) {
            comparacoes = Busca.getComparacoes();
        } else if (nome.equals("Binária")) {
            comparacoes = Busca.getComparacoes();
        } else if (nome.equals("BST")) {
            comparacoes = arvoreTitulos.getComparacoes();
        } else if (nome.equals("AVL")) {
            comparacoes = arvoreAVL.getComparacoes();
        }
        
        System.out.printf("%-12s\t%6d\t\t%9d\n", nome, tempo, comparacoes);
    }

    private void testarOrdenacao(String nome, Runnable metodo) {
        long inicio = System.currentTimeMillis();
        metodo.run();
        long tempo = System.currentTimeMillis() - inicio;
        
        String complexidade = "";
        if (nome.contains("QuickSort") || nome.contains("MergeSort") || nome.contains("HeapSort")) {
            complexidade = "O(n log n)";
        } else {
            complexidade = "O(n²)";
        }
        
        System.out.printf("%-18s\t%6d\t\t%9d\t%s\n", 
            nome, tempo, Ordenacao.getOperacoes(), complexidade);
    }

    public List<Filme> getFilmes() {
        return filmes;
    }
}