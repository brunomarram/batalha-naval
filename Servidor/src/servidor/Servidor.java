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

    public Socket cliente;
    private Tabuleiro jogador1;
    private Tabuleiro jogador2;
    
    public void call(String method, String params){
        String[] data;
        switch(method) {
            case "start":
                data = params.split(",");
                int shipSize = Integer.parseInt(data[0]);
                int tabSize = Integer.parseInt(data[1]);
                int numberOfShips = new Random().nextInt(tabSize);
                int tentatives = new Random().nextInt(numberOfShips*tabSize);
                this.jogador1 = new Tabuleiro(shipSize, tabSize, numberOfShips, tentatives);
                this.jogador2 = new Tabuleiro(shipSize, tabSize, numberOfShips, tentatives);
                System.out.println(this.jogador1.toString());
                break;
            case "play":
                data = params.split(",");
                int Y = Integer.parseInt(data[1]);
                this.jogador1.checkPlay(data[0].charAt(0), Y);
                System.out.println(this.jogador1.toString());
                break;
        }
    }
    
    public String listen() throws Exception {
        ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
        String message = (String) entrada.readObject();
        entrada.close();
        return message;
    }
    
    public void write(String message) throws Exception {
        ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
        saida.flush();
        saida.writeObject(message);
        saida.close();
    }
}
