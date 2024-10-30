package Fes.aragon;

import java.util.Random;

class BankSimulation {
    static Random rd = new Random();
    static int Option(int porciento[]) { //no supe si moverle
        int i = 0, porc, eleccion = Math.abs(rd.nextInt()) % 100 + 1;
        for (porc = porciento[0]; porc < eleccion; porc += porciento[i++]);
        return i;
    }

    public static void main(String args[]) {
        int[] llegada= {15,20,25,10,30};
        int[] servicio = {0,0,0,10,5,10,10,0,15,25,10,15};
        int[] empleados= {0,0,0,0};
        int numEmpleados = empleados.length;
        int clientes, i, t, numMinutos = 100, x;
        double esperaMax = 0.0, libre = 0.0, esperaActual = 0.0;
        Queue simulacion = new Queue();

        System.out.print("t = ");
        for (t = 0; t < numMinutos; t++) {
            System.out.print(t + " ");
            for (i = 0; i < numEmpleados; i++) {
                if (empleados[i] < 60)
                    empleados[i] += 1;   // con máximo de 60 segundos del tiempo faltante para que el cajero atienda al cliente actual;
                else empleados[i] = 0;   // al cliente actual;
            }
            clientes = Option(llegada); //<----
            for (i = 0; i < clientes; i++) {   // inserta todos los clientes nuevos al final
                x = Option(servicio) * 10;  //<----    // (o el tiempo de servicio que requieren);
                simulacion.enqueue(new Integer(x));
                esperaActual += x;
            }
            // extrae los clientes cuando los cajeros están disponibles:
            for (i = 0; i < numEmpleados && !simulacion.isEmpty(); ) {
                if (empleados[i] < 60) {
                    x = ((Integer) simulacion.dequeue()).intValue();
                    empleados[i] += x;   // a un cajero si el tiempo de servicio aún es menor que 60 segundos;
                    esperaActual -= x;
                }
                else i++;
            }
            if (!simulacion.isEmpty()) {
                libre++;
                System.out.print(" espera = " + ((long)(esperaActual/6.0)) / 10.0);
                if (esperaMax < esperaActual)
                    esperaMax = esperaActual;
            } else System.out.print(" espera = 0;");
        }
        System.out.println("\nFor " + numEmpleados + " cajeros, había una fila "
                + libre/numMinutos*100.0 + "% del tiempo;\n"
                + "el tiempo de espera máximo fue " + esperaMax/60.0 + " min.");
    }
}
