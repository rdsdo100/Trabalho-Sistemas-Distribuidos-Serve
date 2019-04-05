import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    List<PrintWriter> escritores = new ArrayList<>(); //armazena todos os textos que serão enviados aos clientes

    Scanner scanner;

    public Server() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(5000); //aponta qual a porta o mesmo ira receber os pacotes
            while (true) { //monitora contanteemente essa porta
                Socket socket = serverSocket.accept(); //fica aguardando respostas do cliente
                new Thread(new EscutaCliente(socket)).start();// cria uma tread para cada cliente
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream()); // recupera as menssagens do cliente
                escritores.add(printWriter); // add a lista de clientes
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void encaminharParaTodos(String texto){
        for (PrintWriter printWriter : escritores){ //percorre a lista e para cada printwriter.
            try{
                printWriter.println(texto); // envia as menssagens
                printWriter.flush(); // garante o envio de menssagens
            }catch (Exception e){}
        }
    }

    private class EscutaCliente implements Runnable { //classe para escutar os clientes

        Scanner scanner;
        public EscutaCliente(Socket socket) {
            try {
               scanner = new Scanner(socket.getInputStream());// scanner de leitura que escuata o cliente
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            try {
                String texto;
                while ((texto = scanner.nextLine()) != null) { //monitora o que o cliente esta digitando
                    System.out.println(texto); //teste para escrever no terminal do servidor
                    encaminharParaTodos(texto); //encaminha as menssagens para os clientes

                }
            } catch (Exception e) {
            }
        }
    }

        public static void main(String[] args) {
            new Server();
        }
    }
