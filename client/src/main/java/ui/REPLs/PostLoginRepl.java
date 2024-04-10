package ui.REPLs;

import ui.ChessClient;
import ui.State;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class PostLoginRepl {
    private final ChessClient client;

    public PostLoginRepl(ChessClient client) {
        this.client = client;
    }

    public void run() {
//        System.out.println("Welcome to your doom. Type Help if you dare");
        System.out.print("\n");
        System.out.print(client.help());
        Scanner scanner = new Scanner(System.in);
        var result = "";
        String line = "";

        while (!result.equals("quit") && (client.getState()!= State.GAMEPLAY)) {
            printPrompt();
            line = scanner.nextLine();
            try {
                result = client.eval(line);
                System.out.print(BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
    }


    private void printPrompt() {
        System.out.print("\n" + RESET + ">>> " + GREEN);
    }


}
