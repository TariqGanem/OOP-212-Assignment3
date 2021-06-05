package View;

import java.util.Scanner;

public class ConsoleInput implements Reader {
    @Override
    public String read() {
        return new Scanner(System.in).next();
    }
}
