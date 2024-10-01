package devdeck.model.home;

import devdeck.model.interfaces.Base;
import devdeck.model.interfaces.Home;
import devdeck.exceptions.InvalidMoves;
import devdeck.model.NoCarta;
import devdeck.model.base.ListaCarta;
import devdeck.view.GameApp;

public class ListaHome extends ListaCarta implements Home {
    private int x = 0;
    private int y = 0;
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

    public void inserir(NoCarta noCarta) {
        NoCarta antigoFinal = this.elementoFinal();
        this.inserirFinal(noCarta);

        // Vira as cartas (fecha a penultima, abre a ultima)
        if(antigoFinal != null)
            antigoFinal.setOpen(false);

        this.elementoFinal().setOpen(true);
    }

    @Override
    public boolean receberNo(NoCarta carta)
            throws InvalidMoves
    {
        if(carta == null || !carta.isOpen())
            throw new InvalidMoves(InvalidMoves.LISTA_NO_FECHADO);

        if(!validaConjuntoNo(carta))
            throw new InvalidMoves(InvalidMoves.LISTA_NO_INVALIDO);


        Home listaFrom = (Home) carta.getHome();
        listaFrom.remover(carta);
        this.inserirFinal(carta);
        carta.setHome(this);

        return true;
    }

    public void moverNo(int num, ListaHome listaHome)
            throws InvalidMoves
    {
        NoCarta no = this.buscarNo(num);
        listaHome.receberNo(no);
    }

    private boolean validaConjuntoNo(NoCarta no) {
        NoCarta aux = no;

        // Verifica se os nós estão na sequencia correta
        while(aux != null) {
            if(
                    aux.getProx() != null &&
                            !GameApp.cartaSequenciaValida(aux, aux.getProx())
            )
                return false;

            aux = aux.getProx();
        }

        return true;
    }


    @Override
    public void remover(NoCarta noCarta) {
        super.remover(noCarta);

        // Abre a ultima carta da lista
        if(!this.listaVazia())
            this.elementoFinal().setOpen(true);
    }
}
