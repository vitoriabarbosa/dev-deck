package devdeck.model.home;

import devdeck.exceptions.MovimentosInvalidos;
import devdeck.model.NoCarta;
import devdeck.model.base.Base;
import devdeck.model.base.ListaCarta;
import devdeck.view.JogoApp;

/**
 * Classe de lista de cartas.
 * 7 instancias dessa classe são criadas para o jogo funcionar.
 * Elas são a área de trabalho principal do paciência.
 */
public class ListaHome extends ListaCarta implements Home {
    private Base base = null;

    public ListaHome(String nome) {
        super(nome);
    }
    
    @Override
    public void setBase(Base base) {
        this.base = base;
    }
    
    @Override
    public Base getBase() {
        return this.base;
    }
    
    /**
     * Insere uma carta na lista de cartas.
     * Este método deve ser usado apenas no inicio do jogo, para colocar
     * cartas aleatórias e sem validação na lista.
     * 
     * @param noCarta
     */
    public void inserir(NoCarta noCarta) {
        NoCarta antigoFinal = this.elementoFinal();
        this.inserirFinal(noCarta);
        
        // Vira as cartas (fecha a penultima, abre a ultima)
        if (antigoFinal != null) {
            antigoFinal.setOpen(false);
        }

        this.elementoFinal().setOpen(true);
    }
    
    /**
     * Recebe um nó, valida e armazena na lista atual
     *
     * @param carta No (sublista) de cartas a serem inseridas na lista.
     */
    @Override
    public void receberNo(NoCarta carta)
        throws MovimentosInvalidos
    {
        if (carta == null || !carta.isOpen()) {
            throw new MovimentosInvalidos(MovimentosInvalidos.LISTA_NO_FECHADO);
        }
        
        // Valida o nó sendo arrastado (deve estar na ordem certa)
        if (!validaConjuntoNo(carta)) {
            throw new MovimentosInvalidos(MovimentosInvalidos.LISTA_NO_INVALIDO);
        }
        

        // Verifica se a carta do topo da sequencia encaixa com a do topo da lista que queremos inserir.
        if (!this.listaVazia() && !JogoApp.cartaSequenciaValida(this.elementoFinal(), carta)) {
            throw new MovimentosInvalidos(MovimentosInvalidos.LISTA_SEQUENCIA_INVALIDA);
        }
        
        // Se for uma sublista indo para uma lista vazia, o topo deve ser obrigatoriamente um rei
        if (this.listaVazia() && carta.getNumero() != 13) {
            throw new MovimentosInvalidos(MovimentosInvalidos.LISTA_VAZIA_APENAS_REIS);
        }
        
        // Tudo certo, agora sim inserimos na nova lista e removemos ele da lista antiga
        Home listaFrom = (Home) carta.getHome();
        listaFrom.remover(carta);
        this.inserirFinal(carta);
        carta.setHome(this);

    }
    
    /**
     * Valida um conjunto de nós.
     * Para um conjunto de nós ser válido e poder ser arrastado,
     * todas as cartas depois dele devem estar em ordem decrescente,
     * com cores intercaladas.
     */
    private boolean validaConjuntoNo(NoCarta no) {
        NoCarta aux = no;
        
        // Verifica se os nós estão na sequencia correta
        while(aux != null) {
            if (aux.getProx() != null && !JogoApp.cartaSequenciaValida(aux, aux.getProx())) {
                return false;
            }
            aux = aux.getProx();
        }
        return true;
    }
    
    @Override
    public void remover(NoCarta noCarta) {
        super.remover(noCarta);
        
        // Abre a ultima carta da lista
        if (!this.listaVazia()) {
            this.elementoFinal().setOpen(true);
        }
    }
}