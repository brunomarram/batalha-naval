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
    public ObjectInputStream entrada;
    public ObjectOutputStream saida;
    
    public String call(String method, String params){
        String response = "";

        try {
            switch(method) {
                case "start":
                    response = this.start(params);
                    break;
                case "connect":
                    response = this.connect(params);
                    break;
                case "play":
                    response = this.play(params);
                    break;
            }
        } catch(Exception ex) {
            System.out.println("Ocorreu um erro: "+ ex.getMessage());
        }

        return response;
    }

    private String connect(String params) {
        if(this.jogador1 == null) {
            return "Nenhum jogo iniciado";
        } else {
            this.jogador2 = new Tabuleiro(
                this.jogador1.getShipSize(),
                this.jogador1.getTabSize(),
                this.jogador1.getNumberOfShips(),
                this.jogador1.getTentatives()
            );
            this.jogador2.setIp(cliente.getRemoteSocketAddress().toString());
            return this.jogador1.getMap();
        }
    }

    private String play(String params) {
        String[] data;
        data = params.split(",");
        int Y = Integer.parseInt(data[1]);
        this.jogador1.checkPlay(data[0].charAt(0), Y);
        return "";
    }

    private String start(String params) {
        String[] data;
        data = params.split(",");
        int shipSize = Integer.parseInt(data[0]);
        int tabSize = Integer.parseInt(data[1]);
        int numberOfShips = new Random().nextInt(tabSize);
        int tentatives = new Random().nextInt(numberOfShips*tabSize);
        this.jogador1 = new Tabuleiro(shipSize, tabSize, numberOfShips, tentatives);
        this.jogador1.setIp(cliente.getRemoteSocketAddress().toString());
        return this.jogador1.getMap();
    }
    
    public String listen() throws Exception {
        entrada = new ObjectInputStream(cliente.getInputStream());
        String message = (String) entrada.readObject();
        return this.call(message.split(":")[0], message.split(":")[1]);
    }
    
    public void write(String message) throws Exception {
        saida = new ObjectOutputStream(cliente.getOutputStream());
        saida.writeObject(message);
    }
}
