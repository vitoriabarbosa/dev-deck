package devdeck.model.home;

import devdeck.exceptions.MovimentosInvalidos;
import devdeck.model.NoCarta;
import devdeck.model.base.Base;
import devdeck.model.base.PilhaCarta;

/**
 * A classe {@code PilhaHome} representa uma pilha de cartas do jogo,
 * onde as cartas são organizadas de acordo com as regras de sequência definidas.
 * É uma implementação da interface {@code Home}.
 */
public class PilhaHome extends PilhaCarta implements Home {

    /**
     * A base associada a esta pilha de cartas.
     */
    private Base base = null;

    /**
     * Cria uma nova {@code PilhaHome} com um nome e define um limite de 7 cartas.
     *
     * @param nome O nome da pilha.
     */
    public PilhaHome(String nome) {
        super(nome);
        this.cartas = new NoCarta[7];
    }

    /**
     * Define a base associada a esta pilha de cartas.
     *
     * @param base A base a ser associada.
     */
    @Override
    public void setBase(Base base) {
        this.base = base;
    }

    /**
     * Retorna a base associada a esta pilha de cartas.
     *
     * @return A base associada.
     */
    @Override
    public Base getBase() {
        return this.base;
    }

    /**
     * Insere uma carta no topo da pilha de cartas, de acordo com as regras:
     * - A carta pode ser inserida se for um Ás (quando a pilha estiver vazia).
     * - A carta pode ser inserida se for a próxima na sequência do topo da pilha,
     *   e se o naipe for o mesmo.
     *
     * @param carta A carta a ser inserida na pilha.
     * @throws MovimentosInvalidos Se a carta não puder ser inserida de acordo com as regras de sequência.
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
            return;
        }

        throw new MovimentosInvalidos(MovimentosInvalidos.PILHA_NO_SEQUENCIA_INVALIDA);
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
}
