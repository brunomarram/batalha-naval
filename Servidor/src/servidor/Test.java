
package servidor;

import model.Tabuleiro;

/**
 * This class implements java Socket server
 * @author pankaj
 *
 */
public class Test {

    public static void main(String args[]) {
        Tabuleiro tabuleiro = new Tabuleiro(2, 15, 2, 4);
        tabuleiro.checkPlay(1, 'C');
        System.out.println(tabuleiro.getMap());
        System.out.println(tabuleiro.justView());
    }

}