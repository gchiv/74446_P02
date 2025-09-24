import java.util.concurrent.ThreadLocalRandom;

public class metodo_montecarlo_hilos {

    static class Worker extends Thread {
        private final int numPuntos;
        private int puntosDentro = 0;

        Worker(int numPuntos) {
            this.numPuntos = numPuntos;
        }

        @Override
        public void run() {
            for (int i = 0; i < numPuntos; i++) {
                double x = ThreadLocalRandom.current().nextDouble();
                double y = ThreadLocalRandom.current().nextDouble();
                if (x * x + y * y <= 1) {
                    puntosDentro++;
                }
            }
        }

        public int getPuntosDentro() {
            return puntosDentro;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int numPuntos = 1_000_000;
        int numHilos = 4;
        int puntosPorHilo = numPuntos / numHilos;

        Worker[] hilos = new Worker[numHilos];
        long start = System.currentTimeMillis();

        for (int i = 0; i < numHilos; i++) {
            hilos[i] = new Worker(puntosPorHilo);
            hilos[i].start();
        }

        int totalDentro = 0;
        for (Worker hilo : hilos) {
            hilo.join();
            totalDentro += hilo.getPuntosDentro();
        }

        double pi = 4.0 * totalDentro / numPuntos;
        long end = System.currentTimeMillis();

        System.out.println("Aproximación de PI usando Monte Carlo con hilos: " + pi);
        System.out.println("Tiempo: " + (end - start) + " ms");
    }
}
