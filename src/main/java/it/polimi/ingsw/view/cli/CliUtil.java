package it.polimi.ingsw.view.cli;

enum ColorCode{
    DEFAULT("\033[0m"),
    GREEN("gre");
    private final String code;
    ColorCode(String code){
        this.code = code;
    }

    String getCode() {
        return code;
    }

}
public class CliUtil {
    public static String makeBoard(Character[] matrix) {
        String green = ColorCode.GREEN.getCode();
        String reset = ColorCode.DEFAULT.getCode();

        StringBuilder builder = new StringBuilder();

        int[][] matrice = new int[9][9];

        // Popolamento della matrice con valori di esempio
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                matrice[i][j] = i + j;
            }
        }

        // Stampa della matrice con linee verdi
        for (int i = 0; i < matrice.length; i++) {
            // Linea superiore della cella
            System.out.print(green + "┌─────" + reset);
            for (int j = 1; j < matrice[i].length; j++) {
                // Linee orizzontali della cella
                System.out.print(green + "┬─────" + reset);
            }
            // Linea superiore destra della cella
            System.out.println(green + "┐" + reset);

            // Contenuto della cella
            for (int j = 0; j < matrice[i].length; j++) {
                System.out.printf("%s│%4d ", green, matrice[i][j]); // %4d indica uno spazio di 4 cifre per l'intero
            }
            System.out.println(green + "│" + reset); // Linea verticale destra della cella

            // Linea inferiore della cella
            if (i == matrice.length - 1) {
                System.out.print(green + "└─────" + reset);
                for (int j = 1; j < matrice[i].length; j++) {
                    System.out.print(green + "┴─────" + reset);
                }
                System.out.println(green + "┘" + reset);
            } else {
                System.out.print(green + "├─────" + reset);
                for (int j = 1; j < matrice[i].length; j++) {
                    System.out.print(green + "┼─────" + reset);
                }
                System.out.println(green + "┤" + reset);
            }
        }
    }

    public static String makeTitle(String title) {
        int titleLength = title.length();
        int delimiterLength = 80 - titleLength - 2;
        int delimiterLeftLength = delimiterLength / 2;
        int delimiterRightLength = delimiterLength - delimiterLeftLength;

        String delimiterLeft = "-".repeat(delimiterLeftLength);
        String delimiterRight = "-".repeat(delimiterRightLength);

        String res;
        res = ("\u001B[31m--------------------------------------------------------------------------------\n" +
                "|"+ delimiterLeft + " " + title + " " + delimiterRight + "|\n" +
                "--------------------------------------------------------------------------------\u001B[0m\n");

        return res;
    }


}
