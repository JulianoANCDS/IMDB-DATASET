package ed;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CatalogoFilmes catalogo = new CatalogoFilmes();
        System.out.println("Carregando dados do IMDb...");
        catalogo.carregarDados("title.basics.tsv", "title.ratings.tsv");
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== SISTEMA DE FILMES IMDb ===");
            System.out.println("1. Buscar filme por título");
            System.out.println("2. Ver filmes melhor avaliados");
            System.out.println("3. Comparar algoritmos de busca");
            System.out.println("4. Comparar algoritmos de ordenação");
            System.out.println("5. Comparar estruturas de dados");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    buscarFilme(catalogo, scanner);
                    break;
                case 2:
                    recomendarFilmes(catalogo, scanner);
                    break;
                case 3:
                    compararAlgoritmosBusca(catalogo, scanner);
                    break;
                case 4:
                    catalogo.compararAlgoritmosOrdenacao();
                    break;
                case 5:
                    catalogo.compararEstruturas();
                    break;
                case 6:
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void buscarFilme(CatalogoFilmes catalogo, Scanner scanner) {
        System.out.print("\nDigite o título do filme: ");
        String titulo = scanner.nextLine();
        
        System.out.println("\nSelecione o método de busca:");
        System.out.println("1. Pesquisa Sequencial");
        System.out.println("2. Pesquisa Binária");
        System.out.println("3. Árvore Binária de Busca (BST)");
        System.out.println("4. Árvore AVL");
        System.out.print("Opção: ");
        int metodo = scanner.nextInt();
        scanner.nextLine();
        
        long inicio = System.currentTimeMillis();
        Filme filme = null;
        
        switch (metodo) {
            case 1: filme = catalogo.buscarPesquisaSequencial(titulo); break;
            case 2: filme = catalogo.buscarPesquisaBinaria(titulo); break;
            case 3: filme = catalogo.buscarBST(titulo); break;
            case 4: filme = catalogo.buscarAVL(titulo); break;
            default: System.out.println("Opção inválida!"); return;
        }
        
        long tempo = System.currentTimeMillis() - inicio;
        
        if (filme != null) {
            System.out.println("\nFilme encontrado:");
            System.out.println(filme);
        } else {
            System.out.println("\nFilme não encontrado.");
        }
        System.out.println("Tempo de busca: " + tempo + "ms");
    }

    private static void recomendarFilmes(CatalogoFilmes catalogo, Scanner scanner) {
        System.out.print("\nQuantos filmes deseja ver? ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();
        
        List<Filme> melhores = catalogo.recomendarTopFilmes(quantidade);
        
        System.out.println("\nTop " + quantidade + " filmes melhor avaliados:");
        for (int i = 0; i < melhores.size(); i++) {
            System.out.println((i+1) + ". " + melhores.get(i));
        }
    }

    private static void compararAlgoritmosBusca(CatalogoFilmes catalogo, Scanner scanner) {
        System.out.print("\nDigite um título para testar: ");
        String titulo = scanner.nextLine();
        catalogo.compararAlgoritmosBusca(titulo);
    }
}