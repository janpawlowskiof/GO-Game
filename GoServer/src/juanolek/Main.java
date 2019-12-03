package juanolek;

public class Main {
    public static void main(String[] args){
        System.out.println("starting server...\n");

        ConnectionGreeter connectionGreeter = new ConnectionGreeter();
        connectionGreeter.start();
    }
}
