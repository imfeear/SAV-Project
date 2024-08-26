package Sorts;

/**
 * Implementa o algoritmo de ordenação Bubble Sort.
 */
public class BubbleSort implements SortAlgorithm {

    /**
     * Ordena o array utilizando o algoritmo Bubble Sort.
     *
     * @param array O array de objetos a ser ordenado.
     * @param order A ordem da ordenação ("AZ" para crescente, "ZA" para decrescente).
     * @param pause O tempo de pausa em milissegundos entre cada passo de ordenação.
     */
    @Override
    public void sort(Object[] array, String order, int pause) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (compare(array[j], array[j + 1], order) > 0) {
                    swap(array, j, j + 1);
                }
                sleep(pause);
            }
        }
    }

    /**
     * Compara dois objetos com base na ordem especificada.
     *
     * @param o1    O primeiro objeto.
     * @param o2    O segundo objeto.
     * @param order A ordem de comparação ("AZ" para crescente, "ZA" para decrescente).
     * @return Um valor negativo, zero ou positivo se o primeiro objeto for menor,
     * igual ou maior que o segundo, respectivamente.
     */
    private int compare(Object o1, Object o2, String order) {
        int comparisonResult;
        if (o1 instanceof Integer && o2 instanceof Integer) {
            comparisonResult = Integer.compare((Integer) o1, (Integer) o2);
        } else if (o1 instanceof Character && o2 instanceof Character) {
            comparisonResult = Character.compare((Character) o1, (Character) o2);
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }

        return order.equalsIgnoreCase("ZA") ? -comparisonResult : comparisonResult;
    }

    /**
     * Troca dois elementos no array.
     *
     * @param array O array em que a troca será realizada.
     * @param i     O índice do primeiro elemento.
     * @param j     O índice do segundo elemento.
     */
    private void swap(Object[] array, int i, int j) {
        Object temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Pausa a execução por um tempo especificado.
     *
     * @param pause O tempo de pausa em milissegundos.
     */
    private void sleep(int pause) {
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
