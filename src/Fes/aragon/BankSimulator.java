package Fes.aragon;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BankSimulator {
    static Random aleatorio = new Random();

    static int opcion(int porcentajes[]) {
        int acumulado = 0;
        int eleccion = aleatorio.nextInt(100) + 1;
        for (int i = 0; i < porcentajes.length; i++) {
            acumulado += porcentajes[i];
            if (eleccion <= acumulado) {
                return i;
            }
        }
        return porcentajes.length - 1; // por defecto al último si no se elige ninguno
    }

    public static void main(String args[]) {
        int[] llegadas = {15, 20, 25, 10, 30};
        int[] servicio = {0, 0, 0, 10, 5, 10, 10, 0, 15, 25, 10, 15};
        int[] oficinistas = {0, 0, 0, 0};
        int numOficinistas = oficinistas.length;
        int clientes, tiempo, minutosSimulacion = 40, x;
        double maxEspera = 0.0, huboFila = 0.0, esperaActual = 0.0;
        Queue<Integer> colaSimulacion = new LinkedList<>();

        System.out.print("tiempo = ");
        for (tiempo = 0; tiempo < minutosSimulacion; tiempo++) {
            System.out.print(tiempo + " ");
            for (int i = 0; i < numOficinistas; i++) {
                oficinistas[i] = Math.min(oficinistas[i] + 1, 60);
            }

            clientes = opcion(llegadas);
            for (int i = 0; i < clientes; i++) {
                x = opcion(servicio) * 10;
                colaSimulacion.add(x);
                esperaActual += x;
            }

            for (int i = 0; i < numOficinistas && !colaSimulacion.isEmpty(); ) {
                if (oficinistas[i] == 60) {
                    x = colaSimulacion.poll();
                    oficinistas[i] = x;
                    esperaActual -= x;
                } else {
                    i++;
                }
            }

            if (!colaSimulacion.isEmpty()) {
                huboFila++;
                System.out.print(" El cliente espero = " + ((long) (esperaActual / 6.0)) / 10.0);
                maxEspera = Math.max(maxEspera, esperaActual);
            } else {
                System.out.print(" El cliente espero = 0");
            }
        }

        System.out.println("\nPara " + numOficinistas + " oficinistas, hubo una fila el "
                + huboFila / minutosSimulacion * 100.0 + "% del tiempo;\n"
                + "El tiempo máximo de espera fue " + maxEspera / 60.0 + " minutos.");
    }
}
