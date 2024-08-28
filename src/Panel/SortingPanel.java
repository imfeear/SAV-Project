package Panel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import Sorts.BubbleSort;
import Sorts.InsertionSort;
import Sorts.SelectionSort;
import Sorts.SortAlgorithm;

/**
 * A classe SortingPanel é um JPanel personalizado usado para visualizar algoritmos de ordenação.
 * Ela exibe um array de dados sendo ordenados, com opções para personalizar o algoritmo de ordenação,
 * a ordem e os rótulos.
 */
public class SortingPanel extends JPanel {
    private Object[] array;
    private SortAlgorithm sortAlgorithm;
    private String order;
    private String labelType;
    private Color[] colors;
    private long startTime;
    private int pause; // Campo para o tempo de pausa entre os passos de ordenação
    private Timer timer;
    private long elapsedTime;

    /**
     * Constrói um SortingPanel com um fundo branco e um tamanho predefinido.
     * Inicializa um array de cores para as barras.
     */
    public SortingPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1470, 700));
        colors = new Color[]{
                Color.BLACK,Color.RED,Color.YELLOW,Color.CYAN,Color.PINK,Color.MAGENTA
        };
    }

    /**
     * Define o array a ser ordenado e repinta o painel.
     *
     * @param array O array a ser ordenado.
     */
    public void setArray(Object[] array) {
        this.array = array;
        repaint();
    }

    /**
     * Configura os dados e o algoritmo de ordenação para o painel.
     *
     * @param data      O array de dados a ser ordenado.
     * @param algorithm O algoritmo de ordenação a ser usado ("b" para BubbleSort, "i" para InsertionSort, "s" para SelectionSort).
     * @param order     A ordem da ordenação ("AZ" para crescente, "ZA" para decrescente).
     * @param labelType O tipo de rótulo a ser exibido nas barras ("n" para números, "c" para caracteres).
     * @param pause     O tempo de pausa em milissegundos entre os passos de ordenação.
     */
    public void setData(Object[] data, String algorithm, String order, String labelType, int pause) {
        this.array = data;
        this.order = order;
        this.labelType = labelType;
        this.pause = pause; //Define o tempo de pausa

        //Configura o algoritmo de ordenação
        switch (algorithm.toLowerCase()) {
            case "b":
                this.sortAlgorithm = new BubbleSort();
                break;
            case "i":
                this.sortAlgorithm = new InsertionSort();
                break;
            case "s":
                this.sortAlgorithm = new SelectionSort();
                break;
            default:
                throw new IllegalArgumentException("Algoritmo não suportado");
        }
    }

    /**
     * Inicia o processo de ordenação e atualiza a interface gráfica periodicamente para refletir os passos da ordenação.
     */
    public void startSorting() {
        startTime = System.currentTimeMillis(); //Marca o início da ordenação
        elapsedTime = 0; //Reseta o tempo decorrido
        timer = new Timer(); //Cria um novo Timer
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedTime = System.currentTimeMillis() - startTime;
                repaint(); //Repinta para atualizar o tempo
            }
        }, 0, 100); //Atualiza a cada 100 ms

        new Thread(() -> {
            sortAlgorithm.sort(array, order, pause);
            timer.cancel(); //Para o Timer quando a ordenação termina
            repaint();
        }).start();
    }

    /**
     * Pinta o componente, desenhando as barras que representam os elementos do array, seus rótulos e o tempo decorrido.
     *
     * @param g O objeto Graphics usado para desenhar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (array == null || array.length == 0) {
            return; // Evita pintar se o array estiver vazio
        }

        int width = getWidth();
        int height = getHeight();
        int margin = 20; // Margem das bordas

        //Corrige a obtenção dos valores máximo e mínimo do array
        int maxValue = Arrays.stream(array)
                .mapToInt(e -> e instanceof Integer ? (Integer) e : (Character) e)
                .max().orElse(1); // Obtém o valor máximo ou 1 se o array estiver vazio

        int minValue = Arrays.stream(array)
                .mapToInt(e -> e instanceof Integer ? (Integer) e : (Character) e)
                .min().orElse(0); // Obtém o valor mínimo ou 0 se o array estiver vazio

        int numBars = array.length; //Usa o comprimento do array como o número de barras
        int totalSpace = width - 2 * margin; // Espaço total disponível para as barras

        //Ajusta a largura das barras e o espaçamento para garantir que todas as barras sejam visíveis
        int barWidth = totalSpace / numBars; // Largura ajustada das barras
        int spaceBetweenBars = Math.max(1, (totalSpace - (barWidth * numBars)) / (numBars - 1)); // Espaço entre as barras

        g.setFont(new Font("SansSerif", Font.PLAIN, 12)); // Ajusta o tamanho da fonte para caracteres

        //Calcula a linha de base para as barras
        int zeroLine = (int) (height * 0.1 + (maxValue / (double) (maxValue - minValue)) * (height * 0.8));

        for (int i = 0; i < numBars; i++) {
            int value = array[i] instanceof Integer ? (Integer) array[i] : (Character) array[i];
            int barHeight = (int) ((double) Math.abs(value) / (maxValue - minValue) * (height * 0.8)); // Dimensiona a altura das barras

            int x = margin + i * (barWidth + spaceBetweenBars); // Calcula a posição x de cada barra
            int y = value >= 0 ? zeroLine - barHeight : zeroLine; // Calcula a posição y de cada barra

            //Define a cor das barras
            g.setColor(colors[i % colors.length]);
            g.fillRect(x, y, barWidth, barHeight);

            //Exibe o rótulo de acordo com o tipo
            g.setColor(Color.BLACK);
            if (labelType.equalsIgnoreCase("n")) {
                // Exibe os valores numéricos acima das barras (ou abaixo para negativos)
                String valueString = String.valueOf(value);
                int labelY = value >= 0 ? y - 5 : y + barHeight + 15;
                g.drawString(valueString, x + barWidth / 2 - g.getFontMetrics().stringWidth(valueString) / 2, labelY);
            } else if (labelType.equalsIgnoreCase("c")) {
                //Exibe os caracteres acima das barras
                String valueString = String.valueOf((char) value);
                g.drawString(valueString, x + barWidth / 2 - g.getFontMetrics().stringWidth(valueString) / 2, y - 5);
            }
        }

        //Exibe o tempo decorrido no canto superior direito
        g.setColor(Color.BLACK);
        String timeString = String.format("Tempo decorrido: %.2f segundos", elapsedTime / 1000.0);
        g.drawString(timeString, width - g.getFontMetrics().stringWidth(timeString) - 10, 20);
    }
}
