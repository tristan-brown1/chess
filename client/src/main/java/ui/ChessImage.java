package ui;

import chess.ChessBoard;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;
public class ChessImage {


    private static final int BOARD_SIZE_IN_SQUARES = 6;
    private static final int SQUARE_SIZE_IN_CHARS = 1;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = "   ";
    private static final String X = " X ";
    private static final String O = " O ";
    private static Random rand = new Random();


    public static void run() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

//        drawHeaders(out);

//        drawTicTacToeBoard(out);

        drawChessBoard(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_DARK_GREY);
    }

    private static void drawChessBoard(PrintStream out) {


        drawRotatedTop(out);
        drawTopPieceRotatedRow(out);
        for (int boardCol = 2; boardCol < 8; ++boardCol) {
            drawChessLine(out,boardCol);
        }
        out.print("\n");
        drawBotPieceRotatedRow(out);
        drawRotatedBot(out);

        out.print("\n");

        drawTop(out);
        drawBotPieceRow(out);
        for (int boardCol = 7; boardCol > 1; --boardCol) {
            drawInverseChessLine(out,boardCol);
        }
        out.print("\n");
        drawTopPieceRow(out);
        drawBot(out);

    }

    private static void drawTop(PrintStream out) {
        System.out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
        out.print("    a  b  c  d  e  f  g  h    \n");
    }

    private static void drawRotatedTop(PrintStream out) {
        System.out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
        out.print("    h  g  f  e  d  c  b  a    \n");
    }

    private static void drawTopPieceRow(PrintStream out) {
        out.print(" 1 ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" R ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" N ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" B ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" Q ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" K ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" B ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" N ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" R ");


        out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(SET_BG_COLOR_BLACK);
        out.print(" 1 ");
        System.out.print("\n");
    }

    private static void drawTopPieceRotatedRow(PrintStream out) {
        out.print(" 1 ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" R ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" N ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" B ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" K ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" Q ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" B ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" N ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        System.out.print(" R ");


        out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(SET_BG_COLOR_BLACK);
        out.print(" 1 ");
        System.out.print("\n");
    }

    private static void drawChessLine(PrintStream out, int boardLine) {
        out.print(" ");
        out.print(boardLine);
        out.print(" ");

        for (int i = 0; i < 8; ++i) {
            if(boardLine % 2 == 0){
                if (i % 2 == 0){
                    System.out.print("\u001b[35;100m");
                    if(boardLine == 2){
                        out.print(SET_TEXT_COLOR_BLUE);
                        System.out.print(" P ");
                    }
                    else{
                        System.out.print("   ");
                    }

                }
                else{
                    System.out.print(SET_BG_COLOR_DARK_GREY);
                    if(boardLine == 2){
                        out.print(SET_TEXT_COLOR_BLUE);
                        System.out.print(" P ");
                    }
                    else{
                        System.out.print("   ");
                    }
                }
            }
            else{
                if (i % 2 == 0){
                    System.out.print(SET_BG_COLOR_DARK_GREY);
                    if(boardLine == 7){
                        out.print(SET_TEXT_COLOR_RED);
                        System.out.print(" P ");
                    }
                    else{
                        System.out.print("   ");
                    }
                }
                else{
                    System.out.print("\u001b[35;100m");
                    if(boardLine == 7){
                        out.print(SET_TEXT_COLOR_RED);
                        System.out.print(" P ");
                    }
                    else{
                        System.out.print("   ");
                    }
                }
            }
        }

        out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(SET_BG_COLOR_BLACK);
        out.print(" ");
        out.print(boardLine);

        if(boardLine < 7){
            out.print(" \n");
        }
    }

    private static void drawInverseChessLine(PrintStream out, int boardLine) {
        out.print(" ");
        out.print(boardLine);
        out.print(" ");
        out.print(SET_TEXT_COLOR_RED);

        for (int i = 0; i < 8; ++i) {
            if(boardLine % 2 == 0){
                if (i % 2 == 0){
                    System.out.print("\u001b[35;100m");
                    if(boardLine == 2){
                        out.print(SET_TEXT_COLOR_BLUE);
                        System.out.print(" P ");
                    }
                    else{
                        System.out.print("   ");
                    }
                }
                else{
                    System.out.print(SET_BG_COLOR_DARK_GREY);
                    if(boardLine == 2){
                        out.print(SET_TEXT_COLOR_BLUE);
                        System.out.print(" P ");
                    }
                    else{
                        System.out.print("   ");
                    }
                }
            }
            else{
                if (i % 2 == 0){
                    System.out.print(SET_BG_COLOR_DARK_GREY);
                    if(boardLine == 7){
                        out.print(SET_TEXT_COLOR_RED);
                        System.out.print(" P ");
                    }
                    else{
                        System.out.print("   ");
                    }
                }
                else{
                    System.out.print("\u001b[35;100m");
                    if(boardLine == 7){
                        out.print(SET_TEXT_COLOR_RED);
                        System.out.print(" P ");
                    }
                    else{
                        System.out.print("   ");
                    }
                }
            }
        }

        out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(SET_BG_COLOR_BLACK);
        out.print(" ");
        out.print(boardLine);

        if(boardLine > 2){
            out.print(" \n");
        }
    }

    private static void drawBotPieceRow(PrintStream out) {
        out.print(" 8 ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" R ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" N ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" B ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" Q ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" K ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" B ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" N ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" R ");


        out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(SET_BG_COLOR_BLACK);
        out.print(" 8 ");
        System.out.print("\n");
    }

    private static void drawBotPieceRotatedRow(PrintStream out) {
        out.print(" 8 ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" R ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" N ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" B ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" K ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" Q ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" B ");

        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" N ");

        System.out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_RED);
        System.out.print(" R ");


        out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(SET_BG_COLOR_BLACK);
        out.print(" 8 ");
        System.out.print("\n");
    }

    private static void drawBot(PrintStream out) {
        out.print("    a  b  c  d  e  f  g  h    ");
    }

    private static void drawRotatedBot(PrintStream out) {
        System.out.print(SET_BG_COLOR_BLACK);
        out.print("    h  g  f  e  d  c  b  a    \n");
    }

