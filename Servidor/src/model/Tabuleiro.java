/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author brunomarrademelo
 */
public class Tabuleiro {
    private String ip;
    private int shipSize;
    private int tabSize;
    private int [][] jogo;
    private String [][] jogoView;
    private int numberOfShips;
    private int tentatives;
    private int points;
    
    public void generateGame() {
        this.jogo = new int[this.tabSize][this.tabSize];
        for(int i = 0; i < this.tabSize; i++){
            for(int j = 0; j < this.tabSize; j++){
                this.jogoView[i][j] = "~";
                this.jogo[i][j] = 0;
            }
        }
        int count = 0;

        while(count < this.numberOfShips) {
            int x = new Random().nextInt(tabSize);
            int y = new Random().nextInt(tabSize - shipSize);
            if(this.jogo[x][y] == 0) {
                for(int i = y; i < y + shipSize; i++){
                    this.jogo[x][i] = 1;
                }
                count++;
            }
        }
    }
    
    public Tabuleiro(int shipSize, int tabSize, int numberOfShips, int tentatives) {
        this.shipSize = shipSize;
        this.tabSize = tabSize;
        this.numberOfShips = numberOfShips;
        this.tentatives = tentatives;
        this.jogo = new int[tabSize][tabSize];
        this.jogoView = new String[tabSize][tabSize];
        this.points = 0;
        this.generateGame();
    }

    public void checkPlay(char line, int column) {
        int X = 67 - line;
        int Y =  column - 1;
        this.jogoView[X][Y] = "X";
    }

    public String justView() {
        String game = "";
        for(int i = 0; i < this.tabSize; i++){
            for(int j = 0; j < this.tabSize; j++) {
                game += this.jogoView[i][j] + " ";
            }
            game += "\n";
        }
        return game;
    }

    public String getMap() {
        String game = "";
        for(int i = 0; i < this.tabSize; i++){
            for(int j = 0; j < this.tabSize; j++) {
                game += this.jogo[i][j] + " ";
            }
            game += "\n";
        }
        return game;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getShipSize() {
        return shipSize;
    }

    public int getTabSize() {
        return tabSize;
    }

    public int getNumberOfShips() {
        return numberOfShips;
    }

    public int getTentatives() {
        return tentatives;
    }
}
