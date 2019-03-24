import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    Scanner scanner;

    public Server() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(5000);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new EscutaCliente(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class EscutaCliente implements Runnable {

        Scanner scanner;
        public EscutaCliente(Socket socket) {
            try {
               scanner = new Scanner(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            try {
                String texto;
                while ((texto = scanner.nextLine()) != null) {
                    System.out.println("Rcecebeu: " + texto);
                }
            } catch (Exception e) {
            }
        }
    }

        public static void main(String[] args) {
            new Server();
        }
    }
