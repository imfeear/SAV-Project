import Panel.SortingPanel;
import TratamentoArgs.ArgumentProcessor;
import javax.swing.*;

/**
 * A classe principal para iniciar a aplicação de visualização de algoritmos de ordenação.
 *
 * Esta classe inicializa o processador de argumentos, configura o painel de ordenação,
 * e exibe a interface gráfica do usuário (GUI) com o painel de ordenação.
 *
 * <p>Nome: Bruno Ricardo da Silva Reis</p>
 * <p>Versão do Java: 22</p>
 * <p>Email: reeiskkz@gmail.com</p>
 *
 * @version 22
 */
public class Algorithm {

    /**
     * O ponto de entrada principal para a aplicação.
     *
     * @param args Argumentos da linha de comando fornecidos ao iniciar a aplicação.
     */
    public static void main(String[] args) {
        try {
            //Inicializa o ArgumentProcessor com os argumentos da linha de comando
            ArgumentProcessor processor = new ArgumentProcessor(args);

            //Cria e configura o painel
            SortingPanel panel = new SortingPanel();
            panel.setData(
                    processor.getData(),
                    processor.getAlgorithm(),
                    processor.getOrder(),
                    processor.getLabelType(),
                    processor.getPause()
            );

            //Inicia a ordenação no painel
            panel.startSorting();

            //Configura a interface gráfica
            JFrame frame = new JFrame("Visualização do Algoritmo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Define o tamanho do frame com base no tamanho especificado pelo ArgumentProcessor
            int size = Math.max(processor.getSize(), 900); //Define um tamanho padrão se o valor for inválido
            frame.setSize(size, size);
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            //Exibe qualquer exceção que ocorra durante a inicialização
            e.printStackTrace();
        }
    }
}
