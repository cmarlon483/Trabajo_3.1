package Fes.aragon;

import java.util.Random;

class BankSimulation {
    static Random rd = new Random();
    static int Option(int percents[]) {
        int i = 0, perc, choice = Math.abs(rd.nextInt()) % 100 + 1;
        for (perc = percents[0]; perc < choice; perc += percents[i++]);
        return i;
    }

    public static void main(String args[]) {
        int[] arrivals = {15,20,25,10,30};
        int[] service = {0,0,0,10,5,10,10,0,15,25,10,15};
        int[] clerks = {0,0,0,0};
        int clerkssize = clerks.length;
        int customers, i, t, numOfMinutes = 100, x;
        double maxWait = 0.0, thereIsLine = 0.0, currWait = 0.0;
        Queue simulQ = new Queue();

        System.out.print("t = ");
        for (t = 0; t < numOfMinutes; t++) {
            System.out.print(t + " ");
            for (i = 0; i < clerkssize; i++) {
                if (clerks[i] < 60)
                    clerks[i] += 1;   // con máximo de 60 segundos del tiempo faltante para que el cajero atienda al cliente actual;
                else clerks[i] = 0;   // al cliente actual;
            }
            customers = Option(arrivals);
            for (i = 0; i < customers; i++) {   // inserta todos los clientes nuevos al final
                x = Option(service) * 10;      // (o el tiempo de servicio que requieren);
                simulQ.enqueue(new Integer(x));
                currWait += x;
            }
            // extrae los clientes cuando los cajeros están disponibles:
            for (i = 0; i < clerkssize && !simulQ.isEmpty(); ) {
                if (clerks[i] < 60) {
                    x = ((Integer) simulQ.dequeue()).intValue();
                    clerks[i] += x;   // a un cajero si el tiempo de servicio aún es menor que 60 segundos;
                    currWait -= x;
                }
                else i++;
            }
            if (!simulQ.isEmpty()) {
                thereIsLine++;
                System.out.print(" wait = " + ((long)(currWait/6.0)) / 10.0);
                if (maxWait < currWait)
                    maxWait = currWait;
            } else System.out.print(" wait = 0;");
        }
        System.out.println("\nFor " + clerkssize + " cajeros, había una fila "
                + thereIsLine/numOfMinutes*100.0 + "% del tiempo;\n"
                + "el tiempo de espera máximo fue " + maxWait/60.0 + " min.");
    }
}
