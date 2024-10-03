package devdeck.model;

import devdeck.model.Baralho.ECor;
import devdeck.model.Baralho.ENaipe;

public class Naipe {
    private ENaipe naipe;
    private ECor cor;
    
    public Naipe(ENaipe naipe, ECor cor) {
        this.naipe = naipe;
        this.cor = cor;
    }
    
    public ENaipe getNaipe() {
        return this.naipe;
    }
    
    public ECor getCor() {
        return this.cor;
    }
}
