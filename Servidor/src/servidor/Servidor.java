/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Random;
import model.Tabuleiro;

/**
 *
 * @author brunomarrademelo
 */
public class Servidor {

    public Socket cliente1;
    public Socket cliente2;
    private Tabuleiro jogador1;
    private Tabuleiro jogador2;
    public ObjectInputStream entrada;
    public ObjectOutputStream saida;
    
    public String call(String method, String params, Socket cliente){
        String response = "";

        try {
            switch(method) {
                case "reset":
                    response = this.reset(params);
                    break;
                case "checkStatus":
                    response = this.checkStatus(params);
                    break;
                case "start":
                    response = this.start(params, cliente);
                    break;
                case "connect":
                    response = this.connect(params, cliente);
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

    private String reset(String params){
        this.jogador1 = null;
        this.jogador2 = null;
        this.cliente1 = null;
        this.cliente2 = null;
        return "";
    }

    private String checkStatus(String params){
        if(this.jogador1 != null){
            return "Jogo Iniciado";
        } else {
            return "Iniciar Jogo";
        }
    }

    private String connect(String params, Socket cliente) throws Exception{
        if(this.jogador1 == null) {
            return "Nenhum jogo iniciado";
        } else {
            this.jogador2 = new Tabuleiro(
                this.jogador1.getShipSize(),
                this.jogador1.getTabSize(),
                this.jogador1.getNumberOfShips(),
                this.jogador1.getTentatives()
            );
            this.cliente2 = cliente;
            return this.jogador2.getMap();
        }
    }

    private String play(String params) {
        String[] data;
        data = params.split(",");
        int Y = Integer.parseInt(data[1]);
        this.jogador1.checkPlay(data[0].charAt(0), Y);
        return "";
    }

    private String start(String params, Socket cliente) {
        String[] data;
        data = params.split(",");
        int shipSize = Integer.parseInt(data[0]);
        int tabSize = Integer.parseInt(data[1]);
        int numberOfShips = new Random().nextInt(tabSize);
        int tentatives = new Random().nextInt(numberOfShips*tabSize);
        this.jogador1 = new Tabuleiro(shipSize, tabSize, numberOfShips, tentatives);
        this.cliente1 = cliente;
        return this.jogador1.getMap();
    }
    
    public String listen(Socket cliente) throws Exception {
        entrada = new ObjectInputStream(cliente.getInputStream());
        String message = (String) entrada.readObject();
        return this.call(message.split(":")[0], message.split(":")[1], cliente);
    }
    
    public void write(Socket cliente, String message) throws Exception {
        saida = new ObjectOutputStream(cliente.getOutputStream());
        saida.writeObject(message);
    }
}
