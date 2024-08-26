package Sorts;

/**
 * Interface que define o método de ordenação para os algoritmos.
 */
public interface SortAlgorithm {

    /**
     * Ordena o array utilizando o algoritmo de ordenação especificado.
     *
     * @param array O array de objetos a ser ordenado.
     * @param order A ordem da ordenação ("AZ" para crescente, "ZA" para decrescente).
     * @param pause O tempo de pausa em milissegundos entre cada passo de ordenação.
     */
    void sort(Object[] array, String order, int pause);
}
