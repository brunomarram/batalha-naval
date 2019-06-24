/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Random;
import model.Tabuleiro;

/**
 *
 * @author brunomarrademelo
 */
public class Servidor {

    Tabuleiro jogador1;
    Tabuleiro jogador2;
    static final int PORT = 50000;
    
    public static void main(String[] args) {
        try {
            // Instancia o ServerSocket ouvindo a porta 12345
            ServerSocket socket = new ServerSocket(PORT);
            System.out.println("Servidor ouvindo a porta "+PORT);
            Servidor server = new Servidor();
            while(true) {
                // o método accept() bloqueia a execução até que
                // o servidor receba um pedido de conexão
                Socket cliente = socket.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
                String message = server.listen(cliente);
                server.call(message.split(":")[0], message.split(":")[1]);
                cliente.close();
            }  
        }   
        catch(Exception e) {
           System.out.println("Erro: " + e.getMessage());
        }  
    }
    
    public void call(String method, String params){
        switch(method) {
            case "start":
                String[] data = params.split(",");
                int shipSize = Integer.parseInt(data[1]);
                int tabSize = Integer.parseInt(data[0]);
                int numberOfShips = new Random().nextInt(tabSize);
                int tentatives = new Random().nextInt(numberOfShips*tabSize);
                this.jogador1 = new Tabuleiro(shipSize, tabSize, numberOfShips, tentatives);
                this.jogador1 = new Tabuleiro(shipSize, tabSize, numberOfShips, tentatives);
                System.out.println(this.jogador1.toString());
                break;
        }
    }
    
    public String listen(Socket cliente) throws Exception {
        ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
        String message = (String) entrada.readObject();
        entrada.close();
        return message;
    }
    
    public void write(Socket cliente, String message) throws Exception {
        ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
        saida.flush();
        saida.writeObject(message);
        saida.close();
    }
}
