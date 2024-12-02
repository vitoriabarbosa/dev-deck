package devdeck.utils.component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EfeitoConfetes extends JPanel {
    private final List<Confetti> confettiList;
    private final Random random;
    private final Timer timer;

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

    // Inicia o efeito de confetes
    public void startConfettiEffect() {
        confettiList.clear();
        Dimension size = getPreferredSize();

        // Gerar confetes aleatórios
        for (int i = 0; i < 200; i++) {
            confettiList.add(new Confetti(random.nextInt(size.width), random.nextInt(size.height)));
        }
        timer.start();
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

    // Classe interna para representar um confete
    private class Confetti {
        private int x, y, size, velocidadeX, velocidadeY;

        public Confetti(int x, int y) {
            this.x = x;
            this.y = y;
            this.size = random.nextInt(15) + 5;
            this.velocidadeX = random.nextInt(5) - 2;    // Velocidade aleatória horizontal
            this.velocidadeY = random.nextInt(5) + 1;    // Velocidade aleatória vertical
        }

        // Atualiza a posição do confete
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

        // Desenha o confete com transparência
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

    public void stopConfettiEffect() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }
}