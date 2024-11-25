package devdeck.model.home;

import devdeck.exceptions.MovimentosInvalidos;
import devdeck.model.NoCarta;
import devdeck.model.base.Base;
import devdeck.utils.event.CartaEvento;

import java.util.ArrayList;
import java.util.Stack;

/**
 * A classe {@code MonteHome} representa os montes de cartas no jogo.
 * Um monte pode conter até 3 cartas e é mostrado no topo esquerdo do jogo.
 */
public class MonteHome implements Home {

    private CartaEvento cartaEvento;

    /**
     * Lista de montes, onde cada monte é representado por uma {@code Stack<NoCarta>}.
     */
    private ArrayList<Stack<NoCarta>> montes = new ArrayList<Stack<NoCarta>>();

    /**
     * Monte atualmente em uso para a inserção de cartas.
     */
    private Stack<NoCarta> monteAtual = null;

    /**
     * Índice do monte ativo, utilizado ao retirar montes de cartas.
     */
    private int numMonteAtivo = 0;

    /**
     * Recebe uma carta no monte.
     * Este método não é utilizado diretamente em {@code MonteHome}.
     *
     * @param cartaEvento A carta a ser recebida.
     * @throws MovimentosInvalidos Este método não lança exceções, pois não é implementado.
     */

    public MonteHome(CartaEvento cartaEvento) {
        this.cartaEvento = cartaEvento;
    }

    @Override
    public void receberNo(NoCarta carta) throws MovimentosInvalidos {
        // Não utilizado em MonteHome
    }

    /**
     * Remove uma carta específica do monte. Se o monte ficar vazio após a remoção,
     * ele também será removido da lista de montes. A carta que estiver no topo do monte
     * restante será virada para cima.
     *
     * @param noCarta A carta a ser removida do monte.
     */
    @Override
    public void remover(NoCarta noCarta) {
        Stack<NoCarta> monteRemover = null;

        // Encontra o monte que contém a carta a ser removida
        for (Stack<NoCarta> monte : this.montes) {
            if (monte.contains(noCarta)) {
                monteRemover = monte;
                break;
            }
        }

        // Remove a carta e atualiza o estado do monte
        if (monteRemover != null) {
            monteRemover.remove(noCarta);

            if (!monteRemover.isEmpty()) {
                // Abre a última carta restante no monte
                NoCarta carta = monteRemover.get(monteRemover.size() - 1);
                carta.setOpen(true);
            } else {
                // Remove o monte se ele estiver vazio
                this.montes.remove(monteRemover);
            }
        }
    }

    /**
     * Insere uma carta no monte. Um monte pode ter no máximo 3 cartas.
     * Se o monte atual já contiver 3 cartas, um novo monte é criado.
     *
     * @param noCarta A carta a ser inserida no monte.
     */
    public void inserir(NoCarta noCarta) {
        if (monteAtual == null || monteAtual.size() == 3) {
            monteAtual = new Stack<NoCarta>();
            this.montes.add(monteAtual);
        }
        noCarta.setHome(this);
        monteAtual.add(noCarta);

        // Associa eventos à carta
        noCarta.addMouseListener(cartaEvento);
        noCarta.addMouseMotionListener(cartaEvento);
    }

    /**
     * Retorna o próximo monte ativo contendo até 3 cartas.
     * Os montes são acessados em um ciclo.
     *
     * @return Um {@code Stack<NoCarta>} contendo até 3 cartas, ou {@code null} se não houver montes.
     */
    public Stack<NoCarta> retira3Cartas() {
        if (this.montes.isEmpty()) {
            return null;
        }

        if (this.numMonteAtivo + 1 < this.montes.size()) {
            this.numMonteAtivo++;
        } else {
            this.numMonteAtivo = 0;
        }
        return this.montes.get(this.numMonteAtivo);
    }

    /**
     * Retorna o índice do monte ativo.
     *
     * @return O número do monte ativo.
     */
    public int getNumMonteAtivo() {
        return this.numMonteAtivo;
    }

    /**
     * Retorna a lista de montes.
     *
     * @return Uma {@code ArrayList<Stack<NoCarta>>} contendo todos os montes.
     */
    public ArrayList<Stack<NoCarta>> getMontes() {
        return this.montes;
    }

    /**
     * Retorna a base associada a esta instância de {@code MonteHome}.
     * Como não há base associada, retorna {@code null}.
     *
     * @return {@code null}, pois {@code MonteHome} não possui base associada.
     */
    @Override
    public Base getBase() {
        return null;
    }

    /**
     * Define a base associada a esta instância de {@code MonteHome}.
     * Como {@code MonteHome} não utiliza base, este método não faz nada.
     *
     * @param base A base a ser associada (não utilizada).
     */
    @Override
    public void setBase(Base base) {}
}
