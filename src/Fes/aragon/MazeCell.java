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
        MazeCell cell = (MazeCell) obj;
        return x == cell.x && y == cell.y;
    }
}

class Maze {
    private int rows = 0;
    private int cols = 0;
    private char[][] store;
    private MazeCell currentCell, exitCell = new MazeCell(), entryCell = new MazeCell();
    private final char exitMarker = 'e', entryMarker = 'm', visited = '.';
    private final char passage = '0', wall = '1';
    private Stack<MazeCell> mazeStack = new Stack<>();

    public Maze() {
        int row = 0;
        int col = 0;
        Stack<String> mazeRows = new Stack<>();
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(isr);
        System.out.println("Introduzca un laberinto rectangular utilizando los caracteres "
                + "siguientes:\nm - entry\ne - exit\n1 - wall\n0 - passage\n"
                + "Introduce una línea a la vez; termine con Ctrl-z:");

        try {
            String str = buffer.readLine();
            while (str != null) {
                row++;
                cols = str.length();
                str = "1" + str + "1"; // pone 1 en las celdas del borde
                mazeRows.push(str);
                if (str.indexOf(exitMarker) != -1) {
                    exitCell.x = row;
                    exitCell.y = str.indexOf(exitMarker);
                }
                if (str.indexOf(entryMarker) != -1) {
                    entryCell.x = row;
                    entryCell.y = str.indexOf(entryMarker);
                }
                str = buffer.readLine();
            }
        } catch (IOException eof) {
            System.out.println("Error de entrada: " + eof.getMessage());
        }

        rows = row;
        store = new char[rows + 2][cols + 2]; // crea un arreglo 1D de arreglos char
        store[0] = new char[cols + 2]; // una fila de borde
        for (; !mazeRows.isEmpty(); row--) {
            store[row] = mazeRows.pop().toCharArray();
        }
        store[rows + 1] = new char[cols + 2]; // otra fila de borde

        for (col = 0; col <= cols + 1; col++) {
            store[0][col] = wall; // llena las filas de borde con 1
            store[rows + 1][col] = wall;
        }
    }

    private void display(PrintStream out) {
        for (int row = 0; row <= rows + 1; row++) {
            out.println(store[row]);
        }
        out.println();
    }

    private void pushUnvisited(int row, int col) {
        if (store[row][col] == passage || store[row][col] == exitMarker)
            mazeStack.push(new MazeCell(row, col));
    }

    public void exitMaze(PrintStream out) {
        currentCell = entryCell;
        out.println();

        while (!currentCell.equals(exitCell)) {
            int row = currentCell.x;
            int col = currentCell.y;
            display(out); // imprime una imagen instantánea
            if (!currentCell.equals(entryCell))
                store[row][col] = visited;
            pushUnvisited(row - 1, col); // arriba
            pushUnvisited(row + 1, col); // abajo
            pushUnvisited(row, col - 1); // izquierda
            pushUnvisited(row, col + 1); // derecha

            if (mazeStack.isEmpty()) {
                display(out);
                out.println("Intento fallido");
                return;
            } else {
                currentCell = mazeStack.pop();
            }
        }
        display(out);
        out.println("Intento exitoso");
    }

    public static void main(String[] args) {
        (new Maze()).exitMaze(System.out);
    }
}
