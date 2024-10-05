package devdeck.model.home;

import devdeck.exceptions.MovimentosInvalidos;
import devdeck.model.NoCarta;
import devdeck.model.base.Base;

/**
 * A interface {@code Home} define a estrutura para os componentes
 * que podem conter cartas no jogo. Qualquer classe que implemente
 * esta interface deve fornecer a funcionalidade para receber,
 * remover cartas e gerenciar uma base associada.
 */
public interface Home {

    /**
     * Recebe um nó de carta e tenta adicioná-lo ao componente
     * "Home". Pode lançar uma exceção caso o movimento seja
     * inválido.
     *
     * @param carta O {@code NoCarta} a ser recebido.
     * @throws MovimentosInvalidos Se a carta não puder ser
     *                             recebida devido a regras de
     *                             movimentação.
     */
    void receberNo(NoCarta carta) throws MovimentosInvalidos;

    /**
     * Remove uma carta do componente "Home".
     *
     * @param nc O {@code NoCarta} a ser removido.
     */
    void remover(NoCarta nc);

    /**
     * Retorna a base associada a este componente "Home".
     *
     * @return A {@code Base} correspondente a este componente.
     */
    Base getBase();

    /**
     * Define a base associada a este componente "Home".
     *
     * @param base A nova base a ser associada.
     */
    void setBase(Base base);
}
