package devdeck.model.home;

import devdeck.model.interfaces.Base;
import devdeck.model.interfaces.Home;
import devdeck.model.base.PilhaCarta;
import devdeck.exceptions.InvalidMoves;
import devdeck.model.NoCarta;

import java.util.Objects;

public class PilhaHome extends PilhaCarta implements Home {
    public int x = 0;
    public int y = 0;
    private Base base = null;

    public PilhaHome(String nome) {
        super(nome);
        this.cartas = new NoCarta[6];
    }

    @Override
    public void setBase(Base base) {
        this.base = base;
    }

    @Override
    public Base getBase() {
        return this.base;
    }

    @Override
    public boolean receberNo(NoCarta carta)
            throws InvalidMoves
    {
        NoCarta cartaTopo = this.elementoTopo();
        this.pilhaVazia();
        if(!this.pilhaVazia() && Objects.equals(cartaTopo.getNumero(), carta.getNumero()) && Objects.equals(cartaTopo.getNaipe(), carta.getNaipe())
        ) {
            Home homeFrom = carta.getHome();
            homeFrom.remover(carta);
            carta.setHome(this);
            return this.empilhar(carta);
        }

        throw new InvalidMoves(InvalidMoves.PILHA_NO_SEQUENCIA_INVALIDA);
    }

    @Override
    public void remover(NoCarta nc) {
        this.desempilhar();
    }


}
