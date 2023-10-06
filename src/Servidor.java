import java.io.*;
import java.net.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class Servidor {
    public static void main(String[] args) {
        // Configurações do servidor
        int porta = 1234;
        Dictionary<String, String> topicos = new Hashtable<>();

        try {
            // Iniciando o servidor na porta especificada
            try (ServerSocket servidorSocket = new ServerSocket(porta)) {
                System.out.println("Servidor iniciado. Aguardando conexões...");

                // Aguardando a conexão de clientes
                while (true) {
                    Socket clienteSocket = servidorSocket.accept();
                    System.out.println("Cliente conectado: " + clienteSocket);

                    // Tratando a conexão em uma nova thread
                    TrataCliente trataCliente = new TrataCliente(clienteSocket, topicos);
                    trataCliente.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
