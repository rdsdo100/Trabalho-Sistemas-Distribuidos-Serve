import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    List<PrintWriter> escritores = new ArrayList<>();

    Scanner scanner;

    public Server() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(5000);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new EscutaCliente(socket)).start();
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                escritores.add(printWriter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void encaminharParaTodos(String texto){
        for (PrintWriter printWriter : escritores){
            try{
                printWriter.println(texto);
                printWriter.flush();
            }catch (Exception e){}
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
                    encaminharParaTodos(texto);

                }
            } catch (Exception e) {
            }
        }
    }

        public static void main(String[] args) {
            new Server();
        }
    }
