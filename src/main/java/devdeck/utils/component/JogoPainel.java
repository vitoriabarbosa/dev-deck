package devdeck.utils.component;

import javax.swing.*;

/**
 * Classe que representa um painel personalizado para o jogo.
 * Esta classe estende {@code JPanel} e desativa a otimização de desenho padrão.
 */
public class JogoPainel extends JPanel {

    /**
     * Indica se a otimização de desenho está habilitada.
     * Neste caso, retorna {@code false} para permitir uma atualização mais completa do painel.
     *
     * @return {@code false}, desativando a otimização de desenho.
     */
    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }
}
