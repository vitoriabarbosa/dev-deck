package devdeck.model.home;

import devdeck.exceptions.MovimentosInvalidos;
import devdeck.model.NoCarta;
import devdeck.model.base.Base;
import devdeck.model.base.PilhaCarta;
import devdeck.utils.event.CartaEvento;
import devdeck.view.JogoApp;

/**
 * A classe {@code PilhaHome} representa uma pilha de cartas do jogo,
 * onde as cartas devem ser organizadas em sequência crescente de números
 * e com o mesmo naipe. É uma extensão de {@link PilhaCarta} e implementa a interface {@link Home}.
 */
public class PilhaHome extends PilhaCarta implements Home {

    private CartaEvento cartaEvento;


    /**
     * A base associada a esta pilha de cartas.
     */
    private Base base = null;

    /**
     * Referência ao JogoApp para interagir com o jogo.
     */
    private JogoApp jogoApp;

    /**
     * Cria uma nova instância de {@code PilhaHome} com um nome definido
     * e inicializa a pilha com uma capacidade máxima de 7 cartas.
     *
     * @param nome O nome da pilha.
     * @param jogoApp Referência para a instância do JogoApp.
     */
    public PilhaHome(String nome, JogoApp jogoApp, CartaEvento cartaEvento) {
        super(nome);
        this.jogoApp = jogoApp;
        this.cartas = new NoCarta[7];
        this.cartaEvento = cartaEvento;
    }

    /**
     * Define a base associada a esta pilha.
     *
     * @param base A instância da {@link Base} a ser associada.
     */
    @Override
    public void setBase(Base base) {
        this.base = base;
    }

    /**
     * Retorna a base associada a esta pilha.
     *
     * @return A instância de {@link Base} associada a esta pilha.
     */
    @Override
    public Base getBase() {
        return this.base;
    }

    /**
     * Insere uma carta no topo da pilha, verificando as regras do jogo.
     *
     * - A pilha aceita uma nova carta se estiver vazia e a carta for um Ás (número 1), ou
     *   se a carta for do mesmo naipe e seguir a sequência crescente de números.
     * - Caso a pilha seja completada, uma mensagem de sucesso é exibida.
     * - O método também verifica se todas as pilhas-base do jogo foram completadas.
     *
     * @param carta A carta a ser inserida na pilha.
     * @throws MovimentosInvalidos Se a carta não atender às regras de sequência.
     */

    @Override
    public void receberNo(NoCarta carta) throws MovimentosInvalidos {
        NoCarta cartaTopo = this.elementoTopo();

        if ((this.pilhaVazia() && carta.getNumero() == 1)
                || (!this.pilhaVazia() && cartaTopo.getNumero() == carta.getNumero() - 1
                && cartaTopo.getNaipe() == carta.getNaipe())
        ) {
            Home homeFrom = (Home) carta.getHome();
            homeFrom.remover(carta);
            carta.setHome(this);
            this.empilhar(carta);

            // Associa eventos à carta recebida
            carta.addMouseListener(cartaEvento);
            carta.addMouseMotionListener(cartaEvento);

            // Verificar condição de vitória para esta pilha
            if (this.verificarPilhaCompleta()) {
                System.out.println("Parabéns! " + this.getNome() + " está completa!");
            }

            // Verificar condição de vitória global
            if (this.jogoApp != null) {
                this.jogoApp.verificaFimDeJogo();
            } else {
                throw new IllegalStateException("Não foi possível encontrar a instância de JogoApp.");
            }
        } else {
            throw new MovimentosInvalidos(MovimentosInvalidos.PILHA_NO_SEQUENCIA_INVALIDA);
        }
    }

    /**
     * Remove a carta do topo da pilha.
     *
     * @param nc A carta a ser removida.
     */
    @Override
    public void remover(NoCarta nc) {
        this.desempilhar();
    }

    /**
     * Verifica se a pilha está completa.
     *
     * @return {@code true} se a pilha estiver completa, {@code false} caso contrário.
     */
    public boolean verificarPilhaCompleta() {
        for (int i = 0; i < cartas.length; i++) {
            if (cartas[i] == null || cartas[i].getNumero() != i + 1) {
                return false; // A pilha está incompleta ou fora de ordem
            }
        }
        return true;
    }
}