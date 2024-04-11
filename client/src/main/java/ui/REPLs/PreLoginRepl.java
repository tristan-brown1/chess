package ui.REPLs;

import ui.ChessClient;

import java.util.Scanner;


public class PreLoginRepl implements Repl {

    private final ChessClient client;

    public PreLoginRepl(ChessClient client) {
        this.client = client;
    }

    public void run() {
        System.out.println("Type help to get started!");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        printPrompt();
        String line = scanner.nextLine();
        System.out.print(client.help());
        while (!result.equals("quit") && !result.contains("logged in")){
            result = getString(scanner, result, client);
        }
    }







}
