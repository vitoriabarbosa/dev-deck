package devdeck.model.interfaces;

import devdeck.exceptions.InvalidMoves;
import devdeck.model.NoCarta;

public interface Home {
    public boolean receberNo(NoCarta carta) throws InvalidMoves;
    public void remover(NoCarta nc);
    public Base getBase();
    public void setBase(Base base);
}
