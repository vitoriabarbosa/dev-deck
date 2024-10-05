package devdeck.model.base;

import devdeck.model.NoCarta;

/**
 * A classe {@code ListaCarta} representa uma lista ligada de cartas
 * (objetos do tipo {@code NoCarta}). Esta classe fornece métodos para
 * manipular a lista, como inserir, remover e recuperar cartas, bem como
 * verificar se a lista está vazia.
 */
public class ListaCarta {

    /**
     * O primeiro nó da lista.
     */
    public NoCarta primeiro = null;

    /**
     * O último nó da lista.
     */
    public NoCarta ultimo = null;

    /**
     * O nome da lista de cartas.
     */
    private String nome;

    /**
     * Construtor que cria uma nova instância de {@code ListaCarta} com
     * um nome especificado.
     *
     * @param nome O nome da lista de cartas.
     */
    public ListaCarta(String nome) {
        this.nome = nome;
    }

    /**
     * Verifica se a lista está vazia.
     *
     * @return {@code true} se a lista estiver vazia; {@code false} caso contrário.
     */
    public boolean listaVazia() {
        return primeiro == null;
    }

    /**
     * Insere um novo nó no final da lista de cartas.
     *
     * @param novoNo O nó a ser inserido.
     */
    protected void inserirFinal(NoCarta novoNo) {
        NoCarta novoUltimo = novoNo;
        if (listaVazia()) {
            primeiro = novoNo;
        } else {
            // Busca o último
            while (novoUltimo.getProx() != null) {
                novoUltimo = novoUltimo.getProx();
            }
            ultimo.setProx(novoNo);
        }
        ultimo = novoUltimo;
    }

    /**
     * Conta o número de nós na lista.
     *
     * @return O número de nós na lista.
     */
    public int contarNos() {
        int i = 0;
        NoCarta aux = primeiro;
        while (aux != null) {
            aux = aux.getProx();
            i++;
        }
        return i;
    }

    /**
     * Busca a posição de um nó específico na lista.
     *
     * @param nc O nó a ser buscado.
     * @return A posição do nó na lista; 0 se o nó não estiver presente.
     */
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

    /**
     * Recupera um nó da lista com base em sua posição.
     *
     * @param num A posição do nó a ser recuperado (1-based).
     * @return O nó na posição especificada; {@code null} se não houver nó nessa posição.
     */
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
     * A lógica de remoção faz com que o penúltimo nó se torne o último
     * (todos os nós subsequentes serão removidos).
     *
     * @param noCarta O nó a ser removido.
     */
    protected void remover(NoCarta noCarta) {
        int pos = this.buscarNo(noCarta);
        NoCarta aux = this.primeiro;

        if (pos > 0) {
            if (pos == 1) {
                this.primeiro = null;
                this.ultimo = null;
            } else {
                for (int i = 1; i <= pos - 2; i++) {
                    aux = aux.getProx();
                }
                aux.setProx(null);
                this.ultimo = aux;
            }
        }
    }

    /**
     * Retorna o último nó da lista.
     *
     * @return O último nó da lista; {@code null} se a lista estiver vazia.
     */
    public NoCarta elementoFinal() {
        if (!listaVazia()) {
            return ultimo;
        } else {
            return null;
        }
    }

    /**
     * Retorna o nome da lista de cartas.
     *
     * @return O nome da lista.
     */
    public String getNome() {
        return this.nome;
    }

    @Override
    public String toString() {
        return this.toString(false);
    }

    /**
     * Retorna uma representação em string da lista de cartas.
     *
     * @param mostrarNome Se {@code true}, o nome da lista será incluído na representação.
     * @return A representação em string da lista de cartas.
     */
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
