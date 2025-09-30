
public class metodo_montecarlo {
    public static double calcularPI(int numPuntos) {
        int puntosDentro = 0;
        for (int i = 0; i < numPuntos; i++) {
            double x = Math.random();
            double y = Math.random();
            if (x * x + y * y <= 1) {
                puntosDentro++;
            }
        }
        return 4.0 * puntosDentro / numPuntos;
    }

    public static void main(String[] args) {
        int numPuntos = 1_000_000;
        long start = System.currentTimeMillis();
        double pi = calcularPI(numPuntos);
        long end = System.currentTimeMillis();

        System.out.println("Aproximación de PI usando Monte Carlo: " + pi);
        System.out.println("Tiempo: " + (end - start) + " ms");
    }
}


