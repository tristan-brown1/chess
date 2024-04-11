package ui.REPLs;

import ui.ChessClient;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public interface Repl {


    default String getString(Scanner scanner, String result, ChessClient client) {
        String line;
        printPrompt();
        line = scanner.nextLine();
        try {
            result = client.eval(line);
            System.out.print(BLUE + result);
        } catch (Throwable e) {
            var msg = e.toString();
            System.out.print(msg);
        }
        return result;
    }

    default void printPrompt() {
        System.out.print("\n" + RESET + ">>> " + GREEN);
    }


}
