import java.io.*;
import java.net.*;
import java.util.Random;

public class ClienteSensor {
    public static void main(String[] args) {
        // Configurações do cliente sensor
        String enderecoServidor = "localhost";
        int portaServidor = 1234;
        String topico = "luminosidade";
        Random random = new Random();

        try {
            while (true) {
                // Estabelecendo conexão com o servidor
                Socket clienteSocket = new Socket(enderecoServidor, portaServidor);
                PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true);

                // Gerando um valor de luminosidade aleatório
                int valor = random.nextInt(101);

                // Enviando o valor de luminosidade ao servidor
                String mensagem = "PUBLICAR," + topico + "," + valor;
                out.println(mensagem);

                // Exibindo o valor de luminosidade publicado
                System.out.println("Valor de luminosidade publicado: " + valor);

                // Fechando conexão com o servidor
                clienteSocket.close();
                Thread.sleep(5000); // Aguarda 5 segundos antes de enviar o próximo valor
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

