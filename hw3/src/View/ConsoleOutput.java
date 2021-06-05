package View;

public class ConsoleOutput implements Printer {
    @Override
    public void write(String output) {
        System.out.println(output);
    }
}
