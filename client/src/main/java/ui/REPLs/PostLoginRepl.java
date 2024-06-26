package ui.REPLs;

import ui.ChessClient;
import ui.State;

import java.util.Scanner;

public class PostLoginRepl implements Repl {
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
            result = getString(scanner, result, client);
        }
    }

}
