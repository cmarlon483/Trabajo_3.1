package Fes.aragon;

import java.util.Scanner;
import java.util.Stack;

    public class InfixToPostfix {
        public static final int MAXCOLS = 80;

        public static void main(String[] args) {
            char[] infija = new char[MAXCOLS];
            char[] posfija = new char[MAXCOLS];
            int pos = 0;

            // Leer la expresión infija desde la entrada
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduce la expresión infija: ");
            String expresion = scanner.nextLine();
            infix = expresion.toCharArray();

            // Llamada a la función que convierte a notación postfija

            convertirAPosfija(infija, posfija);

            // Mostrar el resultado
            System.out.printf("La expresión infija original es: %s\n", new String(infija).trim());
            System.out.printf("La expresión postfija es: %s\n", new String(posfija).trim());

            scanner.close();
        }

        // Método que convierte una expresión infija a postfija
        public static void convertirAPosfija(char[] infija, char[] posfija) {
            int posicion = 0, salidaPos = 0;
            char simbolo;
            Stack<Character> pila = new Stack<>();

            for (posicion = 0; posicion < infija.length && infija[posicion] != '\0'; posicion++) {
                simbolo = infija[posicion];

                if (isOperand(simbolo)) {
                    postfija[saliapos++] = simbolo;
                } else if (simbolo == '(') {
                    pila.push(simbolo);
                } else if (simbolo == ')') {
                    while (!pila.isEmpty() && pila.peek() != '(') {
                        posfija[salidapos++] = pila.pop();
                    }
                    pila.pop(); // Sacar el '('
                } else {
                    while (!pila.isEmpty() && precedence(pila.peek()) >= precedence(simbolo)) {
                        posfija[salidapos++] = pila.pop();
                    }
                    pila.push(simbolo);
                }
            }

            while (!pila.isEmpty()) {
                postfija[salidapos++] = pila.pop();
            }

            // Agregar fin de cadena
            postfija[salidapos] = '\0';
        }

        // Método para verificar si es un operando
        public static boolean esOperando(char c) {
            return Character.isLetterOrDigit(c);
        }

        // Método para definir la precedencia de los operadores
        public static int precendencia(char c) {
            switch (c) {
                case '+':
                case '-':
                    return 1;
                case '*':
                case '/':
                    return 2;
                case '^':
                    return 3;
                default:
                    return -1;
            }
        }
    }
