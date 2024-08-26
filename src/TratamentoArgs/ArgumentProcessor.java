package TratamentoArgs;

import java.util.Arrays;
import java.util.Random;

/**
 * Processa os argumentos da linha de comando para configurar e gerar dados de entrada
 * para algoritmos de ordenação.
 */
public class ArgumentProcessor {

    private String algorithm;
    private String type;
    private String order;
    private Object[] data;
    private int pause;
    private int size;
    private String inputMethod;

    /**
     * Constrói um objeto ArgumentProcessor com base nos argumentos fornecidos.
     *
     * @param args Argumentos da linha de comando.
     * @throws IllegalArgumentException Se os argumentos forem insuficientes ou inválidos.
     */
    public ArgumentProcessor(String[] args) {
        if (args.length < 5) {
            throw new IllegalArgumentException("Argumentos insuficientes.");
        }

        this.algorithm = getArgumentValue(args, "a");
        this.type = getArgumentValue(args, "t");
        this.order = getArgumentValue(args, "o");
        this.inputMethod = getArgumentValue(args, "in");
        this.pause = parseInt(getArgumentValue(args, "s"));
        this.size = parseInt(getArgumentValue(args, "l"));

        //Processar a entrada para determinar o tamanho corretamente
        this.data = processInput(args);
        this.size = data.length;

        //Validar tamanho
        if (size <= 0) {
            throw new IllegalArgumentException("O tamanho tem que ser maior que zero.");
        }

        if (size > 100) {
            throw new IllegalArgumentException("O tamanho não pode ser maior que 100.");
        }
    }

    /**
     * Obtém o valor de um argumento a partir dos argumentos fornecidos.
     *
     * @param args Os argumentos da linha de comando.
     * @param key A chave do argumento.
     * @return O valor do argumento, ou uma string vazia se a chave não for encontrada.
     */
    private String getArgumentValue(String[] args, String key) {
        for (String arg : args) {
            if (arg.startsWith(key + "=")) {
                return arg.substring(key.length() + 1);
            }
        }
        return "";
    }

    /**
     * Converte uma string para um inteiro, lançando uma exceção se o formato for inválido.
     *
     * @param value A string a ser convertida.
     * @return O valor inteiro correspondente.
     * @throws IllegalArgumentException Se o formato do número for inválido.
     */
    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato de número inválido para: " + value, e);
        }
    }

    /**
     * Gera dados aleatórios com base no tipo especificado.
     *
     * @return Um array de objetos contendo dados aleatórios.
     * @throws IllegalArgumentException Se o tipo não for suportado.
     */
    private Object[] generateRandomData() {
        Random random = new Random();
        Object[] randomData = new Object[size];

        if (type.equals("n")) {
            //Gera números inteiros aleatórios no intervalo de -1000 a 1000
            for (int i = 0; i < size; i++) {
                randomData[i] = random.nextInt(2001) - 1000; // De -1000 a 1000
            }
        } else if (type.equals("c")) {
            //Gera caracteres aleatórios entre 'A' e 'Z'
            for (int i = 0; i < size; i++) {
                randomData[i] = (char) ('A' + random.nextInt(26)); // De 'A' a 'Z'
            }
        } else {
            throw new IllegalArgumentException("Tipo não suportado: " + type);
        }

        return randomData;
    }

    /**
     * Processa a entrada com base no método especificado.
     *
     * @param args Os argumentos da linha de comando.
     * @return Um array de objetos contendo os dados processados.
     * @throws IllegalArgumentException Se o método de entrada não for suportado.
     */
    private Object[] processInput(String[] args) {
        if (inputMethod.equals("r")) {
            return generateRandomData();
        } else if (inputMethod.equals("m")) {
            return processCLIInput(args); // Passa os argumentos corretos para o método
        } else {
            throw new IllegalArgumentException("Método de entrada não suportado: " + inputMethod);
        }
    }

    /**
     * Processa a entrada fornecida na linha de comando.
     *
     * @param args Os argumentos da linha de comando.
     * @return Um array de objetos contendo os dados fornecidos pelo usuário.
     * @throws IllegalArgumentException Se o tipo não for suportado.
     */
    private Object[] processCLIInput(String[] args) {
        String input = getArgumentValue(args, "v"); // Pega o valor do argumento "v"
        if (type.equals("n")) {
            return Arrays.stream(input.split(","))
                    .map(String::trim) // Remove espaços em branco
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
        } else if (type.equals("c")) {
            return input.chars()
                    .mapToObj(c -> (char) c)
                    .toArray(Character[]::new);
        } else {
            throw new IllegalArgumentException("Tipo não suportado: " + type);
        }
    }

    /**
     * Obtém o tipo de dados para os rótulos (números ou caracteres).
     *
     * @return O tipo de dados.
     */
    public String getLabelType() {
        return type;
    }

    /**
     * Obtém o algoritmo de ordenação especificado.
     *
     * @return O algoritmo de ordenação.
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * Obtém o tipo de dados (números ou caracteres).
     *
     * @return O tipo de dados.
     */
    public String getType() {
        return type;
    }

    /**
     * Obtém a ordem de ordenação (crescente ou decrescente).
     *
     * @return A ordem de ordenação.
     */
    public String getOrder() {
        return order;
    }

    /**
     * Obtém os dados a serem ordenados.
     *
     * @return O array de dados.
     */
    public Object[] getData() {
        return data;
    }

    /**
     * Obtém o tempo de pausa entre cada passo de ordenação.
     *
     * @return O tempo de pausa em milissegundos.
     */
    public int getPause() {
        return pause;
    }

    /**
     * Obtém o tamanho do array de dados.
     *
     * @return O tamanho do array.
     */
    public int getSize() {
        return size;
    }
}
