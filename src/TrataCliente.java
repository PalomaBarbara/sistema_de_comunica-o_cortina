import java.io.*;
import java.net.*;
import java.util.Dictionary;

public class TrataCliente extends Thread {
    private Socket clienteSocket;
    private Dictionary<String, String> topicos;

    public TrataCliente(Socket clienteSocket, Dictionary<String, String> topicos) {
        this.clienteSocket = clienteSocket;
        this.topicos = topicos;
    }

    public void run() {
        try {
            // Configurando os canais de comunicação com o cliente
            BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // Processando as mensagens recebidas do cliente
                String[] partes = inputLine.split(",");
                String operacao = partes[0].trim();
                String topico = partes[1].trim();

                if (operacao.equals("PUBLICAR")) {
                    // Se a operação for PUBLICAR, armazena o valor do tópico no dicionário
                    String valor = partes[2].trim();
                    topicos.put(topico, valor);
                } else if (operacao.equals("ASSINAR")) {
                    // Se a operação for ASSINAR, obtém o valor do tópico do dicionário e envia ao cliente
                    String valor = topicos.get(topico);
                    out.println(valor);
                }
            }

            // Encerrando a conexão com o cliente
            clienteSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
