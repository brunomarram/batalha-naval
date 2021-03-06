package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Load {
    private static int port = 50000;

    public static void main(String args[]) throws IOException, ClassNotFoundException, Exception{
        //create the socket server object
        ServerSocket server = new ServerSocket(port);
        Servidor servidor = new Servidor();
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Esperando um cliente");
            Socket cliente = server.accept();

            String response = servidor.listen(cliente);

            if(response != null)
                servidor.write(cliente, response);
        }
    }
}
