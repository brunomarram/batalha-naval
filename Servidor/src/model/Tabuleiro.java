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
            if(y + shipSize <= tabSize && this.jogo[x][y] == 0) {
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

    public void match(int line, char column) {
        int Y = column - 65;
        int X =  line - 1;

        int count = 0;
        for(int i = 0; i < this.shipSize; i++) {
            if(X-i > 0 && this.jogoView[X-i][Y].equals("X")) count++;
            else if(X+i < this.tabSize && this.jogoView[X+i][Y].equals("X")) count++;
            else if(Y-i > 0 && this.jogoView[X][Y-i].equals("X")) count++;
            else if(Y+i < this.tabSize && this.jogoView[X][Y+i].equals("X")) count++;
        }
        if(count == this.shipSize) points++;
        System.out.println(count +","+ this.shipSize);
    }

    public boolean checkPlay(int line, char column) {
        int Y = column - 65;
        int X =  line - 1;
        boolean matched = false;

        if(this.jogo[X][Y] == 1) {
            this.jogo[X][Y] = 2;
            this.jogoView[X][Y] = "X";
            int count = 0;
            for(int i = 0; i < this.shipSize; i++) {
                if(X-i > 0 && this.jogoView[X-i][Y].equals("X")) count++;
                else if(X+i < this.tabSize && this.jogoView[X+i][Y].equals("X")) count++;
                else if(Y-i > 0 && this.jogoView[X][Y-i].equals("X")) count++;
                else if(Y+i < this.tabSize && this.jogoView[X][Y+i].equals("X")) count++;
            }
            if(count == this.shipSize) matched = true;
        } else {
            this.jogo[X][Y] = 3;
            this.jogoView[X][Y] = "o";
        }

        return matched;
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

    public int getPoints() {
        return points;
    }

    public void setTentatives() {
        this.tentatives--;
    }

    public void setPoints() {
        this.points++;
    }
}
