import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteAtuador {
    public static void main(String[] args) {
        // Configurações do cliente atuador
        String enderecoServidor = "localhost";
        int portaServidor = 1234;
        String topico = "luminosidade";
        boolean cortinaAberta = false;

        try {
            while (true) {
                // Estabelecendo conexão com o servidor
                Socket clienteSocket = new Socket(enderecoServidor, portaServidor);
                BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in);

                // Solicitando valor de luminosidade ao servidor
                String mensagem = "ASSINAR," + topico;
                out.println(mensagem);
                String resposta = in.readLine();
                int valor = Integer.parseInt(resposta);

                // Verificando valor de luminosidade e atualizando o estado da cortina
                if (valor >= 60 && !cortinaAberta) {
                    cortinaAberta = true;
                    System.out.println("Abrindo cortina - Luminosidade: " + valor);
                } else if (valor < 60 && cortinaAberta) {
                    cortinaAberta = false;
                    System.out.println("Fechando cortina - Luminosidade: " + valor);
                } else {
                    System.out.println("Cortina se mantém " + (cortinaAberta ? "aberta" : "fechada") + " - Luminosidade: " + valor);
                }

                // Fechando conexão com o servidor
                clienteSocket.close();
                Thread.sleep(10000); // Aguarda 10 segundos antes de repetir o ciclo
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
