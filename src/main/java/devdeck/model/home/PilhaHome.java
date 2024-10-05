package devdeck.model.home;

import devdeck.exceptions.MovimentosInvalidos;
import devdeck.model.NoCarta;
import devdeck.model.base.Base;
import devdeck.model.base.PilhaCarta;

public class PilhaHome extends PilhaCarta implements Home {
    private Base base = null;
    
    public PilhaHome(String nome) {
        super(nome);
        this.cartas = new NoCarta[7];
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
     * Insere uma carta no topo da pilha de cartas.
     * Só podemos inserir a carta se ela for um A (se a pilha for vazia)
     * ou se ela for a proxima carta da sequencia.
     *
     * @param carta
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
     * Remove uma carta do topo do baralho (se disponível)
     * 
     * @return Retorna a ultima carta do baralho
     */
    @Override
    public void remover(NoCarta nc) {
        this.desempilhar();
    }
}
