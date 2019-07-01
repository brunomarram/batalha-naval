/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
    private String currentPlayer;
    
    public String call(String method, String params, Socket cliente){
        String response = null;

        try {
            switch(method) {
                case "reset":
                    response = this.reset(params);
                    break;
                case "checkStatus":
                    response = this.checkStatus(params);
                    break;
                case "start":
                    this.start(params, cliente);
                    break;
                case "connect":
                    this.connect(params, cliente);
                    break;
                case "play":
                    this.play(params, cliente);
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

    private void connect(String params, Socket cliente) throws Exception{
        this.jogador2 = new Tabuleiro(
            this.jogador1.getShipSize(),
            this.jogador1.getTabSize(),
            this.jogador1.getNumberOfShips(),
            this.jogador1.getTentatives()
        );
        this.cliente2 = cliente;
        this.currentPlayer = "jogador1";
        this.passPlay();
    }

    private void passPlay() throws Exception {
        if(this.currentPlayer.equals("jogador1")) {
            this.write(this.cliente1, "play");
            this.write(this.cliente2, "wait");
        } else {
            this.write(this.cliente1, "wait");
            this.write(this.cliente2, "play");
        }

        this.write(this.cliente1, Integer.toString(this.jogador1.getPoints()));
        this.write(this.cliente1, Integer.toString(this.jogador1.getTentatives()));
        this.write(this.cliente1, Integer.toString(this.jogador2.getPoints()));
        this.write(this.cliente1, Integer.toString(this.jogador2.getTentatives()));
        this.write(this.cliente1, this.jogador1.getMap());
        this.write(this.cliente1, this.jogador2.justView());

        this.write(this.cliente2, Integer.toString(this.jogador2.getPoints()));
        this.write(this.cliente2, Integer.toString(this.jogador2.getTentatives()));
        this.write(this.cliente2, Integer.toString(this.jogador1.getPoints()));
        this.write(this.cliente2, Integer.toString(this.jogador1.getTentatives()));
        this.write(this.cliente2, this.jogador2.getMap());
        this.write(this.cliente2, this.jogador1.justView());
    }

    private void play(String params, Socket cliente) throws Exception{
        String[] data;
        data = params.split(",");
        int Y = Integer.parseInt(data[1]);

        if(this.currentPlayer.equals("jogador1")) {
            boolean matched = this.jogador2.checkPlay(Y, data[0].charAt(0));
            if(matched) this.jogador1.setPoints();
            this.jogador1.setTentatives();
            this.currentPlayer = "jogador2";
            this.cliente1 = cliente;
        } else {
            boolean matched = this.jogador1.checkPlay(Y, data[0].charAt(0));
            if(matched) this.jogador2.setPoints();
            this.jogador2.setTentatives();
            this.currentPlayer = "jogador1";
            this.cliente2 = cliente;
        }

        this.passPlay();
    }

    private void start(String params, Socket cliente) {
        String[] data;
        data = params.split(",");
        int shipSize = Integer.parseInt(data[0]);
        int tabSize = Integer.parseInt(data[1]);
        int numberOfShips = new Random().nextInt(tabSize);
        int tentatives = new Random().nextInt(numberOfShips*tabSize);
        this.jogador1 = new Tabuleiro(shipSize, tabSize, numberOfShips, tentatives);
        this.cliente1 = cliente;
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
