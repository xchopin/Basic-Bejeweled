package Diamant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Xavier on 15/05/2016.
 */
public abstract class Controleur implements ActionListener {

    protected Jeu modele;


    public Controleur(Jeu m){
        this.modele = m;
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);
}
