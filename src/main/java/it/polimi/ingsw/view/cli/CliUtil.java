package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.entities.Board;
import it.polimi.ingsw.util.BoardCell;
import it.polimi.ingsw.util.CardType;
import it.polimi.ingsw.util.Cell;

import java.util.ArrayList;

enum ColorCode {
    DEFAULT("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    BLUE("\u001B[34m"),
    L_BLUE("\u001B[94m"),
    MAGENTA("\u001B[35m"),
    WHITE("\u001B[37m"),
    BROWN("\u001B[33m"),
    ORANGE("\u001B[38;2;255;165;0m");

    private final String code;
    ColorCode(String code){
        this.code = code;
    }

    String getCode() {
        return code;
    }

}

enum AsciiTool {
    T("─────┬"),
    LT("├"),
    RT("─────┤\n"),
    BT("─────┴"),
    LT_CORNER("┌"),
    RT_CORNER("─────┐\n"),
    CROSS("─────┼"),
    LB_CORNER("└"),
    RB_CORNER("─────┘\n"),
    L_CONTENT("│  "),
    M_CONTENT("  │  "),
    R_CONTENT("  │\n"),
    SPACES("      "),
    DOT("● "),
    X("\u274C"),
    V("\u2714"),
    USER("\u1F464");

    private final String symbol;
    AsciiTool(String symbol){
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}

public class CliUtil {

    private static String getRowContent(Character[] row) {
        StringBuilder rowContent = new StringBuilder();
        rowContent.append(AsciiTool.L_CONTENT.getSymbol());
        for (int i=0; i<row.length; i++){
            if(row[i] == 'u') continue;
            rowContent.append(row[i]);
            if(i < (row.length-1) && row[i+1]!='u') rowContent.append(AsciiTool.M_CONTENT.getSymbol());
        }
        rowContent.append(AsciiTool.R_CONTENT.getSymbol());
        return rowContent.toString();
    }

    private static String getHeader(int length) {
        length--;
        return AsciiTool.LT_CORNER.getSymbol() + AsciiTool.T.getSymbol().repeat(length) + AsciiTool.RT_CORNER.getSymbol();
    }

    private static String getIntermediate(int length) {
        length--;
        return AsciiTool.LT.getSymbol() + AsciiTool.CROSS.getSymbol().repeat(length) + AsciiTool.RT.getSymbol();
    }

    private static String getFooter(int length) {
        length--;
        return AsciiTool.LB_CORNER.getSymbol() + AsciiTool.BT.getSymbol().repeat(length) + AsciiTool.RB_CORNER.getSymbol();
    }

    public static String makeLegend() {
        String line1 = ColorCode.GREEN.getCode() + AsciiTool.DOT.getSymbol() + "C: Cat\t\t" + ColorCode.WHITE.getCode() + AsciiTool.DOT.getSymbol() + "B: Books\n";
        String line2 = ColorCode.ORANGE.getCode() + AsciiTool.DOT.getSymbol() + "G: Game\t\t" + ColorCode.BLUE.getCode() + AsciiTool.DOT.getSymbol() + "F: Frames\n";
        String line3 = ColorCode.L_BLUE.getCode() + AsciiTool.DOT.getSymbol() + "T: Trophies\t" + ColorCode.MAGENTA.getCode() + AsciiTool.DOT.getSymbol() + "P: Plants\n";

        return line3 + line2 + line1;
    }


    //TODO: funziona, ma da revisionare
    public static String makeBoard(Character[][] matrix) {

        return ColorCode.GREEN.getCode() +
                AsciiTool.SPACES.getSymbol().repeat(3) +
                getHeader(2) +
                AsciiTool.SPACES.getSymbol().repeat(3) +
                getRowContent(matrix[0]) +
                AsciiTool.SPACES.getSymbol().repeat(3) +
                AsciiTool.LT.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(2) +
                AsciiTool.RT_CORNER.getSymbol() +
                AsciiTool.SPACES.getSymbol().repeat(3) +
                getRowContent(matrix[1]) +
                AsciiTool.SPACES.getSymbol().repeat(2) +
                AsciiTool.LT_CORNER.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(4) +
                AsciiTool.RT_CORNER.getSymbol() +
                AsciiTool.SPACES.getSymbol().repeat(2) +
                getRowContent(matrix[2]) +
                AsciiTool.SPACES.getSymbol() +
                AsciiTool.LT_CORNER.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(6) +
                AsciiTool.T.getSymbol() +
                AsciiTool.RT_CORNER.getSymbol() +
                AsciiTool.SPACES.getSymbol() +
                getRowContent(matrix[3]) +
                AsciiTool.LT_CORNER.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(8) +
                AsciiTool.RT.getSymbol() +
                getRowContent(matrix[4]) +
                AsciiTool.LT.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(8) +
                AsciiTool.RB_CORNER.getSymbol() +
                getRowContent(matrix[5]) +
                AsciiTool.LB_CORNER.getSymbol() +
                AsciiTool.BT.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(6) +
                AsciiTool.RB_CORNER.getSymbol() +
                AsciiTool.SPACES.getSymbol().repeat(2) +
                getRowContent(matrix[6]) +
                AsciiTool.SPACES.getSymbol().repeat(2) +
                AsciiTool.LB_CORNER.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(4) +
                AsciiTool.RB_CORNER.getSymbol() +
                AsciiTool.SPACES.getSymbol().repeat(3) +
                getRowContent(matrix[7]) +
                AsciiTool.SPACES.getSymbol().repeat(3) +
                AsciiTool.LB_CORNER.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(2) +
                AsciiTool.RT.getSymbol() +
                AsciiTool.SPACES.getSymbol().repeat(4) +
                getRowContent(matrix[8]) +
                AsciiTool.SPACES.getSymbol().repeat(4) +
                getFooter(2);
    }

    public static String makePlayersList(ArrayList<String> players){
        String partial = "";
        for(String p: players) partial += AsciiTool.DOT.getSymbol() + p + "\n";
        return partial;
    }

    public static String makeBookshelf(Character[][] matrix) {
        String bookshelf = ColorCode.BROWN.getCode() + getHeader(5);
        for(int i=0; i<matrix[0].length-1; i++){
            bookshelf += getRowContent(matrix[i])+
                    getIntermediate(5);
        }
        bookshelf += getRowContent(matrix[matrix[0].length-1]) + getFooter(5);
        return bookshelf;
    }

    private static Character getTypeCharacter(CardType type) {

        switch (type) {
            case FRAMES -> {
                return 'F';
            }
            case CATS -> {
                return 'C';
            }
            case BOOKS -> {
                return 'B';
            }
            case GAMES -> {
                return 'G';
            }
            case PLANTS -> {
                return 'P';
            }
            case TROPHIES -> {
                return 'T';
            }
            default -> {
                return null;
            }
        }
    }

    public static Character[][] boardConverter(BoardCell[][] tmp) {

        Character result[][] = new Character[][]{
                {'u', 'u', 'u', ' ', ' ', 'u', 'u', 'u', 'u'},
                {'u', 'u', 'u', ' ', ' ', ' ', 'u', 'u', 'u'},
                {'u', 'u', ' ', ' ', ' ', ' ', ' ', 'u', 'u'},
                {'u', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'u'},
                {'u', 'u', ' ', ' ', ' ', ' ', ' ', 'u', 'u'},
                {'u', 'u', 'u', ' ', ' ', ' ', 'u', 'u', 'u'},
                {'u', 'u', 'u', 'u', ' ', ' ', 'u', 'u', 'u'}
        };

        for (int i = 0; i<9;i++){
            for(int j = 0; j<9; j++){
                if(tmp[i][j].isCellActive() && !tmp[i][j].isCellEmpty()){
                    result[i][j] = getTypeCharacter(tmp[i][j].getCard().getType());
                }
            }
        }
        return result;
    }

    public static Character[][] bookshelfConverter(Cell[][] bookshelf) {
        Character[][] result = new Character[6][5];
        for(int i = 0; i<bookshelf.length; i++){
            for(int j=0; j<bookshelf[0].length; j++){
                if (!bookshelf[i][j].isCellEmpty()){
                    result[i][j] = getTypeCharacter(bookshelf[i][j].getCard().getType());
                }
                else result[i][j] = ' ';
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(makeTitle("Livingroom"));
        Character[][] matrix;
        matrix = new Character[][]{
                {'u', 'u', 'u', 't', 't', 'u', 'u', 'u', 'u'},
                {'u', 'u', 'u', 't', 't', 't', 'u', 'u', 'u'},
                {'u', 'u', 't', 't', 't', 't', 't', 'u', 'u'},
                {'u', 't', 't', 't', 't', 't', 't', 't', 't'},
                {'t', 't', 't', 't', 't', 't', 't', 't', 't'},
                {'t', 't', 't', 't', 't', 't', 't', 't', 'u'},
                {'u', 'u', 't', 't', 't', 't', 't', 'u', 'u'},
                {'u', 'u', 'u', 't', 't', 't', 'u', 'u', 'u'},
                {'u', 'u', 'u', 'u', 't', 't', 'u', 'u', 'u'}
        };
        System.out.println(makeBoard(matrix));
        System.out.println(makeLegend());

        System.out.println(makeTitle("Bookshelf"));
        Character[][] matrix2;
        matrix2 = new Character[][]{
                {'t', 't', 't', 't', 't'},
                {'t', 't', 't', 't', 't'},
                {'t', 't', 't', 't', 't'},
                {'t', 't', 't', 't', 't'},
                {'t', 't', 't', 't', 't'},
                {'t', 't', 't', 't', 't'}
        };
        System.out.println(makeBookshelf(matrix2));

        System.out.println(CliUtil.makeErrorMessage("Enter valid number."));
    }

    public static String makeTitle(String title) {
        int titleLength = title.length();
        int delimiterLength = /*80*/ 55 - titleLength - 2;
        int delimiterLeftLength = delimiterLength / 2;
        int delimiterRightLength = delimiterLength - delimiterLeftLength-2;

        String delimiterLeft = "-".repeat(delimiterLeftLength);
        String delimiterRight = "-".repeat(delimiterRightLength);

        String res;
        res = (ColorCode.BLUE.getCode() + "-".repeat(55) + "\n" +
                "|"+ delimiterLeft + " " + title + " " + delimiterRight + "|\n" +
                "-".repeat(55) + "\n");

        return res;
    }

    public static String makeErrorMessage(String message){
        return ColorCode.RED.getCode() + AsciiTool.X.getSymbol() + message;
    }

    public static String makeConfirmationMessage(String message){
        return ColorCode.GREEN.getCode() + AsciiTool.V.getSymbol() + " " + message;
    }

}
