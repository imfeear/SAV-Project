package Sorts;

/**
 * Implementa o algoritmo de ordenação Insertion Sort.
 */
public class InsertionSort implements SortAlgorithm {

    /**
     * Ordena o array utilizando o algoritmo Insertion Sort.
     *
     * @param array O array de objetos a ser ordenado.
     * @param order A ordem da ordenação ("AZ" para crescente, "ZA" para decrescente).
     * @param pause O tempo de pausa em milissegundos entre cada passo de ordenação.
     */
    @Override
    public void sort(Object[] array, String order, int pause) {
        for (int i = 1; i < array.length; i++) {
            Object key = array[i];
            int j = i - 1;
            while (j >= 0 && compare(array[j], key, order) > 0) {
                array[j + 1] = array[j];
                j--;
                sleep(pause);
            }
            array[j + 1] = key;
            sleep(pause);
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
