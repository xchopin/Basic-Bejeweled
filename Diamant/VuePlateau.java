package Diamant;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Xavier on 14/05/2016.
 */

public class VuePlateau extends JPanel implements Vue {

    JPanel panel;
    GridLayout grid;
    Jeu jeu;
    PlateauControleur controleur;

    int debug = 0;

    public VuePlateau(Jeu j) {
        // Content
        this.jeu = j;
        int x = this.jeu.getMap().length;
        int y = this.jeu.getMap()[0].length;


        // Graphics
        this.panel = new JPanel();
        this.grid = new GridLayout(x, y);
        this.grid.setHgap(0); //espace entre les colonnes (H comme Horizontal)
        this.grid.setVgap(0); //espace entre les lignes (V comme Vertical)
        this.panel.setLayout(this.grid);


    }



    public void setControleur(PlateauControleur c){
        controleur = c;
        this.appliquerBouttons();
    }

    /**
     * Quand on change la map il faut changer également le gridLayout !
     * @param X taille abscisse
     * @param Y taille ordonnée
     */
    public void changerMap(int X, int Y){
        this.grid = new GridLayout(X, Y);
        this.panel.setLayout(this.grid);
        jeu.setMap(LancerJeu.genererMap(X, Y));
        jeu.setScore(0);
        jeu.setNbEchange(0);
       // jeu.setSauvegarde();
        mettreAJour();

    }

    //Fonction pour placer les boutons dans le gridlayout
    private void appliquerBouttons(){
        TransferHandler transfer;
        for (int Y = 0; Y < jeu.getMap()[0].length; Y++){
            for (int X = 0; X < jeu.getMap().length; X++){
                final JButton jbutton = new JButton( jeu.getImage(jeu.getMap()[X][Y].getValeur()));
                jbutton.setBackground(Color.lightGray);
                transfer= new TransferHandler("icon");
                jbutton.setTransferHandler(transfer);
                this.panel.add(jbutton );
                jbutton.addActionListener(controleur);
                jbutton.addMouseListener(controleur);

            }
        }
    }

    public JPanel getJPanel(){
        return this.panel;
    }


    @Override
    public void mettreAJour() {
        panel.removeAll();
        panel.revalidate();
        appliquerBouttons();
        repaint();

    }
}
