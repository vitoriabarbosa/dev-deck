package devdeck.utils.component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Componente Swing para criar um efeito visual de confetes animados.
 * Os confetes são pequenos círculos coloridos que caem pela tela,
 * simulando uma chuva de confetes.
 */
public class EfeitoConfetes extends JPanel {
    private final List<Confetti> confettiList;
    private final Random random;
    private final Timer timer;

    /**
     * Construtor que inicializa o painel com o efeito de confetes.
     *
     * @param screenSize Dimensão da tela para configurar o tamanho do painel.
     */
    public EfeitoConfetes(Dimension screenSize) {
        this.confettiList = new ArrayList<>();
        this.random = new Random();
        setOpaque(false);
        setPreferredSize(screenSize);

        // Timer para animar os confetes
        timer = new Timer(50, e -> {
            for (Confetti confetti : confettiList) {
                confetti.updatePosition(screenSize);
            }
            repaint();
        });
    }

    /**
     * Inicia o efeito de confetes, gerando os confetes de forma aleatória.
     */
    public void startConfettiEffect() {
        confettiList.clear();
        Dimension size = getPreferredSize();

        // Gerar confetes aleatórios
        for (int i = 0; i < 200; i++) {
            confettiList.add(new Confetti(random.nextInt(size.width), random.nextInt(size.height)));
        }
        timer.start();
    }

    /**
     * Interrompe o efeito de confetes e para a animação.
     */
    public void stopConfettiEffect() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Habilitar suavização
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Desenha cada confete com transparência
        for (Confetti confetti : confettiList) {
            confetti.draw(g2d);
        }
    }

    /**
     * Classe interna para representar um confete individual.
     */
    private class Confetti {
        private int x, y, size, velocidadeX, velocidadeY;

        /**
         * Construtor que inicializa um confete em uma posição aleatória.
         *
         * @param x Posição horizontal inicial do confete.
         * @param y Posição vertical inicial do confete.
         */
        public Confetti(int x, int y) {
            this.x = x;
            this.y = y;
            this.size = random.nextInt(15) + 5; // Tamanho entre 5 e 20
            this.velocidadeX = random.nextInt(5) - 2; // Velocidade horizontal (-2 a +2)
            this.velocidadeY = random.nextInt(5) + 1; // Velocidade vertical (1 a 5)
        }

        /**
         * Atualiza a posição do confete com base em sua velocidade.
         * Se sair da tela, o confete é reposicionado no topo ou nas laterais.
         *
         * @param screenSize Dimensão da tela para verificar limites.
         */
        public void updatePosition(Dimension screenSize) {
            x += velocidadeX;
            y += velocidadeY;

            if (y > screenSize.height) {
                y = 0;
                x = random.nextInt(screenSize.width);
            }
            if (x > screenSize.width) {
                x = 0;
            } else if (x < 0) {
                x = screenSize.width;
            }
        }

        /**
         * Desenha o confete como um círculo colorido com transparência.
         *
         * @param g2d Instância de Graphics2D usada para desenhar.
         */
        public void draw(Graphics2D g2d) {
            // Configura transparência dos confetes
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
            g2d.setComposite(composite);

            // Cor aleatória
            g2d.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

            // Desenha confete
            g2d.fillOval(x, y, size, size);

            // Restaura transparência padrão
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }
}