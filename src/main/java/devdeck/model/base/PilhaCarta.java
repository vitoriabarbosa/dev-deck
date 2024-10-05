package devdeck.model.base;

import devdeck.model.NoCarta;

/**
 * A classe {@code PilhaCarta} representa uma pilha de cartas
 * em um jogo de cartas. Esta estrutura permite o empilhamento e
 * desempilhamento de objetos do tipo {@code NoCarta},
 * mantendo o controle do elemento no topo da pilha.
 */
public class PilhaCarta {

    /**
     * O índice do elemento no topo da pilha.
     */
    private int topo = -1;

    /**
     * O array que contém as cartas na pilha.
     */
    protected NoCarta[] cartas;

    /**
     * O nome da pilha.
     */
    private String nome;

    /**
     * Construtor que cria uma nova instância de {@code PilhaCarta}
     * com um nome específico.
     *
     * @param nome O nome da pilha de cartas.
     */
    public PilhaCarta(String nome) {
        this.nome = nome;
    }

    /**
     * Verifica se a pilha está vazia.
     *
     * @return {@code true} se a pilha estiver vazia;
     *         {@code false} caso contrário.
     */
    public boolean pilhaVazia() {
        return topo < 0;
    }

    /**
     * Verifica se a pilha está cheia.
     *
     * @return {@code true} se a pilha estiver cheia;
     *         {@code false} caso contrário.
     */
    public boolean pilhaCheia() {
        return topo >= cartas.length - 1;
    }

    /**
     * Retorna o elemento no topo da pilha,
     * sem removê-lo.
     *
     * @return O {@code NoCarta} no topo da pilha,
     *         ou {@code null} se a pilha estiver vazia.
     */
    public NoCarta elementoTopo() {
        if (!pilhaVazia()) {
            return cartas[topo];
        } else {
            return null;
        }
    }

    /**
     * Empilha um novo elemento no topo da pilha.
     *
     * @param elemento O {@code NoCarta} a ser empilhado.
     * @return {@code true} se o elemento foi empilhado com sucesso;
     *         {@code false} se a pilha estiver cheia.
     */
    protected boolean empilhar(NoCarta elemento) {
        if (pilhaCheia()) {
            return false;
        } else {
            cartas[++topo] = elemento;
            return true;
        }
    }

    /**
     * Desempilha o elemento do topo da pilha.
     *
     * @return O {@code NoCarta} que foi desempilhado,
     *         ou {@code null} se a pilha estiver vazia.
     */
    protected NoCarta desempilhar() {
        if (pilhaVazia()) {
            return null;
        } else {
            topo--;
            return cartas[topo + 1];
        }
    }

    /**
     * Retorna o nome da pilha.
     *
     * @return O nome da pilha.
     */
    public String getNome() {
        return this.nome;
    }
}
