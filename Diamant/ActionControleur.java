package Diamant;


import javax.swing.*;

import java.awt.event.ActionEvent;

/**
 * Created by Xavier on 15/05/2016.
 */
public class ActionControleur extends Controleur {

    private PlateauControleur controleur;
    private VueAction vueAction;



    public ActionControleur(Jeu j, PlateauControleur p, VueAction a){
        super(j);
        controleur = p;
        vueAction = a;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String action =((JButton) e.getSource()).getText();

        if (action.contains("echange")){
            controleur.echangerCase();
        }else if (action.contains("supression")){
            controleur.supprimerLignesDiamants();
        }else if (action.contains("descente des pierres")){
            controleur.descendrePierre();
        }else if (action.contains("nouvelles pierres")){
            controleur.nouvellesPierres();
        }else if (action.contains("Mode Rapide")){
            controleur.changerModeRapide();
            controleur.modeRapide();
            controleur.getModeRapide();
            vueAction.modeRapide(controleur.getModeRapide());
        }


        vueAction.mettreAJour();
    }
}
