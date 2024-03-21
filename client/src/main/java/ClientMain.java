import chess.*;
import ui.ChessClient;
import ui.REPLs.GameplayRepl;
import ui.REPLs.PostLoginRepl;
import ui.REPLs.PreLoginRepl;
import ui.State;

public class ClientMain {
    private static ChessClient client;



    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        client = new ChessClient(serverUrl);
//        while(client.getState() != State.QUIT){
//            while(client.getState() == State.LOGGEDOUT){
//                new PreLoginRepl(client).run();
//            }
//            while(client.getState() == State.LOGGEDIN){
//                new PostLoginRepl(client).run();
//            }
//            while(client.getState() == State.GAMEPLAY){
//                new GameplayRepl(client).run();
//            }
//        }

        new GameplayRepl(client).run();

    }
}