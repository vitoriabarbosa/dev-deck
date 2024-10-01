package devdeck.model.home;

import devdeck.model.NoCarta;
import devdeck.model.interfaces.Base;
import devdeck.model.interfaces.Home;
import devdeck.exceptions.InvalidMoves;

import java.util.ArrayList;
import java.util.Stack;

public class MonteHome implements Home {
    private ArrayList<Stack<NoCarta>> montes = new ArrayList<>();
    private int numMonteAtivo = 0;

    @Override
    public boolean receberNo(NoCarta carta) throws InvalidMoves {
        return false; // Ajuste necessário para lidar com a lógica de receber cartas
    }

    @Override
    public void remover(NoCarta nc) {
        // Lógica para remover carta, se necessário
    }

    private Stack<NoCarta> monteAtual = null;

    public void inserir(NoCarta nc) {
        if (monteAtual == null || monteAtual.size() == 3) {
            monteAtual = new Stack<>();
            this.montes.add(monteAtual);
        }
        nc.setHome(this);
        monteAtual.add(nc);
    }

    public Stack<NoCarta> retira3Cartas() {
        if (this.montes.isEmpty()) return null;

        Stack<NoCarta> cartas = this.montes.get(this.numMonteAtivo);
        this.numMonteAtivo = (this.numMonteAtivo + 1) % this.montes.size(); // Garante que o monte ativo seja circular
        return cartas;
    }

    public int getNumMonteAtivo() {
        return this.numMonteAtivo;
    }

    public ArrayList<Stack<NoCarta>> getMontes() {
        return this.montes;
    }

    @Override
    public Base getBase() {
        return null; // Não aplicável aqui
    }

    @Override
    public void setBase(Base base) {
        // Não aplicável aqui
    }
}
