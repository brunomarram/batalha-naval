/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;

/**
 *
 * @author brunomarrademelo
 */
public class Tabuleiro {
    private int shipSize;
    private int tabSize;
    private int [][] matriz;
    private int numberOfShips;
    private int tentatives;
    
    public void generateGame() {
        this.matriz = new int[tabSize][tabSize];
    }
    
    public Tabuleiro(int shipSize, int tabSize, int numberOfShips, int tentatives) {
        this.shipSize = shipSize;
        this.tabSize = tabSize;
        this.numberOfShips = numberOfShips;
        this.tentatives = tentatives;
        this.generateGame();
    }

    @Override
    public String toString() {
        return "Tabuleiro{" + "shipSize=" + shipSize + ", tabSize=" + tabSize + '}';
    }
    
}
