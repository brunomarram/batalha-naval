package servidor;

import java.net.ServerSocket;

public class Load {
    static final int PORT = 50000;

    public static void main(String[] args) {
        try {
            // Instancia o ServerSocket ouvindo a porta 50000
            ServerSocket socket = new ServerSocket(PORT);
            System.out.println("Servidor ouvindo a porta "+PORT);
            Servidor server = new Servidor();
            while(true) {
                // o método accept() bloqueia a execução até que
                // o servidor receba um pedido de conexão
                server.cliente = socket.accept();
                System.out.println("Cliente conectado: " + server.cliente.getInetAddress().getHostAddress());
                String message = server.listen();
                server.call(message.split(":")[0], message.split(":")[1]);
                server.cliente.close();
            }
        }
        catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
