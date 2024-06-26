package ui.REPLs;

import ui.ChessClient;
import ui.State;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class GameplayRepl implements Repl {

    private final ChessClient client;

    public GameplayRepl(ChessClient client) {
        this.client = client;
//        loadBoard();
    }

    public void run() {
//        System.out.println("Welcome to your doom. Type Help if you dare");

        Scanner scanner = new Scanner(System.in);
        var result = "";
//        String line = scanner.nextLine();

//        printPrompt();
        while (client.getState() == State.GAMEPLAY) {
            printPrompt();
            String line = scanner.nextLine();
            try {
                result = client.eval(line);
                System.out.print(BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
    }

}
