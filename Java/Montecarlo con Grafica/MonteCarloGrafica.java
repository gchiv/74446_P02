import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MonteCarloGrafica extends JPanel {

    private final int n;
    private final double[] inX;
    private final double[] inY;
    private final double[] outX;
    private final double[] outY;
    private int inCount;
    private int outCount;
    private double piEstimate;

    public MonteCarloGrafica(int numPoints) {
        this.n = numPoints;
        this.inX = new double[n];
        this.inY = new double[n];
        this.outX = new double[n];
        this.outY = new double[n];
        simulate();
    }

    private void simulate() {
        Random rand = new Random();
        int inside = 0;
        int idxIn = 0;
        int idxOut = 0;

        for (int i = 1; i <= n; i++) {
            double x = rand.nextDouble() * 2 - 1;
            double y = rand.nextDouble() * 2 - 1;

            if (x * x + y * y <= 1) {
                if (idxIn < n) {
                    inX[idxIn] = x;
                    inY[idxIn] = y;
                    idxIn++;
                }
                inside++;
            } else {
                if (idxOut < n) {
                    outX[idxOut] = x;
                    outY[idxOut] = y;
                    idxOut++;
                }
            }
            
            this.piEstimate = 4.0 * inside / i;
        }

        this.inCount = idxIn;
        this.outCount = idxOut;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        int size = Math.min(w, h) - 20;
        int mX = (w - size) / 2;
        int mY = (h - size) / 2;

        g.setColor(Color.BLACK);
        g.drawOval(mX, mY, size, size);

        g.setColor(new Color(34, 139, 34));
        for (int i = 0; i < inCount; i++) {
            int px = (int) (w / 2 + inX[i] * (size / 2.0));
            int py = (int) (h / 2 - inY[i] * (size / 2.0));
            g.fillOval(px - 1, py - 1, 3, 3);
        }

        g.setColor(new Color(255, 140, 0));
        for (int i = 0; i < outCount; i++) {
            int px = (int) (w / 2 + outX[i] * (size / 2.0));
            int py = (int) (h / 2 - outY[i] * (size / 2.0));
            g.fillOval(px - 1, py - 1, 3, 3);
        }

        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Verdana", Font.BOLD, 20));
        String text = String.format("Estimación de π ≈ %.7f", piEstimate);
        g.drawString(text, 25, 35);
    }

    public void rerun() {
        simulate();
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Estimación de Pi con Monte Carlo");
        
        MonteCarloGrafica panel = new MonteCarloGrafica(15000);

        JButton button = new JButton("Ejecutar de nuevo");
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.addActionListener(e -> panel.rerun());

        JPanel container = new JPanel(new BorderLayout());
        container.add(panel, BorderLayout.CENTER);
        container.add(button, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(container);
        frame.setSize(700, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}