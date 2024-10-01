package devdeck.model.base;

import devdeck.model.NoCarta;

public class ListaCarta {
    public NoCarta primeiro = null;
    public NoCarta ultimo = null;
    private String nome = "";

    public ListaCarta(String nome) {
        this.nome = nome;
    }

    public boolean listaVazia() {
        return primeiro == null;
    }

    protected void inserirInicio(NoCarta novoNo) {
        if(listaVazia()) {
            ultimo = novoNo;
        } else {
            novoNo.setProx(primeiro);
        }
        primeiro = novoNo;
    }

    protected void inserirFinal(NoCarta novoNo) {
        NoCarta novoUlt = novoNo;
        if(listaVazia()) {
            primeiro = novoNo;
        } else {
            // Busca o ultimo
            while(novoUlt.getProx() != null)
                novoUlt = novoUlt.getProx();
            ultimo.setProx(novoNo);
        }
        ultimo = novoUlt;
    }

    public int contarNos() {
        int i = 0;
        NoCarta aux = primeiro;
        while(aux != null) {
            aux = aux.getProx();
            i++;
        }
        return i;
    }

    protected void inserirMeio(NoCarta novoNo, int posicao) {
        NoCarta noTemp = this.primeiro;
        int nroNos = contarNos();
        int posAux = 1;

        if(nroNos <= 1) {
            inserirInicio(novoNo);
        } else {
            if(posicao > nroNos) {
                inserirFinal(novoNo);
            }
        }
    }

    protected int buscarNo(NoCarta nc) {
        int pos = 0;
        NoCarta aux = primeiro;
        while(aux != null) {
            pos++;
            if(aux == nc)
                return pos;

            aux = aux.getProx();
        }
        return 0;
    }

    protected NoCarta buscarNo(int num) {
        int pos = 0;
        NoCarta aux = primeiro;
        while(pos < num) {
            pos++;
            if(pos == num)
                return aux;

            aux = aux.getProx();
        }

        return null;
    }

    public NoCarta recuperaNo(int num) {
        int pos = 1;
        NoCarta aux = primeiro;
        while(aux != null) {
            if(pos == num)
                return aux;

            aux = aux.getProx();
            pos++;
        }

        return null;
    }

    protected void remover(NoCarta nc) {
        int pos = this.buscarNo(nc);
        NoCarta aux = this.primeiro;

        if(pos > 0) {
            if(pos == 1) {
                this.primeiro = null;
                this.ultimo = null;
            } else {
                for(int i=1; i<=pos-2; i++) {
                    aux = aux.getProx();
                }
                aux.setProx(null);
                this.ultimo = aux;
            }
        }
    }

    public NoCarta elementoInicio() {
        if(!listaVazia()) {
            return primeiro;
        } else {
            return null;
        }
    }

    public NoCarta elementoFinal() {
        if(!listaVazia()) {
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

    public String toString(boolean showName) {
        String cards = "";
        if(showName) {
            cards += this.getNome() +": ";
        }
        NoCarta aux = primeiro;
        while(aux != null) {
            cards += aux.getNumero() + "" + aux.getNaipe() + ",";
            aux = aux.getProx();
        }
        return cards;
    }
}