//    private static void drawHeaders(PrintStream out) {
//
//        setBlack(out);
//
//        String[] headers = { "TIC", "TAC", "TOE" };
//        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
//            drawHeader(out, headers[boardCol]);
//
//            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
//                out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
//            }
//        }
//
//        out.println();
//    }
//
//    private static void drawHeader(PrintStream out, String headerText) {
//        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
//        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;
//
//        out.print(EMPTY.repeat(prefixLength));
//        printHeaderText(out, headerText);
//        out.print(EMPTY.repeat(suffixLength));
//    }
//
//    private static void printHeaderText(PrintStream out, String player) {
//        out.print(SET_BG_COLOR_BLACK);
//        out.print(SET_TEXT_COLOR_GREEN);
//
//        out.print(player);
//
//        setBlack(out);
//    }
//
//    private static void drawTicTacToeBoard(PrintStream out) {
//
//        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
//
//            drawRowOfSquares(out);
//
//            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
//                drawOffRowOfSquares(out);
////                drawVerticalLine(out);
////                setBlack(out);
//            }
//        }
//    }
//
//    private static void drawRowOfSquares(PrintStream out) {
//
//        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
//            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
//                setWhite(out);
//
//                if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
//                    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
//                    int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;
//
//                    out.print(EMPTY.repeat(prefixLength));
//                    printPlayer(out, rand.nextBoolean() ? X : O);
//                    out.print(EMPTY.repeat(suffixLength));
//                }
//                else {
//                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
//                }
//
//                if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
//                    // Draw right line
//                    setRed(out);
//                    out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
//                }
//
//                setBlack(out);
//            }
//
//            out.println();
//        }
//    }
//
//    private static void drawOffRowOfSquares(PrintStream out) {
//
//        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
//            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
//                setRed(out);
//
////                if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
////                    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
////                    int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;
////
////                    out.print(EMPTY.repeat(prefixLength));
////                    printPlayer(out, rand.nextBoolean() ? X : O);
////                    out.print(EMPTY.repeat(suffixLength));
////                }
////                else {
////                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
////                }
//
////                if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
////                    // Draw right line
////                    setWhite(out);
////                    out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
////                }
//
//                setBlack(out);
//            }
//
//            out.println();
//        }
//    }
//
//    private static void drawVerticalLine(PrintStream out) {
//
//        int boardSizeInSpaces = BOARD_SIZE_IN_SQUARES * SQUARE_SIZE_IN_CHARS +
//                (BOARD_SIZE_IN_SQUARES - 1) * LINE_WIDTH_IN_CHARS;
//
//        for (int lineRow = 0; lineRow < LINE_WIDTH_IN_CHARS; ++lineRow) {
//            setRed(out);
//            out.print(EMPTY.repeat(boardSizeInSpaces));
//
//            setBlack(out);
//            out.println();
//        }
//    }
//
    private static void setWhite(PrintStream out) {
//        out.print(SET_BG_COLOR_WHITE);
//        out.print(SET_TEXT_COLOR_WHITE);

        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_DARK_GREY);
    }
//
//    private static void setRed(PrintStream out) {
////        out.print(SET_BG_COLOR_RED);
////        out.print(SET_TEXT_COLOR_RED);
//
//        out.print(SET_BG_COLOR_LIGHT_GREY);
//        out.print(SET_TEXT_COLOR_LIGHT_GREY);
//    }
//
//    private static void setBlack(PrintStream out) {
//        out.print(SET_BG_COLOR_BLACK);
//        out.print(SET_TEXT_COLOR_BLACK);
//    }
//
    private static void printPlayer(PrintStream out, String player) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLACK);

        out.print(player);

        setWhite(out);
    }

    public static void printCurrentBoard(ChessBoard chessBoard){

    }

}
