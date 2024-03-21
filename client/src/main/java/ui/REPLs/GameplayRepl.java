package ui.REPLs;

import ui.ChessClient;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class GameplayRepl {

    private final ChessClient client;

    public GameplayRepl(ChessClient client) {
        this.client = client;
    }

    public void run() {
//        System.out.println("Welcome to your doom. Type Help if you dare");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        printPrompt();
        String line = scanner.nextLine();
        if(line.equalsIgnoreCase("help")){
            System.out.print(client.help());
            while (!result.equals("quit")) {
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
        else{
            System.out.println("boom pow you die");
        }
    }


    private void printPrompt() {
        System.out.print("\n" + RESET + ">>> " + GREEN);
    }


}
