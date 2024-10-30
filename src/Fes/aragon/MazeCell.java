
package Fes.aragon;

import java.io.*;
import java.util.Stack;

//Ejemplos que se pueden usar

//11111111111
//10000010001
//10100010101
//e0100000101
//10111110101
//10101000101
//10001010001
//11111010001
//101m1010001
//10000010001
//11111111111

//Ejemplo2

//11111
//1m001
//11101
//100e1
//11111

public class MazeCell {
    public int x, y;

    public MazeCell() {}

    public MazeCell(int i, int j) {
        x = i;
        y = j;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MazeCell cell = (MazeCell) obj; //aqui no supe si moverle o no
        return x == cell.x && y == cell.y;
    }
}

class Maze {
    private int filas = 0;
    private int columnas = 0;
    private char[][] memoria;
    private MazeCell CeldaActual, salida = new MazeCell(), entrada = new MazeCell(); //lo mismo
    private final char marcadorSalida = 'e', marcadorEntrada = 'm', visitado = '.';
    private final char camino = '0', muro = '1';
    private Stack<MazeCell> mazeStack = new Stack<>();

    public Maze() {
        int filas = 0;
        int columnas = 0;
        Stack<String> filasLab = new Stack<>();
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(isr);
        System.out.println("Introduzca un laberinto rectangular utilizando los caracteres "
                + "siguientes:\nm - entry\ne - exit\n1 - wall\n0 - passage\n"
                + "Introduce una línea a la vez; termine con Ctrl-z:");

        try {
            String lab = buffer.readLine();
            while (lab != null) {
                filas++;
                columna = lab.length();
                lab = "1" + lab + "1"; // pone 1 en las celdas del borde
                mazeRows.push(lab); //lo mismo
                if (laberinto.indexOf(marcadorSalida) != -1) {
                    salida.x = fila;
                    salida.y = lab.indexOf(marcadorSalida);
                }
                if (lab.indexOf(marcadorEntrada) != -1) {
                    entrada.x = fila;
                    entrada.y = lab.indexOf(marcadorEntrada);
                }
                lab = buffer.readLine();
            }
        } catch (IOException errorDeEntrada) { //anteriormnte "eof"
            System.out.println("Error de entrada: " + errorDeEntrada.getMessage());
        }

        filas = fila;
        memoria = new char[filas + 2][columnas + 2]; // crea un arreglo 1D de arreglos char
        memoria[0] = new char[columnas + 2]; // una fila de borde
        for (; !mazeRows.isEmpty(); fila--) {//lo mismo
            memoria[fila] = mazeRows.pop().toCharArray();
        }
        memoria[filas + 1] = new char[columnas + 2]; // otra fila de borde

        for (columna = 0; columna <= columnas + 1; columna++) {
            memoria[0][col] = muro; // llena las filas de borde con 1
            memoria[filas + 1][columna] = muro;
        }
    }

    private void imprimir(PrintStream out) {
        for (int fila = 0; fila <= filas + 1; fila++) {
            out.println(memoria[fila]);
        }
        out.println();
    }

    private void noVisitado(int fila, int columna) {
        if (memoria[fila][columna] == camino || memoria[fila][columna] == marcadorSalida)
            mazeStack.push(new MazeCell(fila, columna));//lo mismo
    }

    public void exitMaze(PrintStream out) {
        celdaAcutal = entrada;
        out.println();

        while (!celdaActua.equals(salida)) {
            int fila = celdaActual.x;
            int columna = celdaActual.y;
            imprimir(out); // imprime una imagen instantánea
            if (!celdaActual.equals(entrada))
                memoria[fila][columna] = visitado;
            noVisitado(fila - 1, columna); // arriba
            noVisitado(fila + 1, columna); // abajo
            noVisitado(fila, columna - 1); // izquierda
            noVisitado(fila, columna + 1); // derecha

            if (mazeStack.isEmpty()) { //tampoco supe si moverle
                imrpimir(out);
                out.println("Intento fallido");
                return;
            } else {
                celdaActual = mazeStack.pop(); // lo mismo
            }
        }
        imprimir(out);
        out.println("Intento exitoso");
    }

    public static void main(String[] args) {
        (new Maze()).exitMaze(System.out);//lo mismo
    }
}
