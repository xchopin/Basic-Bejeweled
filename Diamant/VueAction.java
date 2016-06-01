
package Diamant;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Xavier on 16/05/2016.
 */

public class VueAction extends JPanel implements Vue {

    private JPanel panel;
    private GridLayout g1;
    private JButton b1,b2,b3,b4,b5;
    private Jeu jeu;
    private ActionControleur controleur;
    private static boolean modeDebug;


    public VueAction(Jeu j){
        // Content
        this.jeu = j;
        modeDebug = true;
        // Graphics
        this.panel = new JPanel();
        this.panel.setPreferredSize(new Dimension(LancerJeu.LARGEUR, LancerJeu.HAUTEUR/8));
        this.g1 = new GridLayout(1,5);
        this.panel.setLayout(g1);

        b1 = new JButton("mode rapide");
        b2 = new JButton("echange");
        b3 = new JButton("<html><center> supression <br> des <br> alignements</center></html>");
        b4 = new JButton("descente des pierres");
        b5 = new JButton("nouvelles pierres");

        b1.setBackground(Color.RED);
        b1.setText("Mode Rapide [Desactive]");

        b2.setEnabled(false);
        b4.setEnabled(false);
        b5.setEnabled(false);

        this.panel.add(b1);
        this.panel.add(b2);
        this.panel.add(b3);
        this.panel.add(b4);
        this.panel.add(b5);



    }

    /**
     * Fonction qui ajoute les listener aux buttons et qui set de le controleur
     * @param c l'ActionControleur
     */
    public void setControleur(ActionControleur c){
        controleur = c;
        b1.addActionListener(controleur);
        b2.addActionListener(controleur);
        b3.addActionListener(controleur);
        b4.addActionListener(controleur);
        b5.addActionListener(controleur);
    }


    /**
     * Fonction qui indique l'état du mode debug
     * @return modeDebug
     */
    public boolean isModeDebug(){
        return modeDebug;
    }

    /**
     * Méthode qui active / désactive le modeDebug
     */
    public void switchModeDebug(){
        if (modeDebug){
            modeDebug = false;
        }else{
            modeDebug = true;
        }

        if (modeDebug){
            PlateauControleur.setModeRapide(false);
            b2.setEnabled(false);
            b4.setEnabled(false);
            b5.setEnabled(false);

            this.panel.add(b1);
            this.panel.add(b2);
            this.panel.add(b3);
            this.panel.add(b4);
            this.panel.add(b5);
            this.panel.repaint();
            this.panel.revalidate();


        }else{
            PlateauControleur.setModeRapide(true);
            this.panel.removeAll();
            this.panel.repaint();
            this.panel.revalidate();

        }

    }

    public JPanel getJPanel(){
        return this.panel;
    }

    /**
     * Méthode pour indiquer si le mode rapide est activé
     * @param bool etat de modeRapide
     */
    public void modeRapide(boolean bool){
        if (bool){
            b1.setBackground(Color.GREEN);
            b1.setText("Mode Rapide [Active]");
        } else {
            b1.setBackground(Color.RED);
            b1.setText("Mode Rapide [Desactive]");
        }

    }

    @Override
    public void mettreAJour() {

        if (modeDebug == true){
            b2.setEnabled(PlateauControleur.peutOnEchanger());
            b4.setEnabled(jeu.contientCaseVide());
            b5.setEnabled(jeu.contientColonneIncomplete());
        }



    }
}

