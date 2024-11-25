package devdeck.utils.component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EfeitoConfetes extends JPanel {
    private List<Confetti> confettiList;
    private Random random;
    private Timer timer;

    public EfeitoConfetes(Dimension screenSize) {
        this.confettiList = new ArrayList<>();
        this.random = new Random();
        setOpaque(false);
        setPreferredSize(screenSize); // Define o tamanho correto do painel

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
        super.paintComponent(g);  // Chama o método da superclasse
        Graphics2D g2d = (Graphics2D) g;

        // Habilitar suavização
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Limpar a área onde os confetes serão desenhados
        g2d.clearRect(0, 0, getWidth(), getHeight());

        // Desenha cada confete
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

        // Desenha o confete
        public void draw(Graphics2D g2d) {
            g2d.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g2d.fillOval(x, y, size, size);
        }
    }

    public void stopConfettiEffect() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }
}