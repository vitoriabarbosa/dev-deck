package devdeck.model.home;

import devdeck.exceptions.MovimentosInvalidos;
import devdeck.model.NoCarta;
import devdeck.model.base.Base;

/**
 * Interface dos componentes "Home"
 * Todos eles devem poder receber uma carta
 */
public interface Home {
    void receberNo(NoCarta carta) throws MovimentosInvalidos;
    void remover(NoCarta nc);
    Base getBase();
    void setBase(Base base);
}
