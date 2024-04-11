package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;
public class ChessImage {


    private static void drawTop(PrintStream out) {
        System.out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
        out.print("    a  b  c  d  e  f  g  h    \n");
        System.out.print(RESET);
    }


    private static void drawBot(PrintStream out) {
        System.out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
        out.print("    a  b  c  d  e  f  g  h    \n");
        System.out.print(RESET);
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

    }

    private static void printCheckeredPattern(PrintStream out,int boardLine, ChessBoard chessBoard){

        out.print(" ");
        out.print(boardLine);
        out.print(" ");
        for(int i = 0; i < 8; i++){

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
            if(boardLine % 2 == 0){
                checkNum(i, pieceChar, SET_BG_COLOR_LIGHT_GREY, SET_BG_COLOR_DARK_GREY);
            }
            else{
                checkNum(i, pieceChar, SET_BG_COLOR_DARK_GREY, SET_BG_COLOR_LIGHT_GREY);
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

    private static void checkNum(int i, String pieceChar, String setBgColorLightGrey, String setBgColorDarkGrey) {
        if (i % 2 == 0){
            System.out.print(setBgColorLightGrey);
            System.out.print(" ");
            System.out.print(pieceChar);
            System.out.print(" ");
        }
        else{
            System.out.print(setBgColorDarkGrey);
            System.out.print(" ");
            System.out.print(pieceChar);
            System.out.print(" ");
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
                pieceChar = "B";
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