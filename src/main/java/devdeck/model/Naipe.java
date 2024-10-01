package devdeck.model;

public class Naipe {
    public enum EnumNaipe {
        JAVA, PYTHON, C, CPLUS
    }

    private EnumNaipe naipe;

    public Naipe(EnumNaipe naipe) {
        this.naipe = naipe;
    }

    public EnumNaipe getNaipe() {
        return this.naipe;
    }
}
