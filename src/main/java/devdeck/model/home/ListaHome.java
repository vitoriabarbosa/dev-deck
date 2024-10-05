package devdeck.model.home;

import devdeck.exceptions.MovimentosInvalidos;
import devdeck.model.NoCarta;
import devdeck.model.base.Base;
import devdeck.model.base.ListaCarta;
import devdeck.view.JogoApp;

/**
 * A classe {@code ListaHome} representa uma lista de cartas no jogo.
 * 7 instâncias desta classe são criadas para formar as áreas de trabalho
 * principais do jogo de paciência.
 */
public class ListaHome extends ListaCarta implements Home {

    /**
     * Base associada à lista.
     */
    private Base base = null;

    /**
     * Construtor que cria uma nova lista de cartas com o nome especificado.
     *
     * @param nome O nome da lista.
     */
    public ListaHome(String nome) {
        super(nome);
    }

    /**
     * Define a base associada à lista.
     *
     * @param base A base a ser associada.
     */
    @Override
    public void setBase(Base base) {
        this.base = base;
    }

    /**
     * Retorna a base associada à lista.
     *
     * @return A base associada à lista.
     */
    @Override
    public Base getBase() {
        return this.base;
    }

    /**
     * Insere uma carta no final da lista de cartas.
     * Esse método é usado apenas no início do jogo para inserir cartas
     * aleatoriamente na lista, sem validação.
     *
     * @param noCarta A carta a ser inserida na lista.
     */
    public void inserir(NoCarta noCarta) {
        if (noCarta != null) {
            NoCarta antigoFinal = this.elementoFinal();
            this.inserirFinal(noCarta);

            if (antigoFinal != null) {
                antigoFinal.setOpen(false);
            }

            this.elementoFinal().setOpen(true);
            noCarta.setHome(this);
        } else {
            System.err.println("Erro: tentativa de inserir um NoCarta null em ListaHome.");
        }
    }

    /**
     * Recebe um nó de cartas, valida a sequência e insere na lista atual.
     *
     * @param carta O nó (ou sublista) de cartas a ser inserido na lista.
     * @throws MovimentosInvalidos Se a carta estiver fechada ou a sequência não for válida.
     */
    @Override
    public void receberNo(NoCarta carta) throws MovimentosInvalidos {
        if (carta == null || !carta.isOpen()) {
            throw new MovimentosInvalidos(MovimentosInvalidos.LISTA_NO_FECHADO);
        }

        // Valida a sequência de nós.
        if (!validaConjuntoNo(carta)) {
            throw new MovimentosInvalidos(MovimentosInvalidos.LISTA_NO_INVALIDO);
        }

        // Valida a sequência entre o topo da lista e a carta recebida.
        if (!this.listaVazia() && !JogoApp.cartaSequenciaValida(this.elementoFinal(), carta)) {
            throw new MovimentosInvalidos(MovimentosInvalidos.LISTA_SEQUENCIA_INVALIDA);
        }

        // Se a lista estiver vazia, o topo da sublista deve ser um rei (7).
        if (this.listaVazia() && carta.getNumero() != 7) {
            throw new MovimentosInvalidos(MovimentosInvalidos.LISTA_VAZIA_APENAS_REIS);
        }

        // Move a sublista para a nova lista e remove da anterior.
        Home listaFrom = (Home) carta.getHome();
        listaFrom.remover(carta);
        this.inserirFinal(carta);
        carta.setHome(this);
    }

    /**
     * Valida um conjunto de nós para garantir que todas as cartas estão
     * em ordem decrescente com cores alternadas.
     *
     * @param no O nó de cartas a ser validado.
     * @return {@code true} se o conjunto for válido, caso contrário, {@code false}.
     */
    private boolean validaConjuntoNo(NoCarta no) {
        NoCarta aux = no;

        // Verifica se os nós estão na sequência correta.
        while(aux != null) {
            if (aux.getProx() != null && !JogoApp.cartaSequenciaValida(aux, aux.getProx())) {
                return false;
            }
            aux = aux.getProx();
        }
        return true;
    }

    /**
     * Remove uma carta da lista. Se a carta removida for a última da lista,
     * a próxima carta no topo será virada para cima.
     *
     * @param nc A carta a ser removida.
     */
    @Override
    public void remover(NoCarta nc) {
        super.remover(nc);

        // Abre a última carta da lista, se houver.
        if (!this.listaVazia()) {
            this.elementoFinal().setOpen(true);
        }
    }
}
