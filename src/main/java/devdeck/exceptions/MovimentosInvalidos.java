package devdeck.exceptions;

/**
 * Exceção que será lançada quando um movimento inválido for realizado.
 * Esta classe estende {@code IOException} e fornece códigos de erro
 * para diferentes tipos de movimentos inválidos que podem ocorrer no jogo.
 */
public class MovimentosInvalidos extends java.io.IOException {
    public final static int GENERICO = 0;
    public final static int LISTA_NO_FECHADO = 1;
    public final static int LISTA_NO_INVALIDO = 2;
    public final static int LISTA_VAZIA_APENAS_REIS = 3;
    public final static int LISTA_SEQUENCIA_INVALIDA = 4;
    public final static int PILHA_NO_SEQUENCIA_INVALIDA = 5;

    private int code = 0;

    /**
     * Construtor da exceção MovimentosInvalidos.
     *
     * @param code Código que representa o tipo de movimento inválido.
     */
    public MovimentosInvalidos(int code) {
        this.code = code;
    }

    /**
     * Obtém o código do movimento inválido.
     *
     * @return O código associado à exceção.
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Obtém uma mensagem descritiva sobre o movimento inválido.
     *
     * @return Uma string com a mensagem de erro formatada em HTML.
     */
    @Override
    public String getMessage() {
        String msg = "<span style=\"color: #0000ff;\">Movimento Inválido</span><br />";
        switch (this.code) {
            case LISTA_NO_FECHADO -> msg += "Não pode arrastar um nó fechado.";
            case LISTA_NO_INVALIDO -> msg += "O nó está na ordem errada.";
            case LISTA_VAZIA_APENAS_REIS -> msg += "Só pode iniciar uma lista com um 7.";
            case LISTA_SEQUENCIA_INVALIDA -> msg += "Mantenha a mesma cor e<br>siga a sequencia: 7, 6, 5, 4, 3, 2, A.";
            case PILHA_NO_SEQUENCIA_INVALIDA -> msg += "Inicie uma pilha com As.";
            default -> msg += "Movimento inválido.";
        }
        return msg;
    }
}
