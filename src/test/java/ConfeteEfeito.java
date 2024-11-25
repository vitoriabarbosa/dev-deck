import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfeteEfeito extends JPanel {
    private List<Confetti> confettiList;
    private Random random;
    private Timer timer;

    public ConfeteEfeito() {
        this.confettiList = new ArrayList<>();
        this.random = new Random();
        setOpaque(false);  // Tornar o fundo do painel transparente
        setPreferredSize(new Dimension(1800, 1000)); // Definir o tamanho do painel

        // Timer para animar os confetes
        timer = new Timer(50, e -> {
            for (Confetti confetti : confettiList) {
                confetti.updatePosition();  // Atualiza a posição dos confetes
            }
            repaint();  // Redesenha a área
        });
    }

    // Inicia o efeito de confetes
    public void iniciarConfeteEfeito() {
        confettiList.clear();
        // Gerar confetes aleatórios
        for (int i = 0; i < 100; i++) {
            confettiList.add(new Confetti(random.nextInt(1800), random.nextInt(1000)));
        }
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  // Chama o método da superclasse
        Graphics2D g2d = (Graphics2D) g;

        // Limpar a área onde os confetes serão desenhados
        g2d.clearRect(0, 0, getWidth(), getHeight());

        // Desenha cada confete
        for (Confetti confetti : confettiList) {
            confetti.draw(g2d);
        }
    }

    // Classe interna para representar um confete
    private class Confetti {
        private int x, y, size, dx, dy;

        public Confetti(int x, int y) {
            this.x = x;
            this.y = y;
            this.size = random.nextInt(5) + 5;  // Tamanho aleatório do confete
            this.dx = random.nextInt(5) - 2;    // Velocidade aleatória horizontal
            this.dy = random.nextInt(5) + 1;    // Velocidade aleatória vertical
        }

        // Atualiza a posição do confete
        public void updatePosition() {
            this.x += dx;
            this.y += dy;
            if (y > 1000) {  // Se os confetes saírem da tela, reiniciar na parte superior
                y = 0;
                x = random.nextInt(1800);
            }
        }

        // Desenha o confete
        public void draw(Graphics2D g2d) {
            g2d.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g2d.fillOval(x, y, size, size);
        }
    }

    public static void main(String[] args) {
        // Configuração da janela principal
        JFrame telaPrincipal = new JFrame("Tela Principal");
        telaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        telaPrincipal.setSize(1800, 1000);
        telaPrincipal.setLayout(new BorderLayout());
        telaPrincipal.setLocationRelativeTo(null);  // Centraliza a janela
        telaPrincipal.setVisible(true);

        // Criando a janela de sobreposição com transparência
        JWindow telaSopreposicao = new JWindow(telaPrincipal);
        telaSopreposicao.setSize(1800, 1000);
        telaSopreposicao.setLocationRelativeTo(telaPrincipal);  // Fica centralizada sobre a janela principal

        // Tornando a janela totalmente transparente
        telaSopreposicao.setOpacity(0.0f);  // 0.0f é totalmente transparente
        telaSopreposicao.setBackground(new Color(0, 0, 0, 0));  // Remover qualquer cor de fundo

        // Adiciona o painel com confetes à janela de sobreposição
        ConfeteEfeito confeteEfeito = new ConfeteEfeito();
        telaSopreposicao.add(confeteEfeito, BorderLayout.CENTER);
        telaSopreposicao.setVisible(true);

        // Iniciar o efeito de confetes
        confeteEfeito.iniciarConfeteEfeito();
    }
}
