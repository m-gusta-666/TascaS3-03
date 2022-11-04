package nivell1;


import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilities {

    public static final String ANSI_RESET = "\u001B[0m",
                                ANSI_YELLOW = "\u001B[33m",
                                ANSI_BLUE = "\u001B[32m",
                                ANSI_RED = "\u001B[31m";
    static Scanner scanner = new Scanner(System.in);
/*
    public static void screenMenu() {

        System.out.println();

        System.out.println("               __/)  \n" +
                "            .-(__(=:       " + "\u001B[32m" + "FLORISTERIA PEPA \n" + "\u001B[0m" +
                "            |    \\)\n" +
                "     (\\__  |               1 - AFEGIR PRODUCTE" +
                "\n" +
                "     :=)__)-|  __/)        2 - RETIRAR PRODUCTE\n" +
                "      (/    |-(__(=:       3 - IMPRIMIR STOCK TOTES PRODUCTES\n" +
                "    ______  |  _ \\)        4 - IMPRIMIR STOCK AMB QUANTITATS\n" +
                "   /      \\ | / \\          5 - MOSTRAR COMPRES ANTIGUES\n" +
                "        ___\\|/___\\\n" +
                "       [         ]\\        6 - CREAR TIQUET DE VENDA\n" +
                "        \\       /  \\\n" +
                "         \\     /        " + String.format("%s: %9s ",  "   TOTAL VENUT",  valorTotalVendes)  +
                "\n          \\___/            " + String.format("%s %9s","VALOR STOCK:" + ANSI_BLUE,  valorTotalStock) + ANSI_RESET
                + (valorTotalStock.equalsIgnoreCase("0,00") ? (ANSI_RED + " (Botiga buida)" + ANSI_RESET) : "") + "\n" +
                "--------------------------------------------------------------");

    }
*/
    public static void printClassOfProducts() {
        System.out.println(tabulate() + " ARBRE  -  FLOR  -  DECORACIÓ  - " + ANSI_BLUE + " SORTIR" + ANSI_RESET);
        System.out.println(tabulate() + "  [1]       [2]        [3]     "   + ANSI_BLUE + "    [ 0 ]" );
        System.out.println(tabulate() + printSymbol('-', 52)   + ANSI_RESET);
    }
    public static String printSymbol(char e, int nTimes) {
        return String.valueOf(e).repeat(nTimes);
    }


    public static String tabulate() {
        return "\t\t";
    }

    public static void pressEnterToContinue() {
        System.out.print("\n\tPrem Enter per continuar...");
        try {
            System.in.read();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void printProductesHead(String message) {

        System.out.println(ANSI_YELLOW + "\n\n\n\tLLISTAT DE PRODUCTES " + message);
        printLines( 105,'-');

        System.out.printf("%-3s %-20s %-15s %s %24s %25s\n", "ID", " NOM"
                , " TIPUS" , "PECULIARITAT", "PREU", "OBJECT_ID");
        printLines( 105,'-');
    }

    public static void printLines(int nTimes, char sSymbol){
        System.out.println( ANSI_YELLOW + printSymbol(sSymbol, nTimes) + ANSI_RESET);
    }


    /**
     * Control data imput
     *
     * * **/

    public static int requireIntNumber(String message) {

        int num;

        System.out.println(message);
        num = scanner.nextInt();

        return num;
    }

    public static String requireString(String message) {

        String string;

        System.out.print(message);
        string = scanner.nextLine();

        return string;
    }

    public static int inputDataInt(String missatge) {
        // Collect data of Integer type
        int dataInput = -1;
        boolean correct = false;

        while (!correct) {
            try {
                System.out.print(missatge);
                dataInput = scanner.nextInt();
                if (dataInput < 1 && dataInput != 0) {
                    System.out.println("\u001B[31m" + "¡Número incorrecte!" + "\u001B[0m");
                    //correct = true;

                } else {

                    return dataInput;
                }

            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println(tabulate() + "\u001B[31m" + "¡Error de formato de entrada!" + "\u001B[0m");

            }
        }
        return dataInput;
    }


    // Format a double value for european currency
    protected static String doubleFormatProperly(double totalStock) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        df.setMaximumFractionDigits(2);
        return df.format(totalStock);
    }

}
