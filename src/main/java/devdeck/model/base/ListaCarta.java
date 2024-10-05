package devdeck.model.base;

import devdeck.model.NoCarta;

public class ListaCarta {
    public NoCarta primeiro = null;
    public NoCarta ultimo = null;
    private String nome;
    
    public ListaCarta(String nome) {
        this.nome = nome;
    }
    
    public boolean listaVazia() {
        return primeiro == null;
    }
    
    protected void inserirFinal(NoCarta novoNo) {
        NoCarta novoUltimo = novoNo;
        if (listaVazia()) {
            primeiro = novoNo;
        } else {
            // Busca o ultimo
            while (novoUltimo.getProx() != null)
                novoUltimo = novoUltimo.getProx();
            ultimo.setProx(novoNo);
        }
        ultimo = novoUltimo;
    }
    
    public int contarNos() {
        int i = 0;
        NoCarta aux = primeiro;
        while (aux != null) {
            aux = aux.getProx();
            i++;
        }
        return i;
    }
    
    protected int buscarNo(NoCarta nc) {
        int pos = 0;
        NoCarta aux = primeiro;
        while(aux != null) {
            pos++;
            if (aux == nc) {
                return pos;
            }
            aux = aux.getProx();
        }
        return 0;
    }
    
    public NoCarta recuperaNo(int num) {
        int pos = 1;
        NoCarta aux = primeiro;
        while (aux != null) {
            if (pos == num) {
                return aux;
            }
            aux = aux.getProx();
            pos++;
        }
        return null;
    }
    
    /**
     * Remove um nó da lista ligada.
     * A lógica é um pouco diferente de uma lista comum, ao remover um nó
     * o penultimo nó vira o último (todos os nós para frente vão embora junto).
     * @param noCarta
     */
    protected void remover(NoCarta noCarta) {
        int pos = this.buscarNo(noCarta);
        NoCarta aux = this.primeiro;
        
        if (pos > 0) {
            if (pos == 1) {
                this.primeiro = null;
                this.ultimo = null;
            } else {
                for (int i=1; i<=pos-2; i++) {
                    aux = aux.getProx();
                }
                aux.setProx(null);
                this.ultimo = aux;
            }
        }
    }

    public NoCarta elementoFinal() {
        if (!listaVazia()) {
            return ultimo;
        } else {
            return null;
        }
    }
    
    public String getNome() {
        return this.nome;
    }

    @Override
    public String toString() {
        return this.toString(false);
    }
    
    public String toString(boolean mostrarNome) {
        StringBuilder builder = new StringBuilder();
        if (mostrarNome) {
            builder.append(this.getNome()).append(": ");
        }
        NoCarta aux = primeiro;
        while (aux != null) {
            builder.append(aux.getNumero()).append(aux.getNaipe().getNaipe().name()).append(",");
            aux = aux.getProx();
        }
        return builder.toString();
    }
}