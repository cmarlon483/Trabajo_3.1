package Fes.aragon;

import java.util.Scanner;
import java.util.Stack;

    public class InfixToPostfix {
        public static final int MAXCOLS = 80;

        public static void main(String[] args) {
            char[] infix = new char[MAXCOLS];
            char[] postfix = new char[MAXCOLS];
            int pos = 0;

            // Leer la expresión infija desde la entrada
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduce la expresión infija: ");
            String expression = scanner.nextLine();
            infix = expression.toCharArray();

            // Llamada a la función que convierte a notación postfija

            convertToPostfix(infix, postfix);

            // Mostrar el resultado
            System.out.printf("La expresión infija original es: %s\n", new String(infix).trim());
            System.out.printf("La expresión postfija es: %s\n", new String(postfix).trim());

            scanner.close();
        }

        // Método que convierte una expresión infija a postfija
        public static void convertToPostfix(char[] infix, char[] postfix) {
            int position = 0, outpos = 0;
            char symb;
            Stack<Character> opstk = new Stack<>();

            for (position = 0; position < infix.length && infix[position] != '\0'; position++) {
                symb = infix[position];

                if (isOperand(symb)) {
                    postfix[outpos++] = symb;
                } else if (symb == '(') {
                    opstk.push(symb);
                } else if (symb == ')') {
                    while (!opstk.isEmpty() && opstk.peek() != '(') {
                        postfix[outpos++] = opstk.pop();
                    }
                    opstk.pop(); // Sacar el '('
                } else {
                    while (!opstk.isEmpty() && precedence(opstk.peek()) >= precedence(symb)) {
                        postfix[outpos++] = opstk.pop();
                    }
                    opstk.push(symb);
                }
            }

            while (!opstk.isEmpty()) {
                postfix[outpos++] = opstk.pop();
            }

            // Agregar fin de cadena
            postfix[outpos] = '\0';
        }

        // Método para verificar si es un operando
        public static boolean isOperand(char c) {
            return Character.isLetterOrDigit(c);
        }

        // Método para definir la precedencia de los operadores
        public static int precedence(char c) {
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
