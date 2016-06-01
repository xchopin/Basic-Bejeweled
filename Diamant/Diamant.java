package Diamant;

/**
 * Created by Xavier on 18/05/2016.
 */
public class Diamant {
    private int x,y,valeur;

    public Diamant(int a, int b, int c){
        x = a;
        y = b;
        valeur = c;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
}
