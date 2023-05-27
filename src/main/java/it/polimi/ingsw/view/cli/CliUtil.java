package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.entities.util.BoardTile;
import it.polimi.ingsw.entities.util.CardType;
import it.polimi.ingsw.entities.util.Tile;
import it.polimi.ingsw.mechanics.ToolXML;

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
    ORANGE("\u001B[38;2;255;165;0m"),
    YELLOW("\u001B[33;1m");

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

    //region CONVERSION METHODS
    private static char getTypeCharacter(CardType type) {

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
                return ' ';
            }
        }
    }

    public static char[][] boardConverter(BoardTile[][] tmp) {

        char[][] result = new char[][]{
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
                if(tmp[i][j].isTileActive() && !tmp[i][j].isTileEmpty()){
                    result[i][j] = getTypeCharacter(tmp[i][j].getCard().getType());
                }
            }
        }
        return result;
    }

    public static char[][] bookshelfConverter(Tile[][] bookshelf) {
        char[][] result = new char[6][5];

        for(int i = 0; i<6; i++){
            for(int j=0; j<5; j++){
                if (!bookshelf[i][j].isTileEmpty()){
                    result[i][j] = getTypeCharacter(bookshelf[i][j].getCard().getType());
                }
                else result[i][j] = ' ';
            }
        }
        return result;
    }
    //endregion

    //region UTILITY METHODS
    private static String getRowContent(char[] row) {
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

    private static String getTopNumeration(){
        StringBuilder result = new StringBuilder();
        result.append("     ");
        for(int i=0; i<9; i++){
            result.append(i);
            result.append("     ");

        }
        result.append("\n");
        return result.toString();
    }
    //endregion

    //region MAKE METHODS
    public static String makeTitle(){
        return ColorCode.YELLOW.getCode() + " __       __                   ______   __                  __   ______   __           \n" +
                "|  \\     /  \\                 /      \\ |  \\                |  \\ /      \\ |  \\          \n" +
                "| $$\\   /  $$ __    __       |  $$$$$$\\| $$____    ______  | $$|  $$$$$$\\ \\$$  ______  \n" +
                "| $$$\\ /  $$$|  \\  |  \\      | $$___\\$$| $$    \\  /      \\ | $$| $$_  \\$$|  \\ /      \\ \n" +
                "| $$$$\\  $$$$| $$  | $$       \\$$    \\ | $$$$$$$\\|  $$$$$$\\| $$| $$ \\    | $$|  $$$$$$\\\n" +
                "| $$\\$$ $$ $$| $$  | $$       _\\$$$$$$\\| $$  | $$| $$    $$| $$| $$$$    | $$| $$    $$\n" +
                "| $$ \\$$$| $$| $$__/ $$      |  \\__| $$| $$  | $$| $$$$$$$$| $$| $$      | $$| $$$$$$$$\n" +
                "| $$  \\$ | $$ \\$$    $$       \\$$    $$| $$  | $$ \\$$     \\| $$| $$      | $$ \\$$     \\\n" +
                " \\$$      \\$$ _\\$$$$$$$        \\$$$$$$  \\$$   \\$$  \\$$$$$$$ \\$$ \\$$       \\$$  \\$$$$$$$\n" +
                "             |  \\__| $$                                                                \n" +
                "              \\$$    $$                                                                \n" +
                "               \\$$$$$$                                                                 \n" +
                "\n" + ColorCode.DEFAULT.getCode();
    }
    public static String makeLegend() {
        String line1 = ColorCode.GREEN.getCode() + AsciiTool.DOT.getSymbol() + "C: Cat\t\t" + ColorCode.WHITE.getCode() + AsciiTool.DOT.getSymbol() + "B: Books\n";
        String line2 = ColorCode.ORANGE.getCode() + AsciiTool.DOT.getSymbol() + "G: Game\t\t" + ColorCode.BLUE.getCode() + AsciiTool.DOT.getSymbol() + "F: Frames\n";
        String line3 = ColorCode.L_BLUE.getCode() + AsciiTool.DOT.getSymbol() + "T: Trophies\t" + ColorCode.MAGENTA.getCode() + AsciiTool.DOT.getSymbol() + "P: Plants\n";

        return line3 + line2 + line1 + ColorCode.DEFAULT.getCode();
    }

    public static String makeBoard(char[][] matrix) {

        return ColorCode.GREEN.getCode() +
                getTopNumeration() +
                "  " +
                AsciiTool.SPACES.getSymbol().repeat(3) +
                getHeader(2) +
                "0 " +
                AsciiTool.SPACES.getSymbol().repeat(3) +
                getRowContent(matrix[0]) +
                "  " +
                AsciiTool.SPACES.getSymbol().repeat(3) +
                AsciiTool.LT.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(2) +
                AsciiTool.RT_CORNER.getSymbol() +
                "1 " +
                AsciiTool.SPACES.getSymbol().repeat(3) +
                getRowContent(matrix[1]) +
                "  " +
                AsciiTool.SPACES.getSymbol().repeat(2) +
                AsciiTool.LT_CORNER.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(4) +
                AsciiTool.RT_CORNER.getSymbol() +
                "2 " +
                AsciiTool.SPACES.getSymbol().repeat(2) +
                getRowContent(matrix[2]) +
                "  " +
                AsciiTool.SPACES.getSymbol() +
                AsciiTool.LT_CORNER.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(6) +
                AsciiTool.T.getSymbol() +
                AsciiTool.RT_CORNER.getSymbol() +
                "3 " +
                AsciiTool.SPACES.getSymbol() +
                getRowContent(matrix[3]) +
                "  " +
                AsciiTool.LT_CORNER.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(8) +
                AsciiTool.RT.getSymbol() +
                "4 " +
                getRowContent(matrix[4]) +
                "  " +
                AsciiTool.LT.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(8) +
                AsciiTool.RB_CORNER.getSymbol() +
                "5 " +
                getRowContent(matrix[5]) +
                "  " +
                AsciiTool.LB_CORNER.getSymbol() +
                AsciiTool.BT.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(6) +
                AsciiTool.RB_CORNER.getSymbol() +
                "6 " +
                AsciiTool.SPACES.getSymbol().repeat(2) +
                getRowContent(matrix[6]) +
                "  " +
                AsciiTool.SPACES.getSymbol().repeat(2) +
                AsciiTool.LB_CORNER.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(4) +
                AsciiTool.RB_CORNER.getSymbol() +
                "7 " +
                AsciiTool.SPACES.getSymbol().repeat(3) +
                getRowContent(matrix[7]) +
                "  " +
                AsciiTool.SPACES.getSymbol().repeat(3) +
                AsciiTool.LB_CORNER.getSymbol() +
                AsciiTool.CROSS.getSymbol().repeat(2) +
                AsciiTool.RT.getSymbol() +
                "8 " +
                AsciiTool.SPACES.getSymbol().repeat(4) +
                getRowContent(matrix[8]) +
                "  " +
                AsciiTool.SPACES.getSymbol().repeat(4) +
                getFooter(2);
    }

    public static String makePlayersList(ArrayList<String> players){
        StringBuilder partial = new StringBuilder();
        for(String p: players) partial.append(AsciiTool.DOT.getSymbol()).append(p).append("\n");
        return partial.toString();
    }

    public static String makeBookshelf(char[][] matrix) {
        StringBuilder bookshelf = new StringBuilder(ColorCode.BROWN.getCode() + getHeader(5));
        for(int i=0; i<matrix[0].length; i++){
            bookshelf.append(getRowContent(matrix[i])).append(getIntermediate(5));
        }
        bookshelf.append(getRowContent(matrix[matrix[0].length])).append(getFooter(5));
        return bookshelf.toString();
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

        return res + ColorCode.DEFAULT.getCode();
    }

    public static String makeCommonGoal(String description){
        return ColorCode.ORANGE.getCode()  + AsciiTool.DOT.getSymbol() + description.replace("\n", "\n  ") + ColorCode.DEFAULT.getCode();
    }

    public static String makeErrorMessage(String message) {
        return ColorCode.RED.getCode() + AsciiTool.X.getSymbol() + message + ColorCode.DEFAULT.getCode();
    }

    public static String makeConfirmationMessage(String message) {
        return ColorCode.GREEN.getCode() + AsciiTool.V.getSymbol() + " " + message + ColorCode.DEFAULT.getCode();
    }

    public static String makeCommandList() {
        return ColorCode.ORANGE.getCode() + AsciiTool.DOT.getSymbol() +
                String.join("\n" + AsciiTool.DOT.getSymbol(), ToolXML.getCommandList()) +
                ColorCode.DEFAULT.getCode();
    }
    //endregion

}
