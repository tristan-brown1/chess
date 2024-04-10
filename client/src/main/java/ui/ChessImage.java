package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;

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
        System.out.print(RESET);
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
        System.out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
        out.print("    a  b  c  d  e  f  g  h    \n");
        System.out.print(RESET);
    }

    private static void drawRotatedBot(PrintStream out) {
        System.out.print(SET_BG_COLOR_BLACK);
        out.print("    h  g  f  e  d  c  b  a    \n");
    }


    private static void setWhite(PrintStream out) {
//        out.print(SET_BG_COLOR_WHITE);
//        out.print(SET_TEXT_COLOR_WHITE);

        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_DARK_GREY);
    }

    private static void printPlayer(PrintStream out, String player) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLACK);

        out.print(player);

        setWhite(out);
    }

    public static void printCurrentBoard(ChessBoard chessBoard){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);

        System.out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
        drawTop(out);
        System.out.print(RESET);
        for (int boardCol = 8; boardCol >= 1; --boardCol) {
            System.out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_WHITE);
            printCheckeredPattern(out,boardCol,chessBoard);
            System.out.print(RESET);
        }
        drawBot(out);
        System.out.print(RESET);
    }

    private static void printCheckeredPattern(PrintStream out,int boardLine, ChessBoard chessBoard){

        out.print(" ");
        out.print(boardLine);
        out.print(" ");
        for(int i = 0; i < 8; i++){
//            ChessPiece chessPiece = chessBoard.getBoard()[boardLine - 1][i];

            try{
                ChessPiece chessPiece = chessBoard.getBoard()[boardLine - 1][i];
                if(chessPiece.getTeamColor().equals(ChessGame.TeamColor.WHITE)){
                    System.out.print(SET_TEXT_COLOR_RED);
                }
                else {
                    System.out.print(SET_TEXT_COLOR_BLUE);
                }
            }catch (Exception e){
                System.out.print(SET_TEXT_COLOR_BLUE);
            }



            String pieceChar = getPieceChar(boardLine - 1, chessBoard, i);
//            ChessPiece chessPiece = getChessPiece()
            if(boardLine % 2 == 0){
                if (i % 2 == 0){
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(" ");
                    System.out.print(pieceChar);
                    System.out.print(" ");

                }
                else{
                    System.out.print(SET_BG_COLOR_DARK_GREY);
                    System.out.print(" ");
                    System.out.print(pieceChar);
                    System.out.print(" ");
                }
            }
            else{
                if (i % 2 == 0){
                    System.out.print(SET_BG_COLOR_DARK_GREY);
                    System.out.print(" ");
                    System.out.print(pieceChar);
                    System.out.print(" ");
                }
                else{
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(" ");
                    System.out.print(pieceChar);
                    System.out.print(" ");
                }
            }
        }

        out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(SET_BG_COLOR_BLACK);
        out.print(" ");
        out.print(boardLine);

        if(boardLine <= 8){
            out.print(" \n");
        }

    }

    private static String getPieceChar(int boardLine, ChessBoard chessBoard, int i) {
        String pieceChar = " ";
        try{
            ChessPiece chessPiece = chessBoard.getBoard()[boardLine][i];
            ChessPiece.PieceType pieceType = chessPiece.getPieceType();
            if(pieceType.equals(ChessPiece.PieceType.PAWN)){
                pieceChar = "P";
            }
            else if(pieceType.equals(ChessPiece.PieceType.ROOK)){
                pieceChar = "R";
            }
            else if(pieceType.equals(ChessPiece.PieceType.BISHOP)){
                pieceChar = "R";
            }
            else if(pieceType.equals(ChessPiece.PieceType.KNIGHT)){
                pieceChar = "N";
            }
            else if(pieceType.equals(ChessPiece.PieceType.QUEEN)){
                pieceChar = "Q";
            }
            else if(pieceType.equals(ChessPiece.PieceType.KING)){
                pieceChar = "K";
            }
        }catch (Exception e){
            pieceChar = " ";
        }

        return pieceChar;
    }

}
