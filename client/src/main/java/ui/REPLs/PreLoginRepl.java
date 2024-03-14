package ui.REPLs;

import ui.ChessClient;

import java.util.Objects;
import java.util.Scanner;
import static ui.EscapeSequences.*;


public class PreLoginRepl {

    private final ChessClient client;

    public PreLoginRepl(String serverUrl) {
        client = new ChessClient(serverUrl);
    }

    public void run() {
        System.out.println("Welcome to your doom. Type Help if you dare");
//
        Scanner scanner = new Scanner(System.in);
        var result = "";
        printPrompt();
        String line = scanner.nextLine();
        if(Objects.equals(line, "help")){
            System.out.println("okay sure whats up");
        }
        else{
            System.out.println("boom pow you die");
        }
//        while (!result.equals("quit")) {
//            printPrompt();
//            String line = scanner.nextLine();
//
//            try {
//                result = client.eval(line);
//                System.out.print(BLUE + result);
//            } catch (Throwable e) {
//                var msg = e.toString();
//                System.out.print(msg);
//            }
//        }
//        System.out.println();
    }


    private void printPrompt() {
        System.out.print("\n" + RESET + ">>> " + GREEN);
    }


}
