package Diamant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Xavier on 15/05/2016.
 */
public class PlateauControleur extends Controleur implements MouseListener {

    private VueInfos infos;
    private VueAction action;
    private VuePlateau plateau;

    private static boolean modeRapide;

     int X,Y,X1,Y1,largeurBouton,hauteurBouton;
     int diamX,diamY;


    private static ArrayList<JButton> buttonSelected; // tableau des boutons (diamants) séléctionnés

    public PlateauControleur(Jeu j, VueInfos v, VueAction a, VuePlateau p) {
        super(j);
        this.infos = v;
        this.action = a;
        this.plateau = p;
        buttonSelected = new ArrayList<JButton>();
        modeRapide = false;
    }

    /**
     * Méthode pour activer / desactiver le mode rapide
     * @return le nouvel état de mode rapide
     */
    public boolean changerModeRapide(){
        if (modeRapide)
            modeRapide = false;
        else
            modeRapide = true;

        return modeRapide;
    }

    /**
     * Simple getter de mode rapide
     */
    public static boolean getModeRapide(){
        return modeRapide;
    }

    public static void setModeRapide(boolean b) {modeRapide = b;}


    /**
     * Mode rapide: fonction qui automatise tous les processus et les clics ! sauf les échanges
     */
    public void modeRapide(){
        if (modeRapide){
            supprimerLignesDiamants();
            if (modele.contientCaseVide()){
                descendrePierre();
            }
            if (modele.contientColonneIncomplete()){
                nouvellesPierres();
            }

        }
    }

    /**
     * Méthode supprimant les diamants de la map
     * @param map le plateau
     * @param liste la liste des diamants à supprimer
     */
    public void supprimerDiamants(Diamant[][] map, ArrayList<Diamant> liste){
        for (Diamant d : liste)
            map[d.getX()][d.getY()].setValeur(Jeu.VIDE);

        modele.augmenterScore(liste.size());
        modele.setMap(map);
        plateau.mettreAJour();

        liste.clear();
    }


    /**
     * Méthode pour mettre des nouvelles pierres dans le plateau
     */
    public void nouvellesPierres(){
        modele.nouvellesPierres();
        modeRapide();
        plateau.mettreAJour();
    }
    /**
     * Méthode pour descendre les pierres dans le plateau
     */
    public void descendrePierre(){
        modele.descendrePierres();
        plateau.mettreAJour();
    }

    /**
     * Méthode vérifiant si au moins 3 diamants du même type sont alignés pour pouvoir les supprimer
     */
    public void supprimerLignesDiamants() {

        ArrayList<Diamant> liste = new ArrayList<Diamant>();
        Diamant[][] map = modele.getMap();
        Diamant ancien;
        Diamant actuel;

        for (int Y = 0; Y < map[0].length; Y++) {
            liste.clear();
            for (int X = 0; X < map.length; X++) {
                actuel = modele.obtenirDiamant(X, Y);
                switch (liste.size()) {
                    case 0 :
                        if (actuel.getValeur() != Jeu.VIDE)
                            liste.add(actuel);
                        break;
                    case 1:
                        ancien = liste.get(0);
                        if (ancien.getValeur() != actuel.getValeur())
                            liste.clear();

                        if (actuel.getValeur() != Jeu.VIDE)
                            liste.add(actuel);
                        break;
                    case 2 :
                        ancien = liste.get(1);
                        if (ancien.getValeur() == actuel.getValeur()) {
                            liste.add(actuel);
                            if (X == map.length -1)
                                supprimerDiamants(map, liste);


                        } else {
                            liste.clear();
                            if (actuel.getValeur() != Jeu.VIDE)
                                liste.add(actuel);
                        }
                        break;
                    default:
                        ancien = liste.get(liste.size() - 1);

                        if (ancien.getValeur() != actuel.getValeur() ) {
                            supprimerDiamants(map,liste);
                            if (actuel.getValeur() != Jeu.VIDE)
                                liste.add(actuel);
                        } else {
                            liste.add(actuel);
                            if (X == map.length -1 )
                                supprimerDiamants(map, liste);
                        }
                        break;
                }

            }
        }


        for (int X = 0; X < map.length; X++) {
            liste.clear();
            for (int Y = 0; Y < map[0].length; Y++) {
                actuel = modele.obtenirDiamant(X, Y);
                switch (liste.size()) {
                    case 0 :
                        if (actuel.getValeur() != Jeu.VIDE)
                            liste.add(actuel);
                        break;
                    case 1:
                        ancien = liste.get(0);
                        if (ancien.getValeur() != actuel.getValeur())
                            liste.clear();

                        if (actuel.getValeur() != Jeu.VIDE)
                            liste.add(actuel);
                        break;
                    case 2 :
                        ancien = liste.get(1);
                        if (ancien.getValeur() == actuel.getValeur() && actuel.getValeur() != Jeu.VIDE ) {
                            liste.add(actuel);
                            if (Y == map[0].length -1 )
                                supprimerDiamants(map,liste);

                        } else {
                            liste.clear();
                            if (actuel.getValeur() != Jeu.VIDE)
                                liste.add(actuel);
                        }

                        break;
                    default:
                        ancien = liste.get(liste.size() - 1);

                        if (ancien.getValeur() != actuel.getValeur()) {
                            supprimerDiamants(map, liste);
                            if (actuel.getValeur() != Jeu.VIDE)
                                liste.add(actuel);
                        } else {
                            if (actuel.getValeur() != Jeu.VIDE)
                                liste.add(actuel);

                            if (Y == map[0].length - 1)
                                supprimerDiamants(map,liste);

                        }

                        break;
                }


            }
        }

        infos.mettreAJour();
        action.mettreAJour();
    }




    /**
     * Méthode permettant de séléctionner un bouton et de faire le traitement GUI
     * @param j le bouton cliqué
     */
    public void selectionnerBouton(JButton j){
        if (action.isModeDebug()){


            switch (buttonSelected.size()){
                case (0):
                    infos.setX1("" + j.getX() / j.getWidth());
                    infos.setY1("" + j.getY() / j.getHeight());
                    buttonSelected.add(j);
                    j.setBorder(BorderFactory.createLineBorder(Color.red,4));
                    break;
                case(1):
                        int x = j.getX() / j.getWidth();
                        int y = j.getY() / j.getHeight();
                        int x2 = buttonSelected.get(0).getX() / buttonSelected.get(0).getWidth();
                        int y2 = buttonSelected.get(0).getY() / buttonSelected.get(0).getHeight();

                        if ( ( (Math.abs(x-x2) == 1 && (Math.abs(y-y2) == 0) )  || ( Math.abs(x-x2) == 0 && (Math.abs(y-y2) == 1) ) ) ){
                            infos.setX2("" + j.getX() / j.getWidth());
                            infos.setY2("" + j.getY() / j.getHeight());
                            buttonSelected.add(j);
                            j.setBorder(BorderFactory.createLineBorder(Color.red, 4));
                        }else{
                            // On efface !
                            infos.setX1("");  infos.setY1("");
                            infos.setX2("");  infos.setY2("");
                            for (JButton bouton : buttonSelected)
                                bouton.setBorder(BorderFactory.createLineBorder(Color.gray));
                            buttonSelected.clear();
                            selectionnerBouton(j);
                        }

                    break;
                case (2) :
                    // On efface !
                    reinitialiserSelection();
                    selectionnerBouton(j);
                    break;
                default:
                    break;

            }

            infos.mettreAJour();
            action.mettreAJour();
        }else{
            reinitialiserSelection();
        }

    }

    /**
     * Méthode pour savoir si l'on a selectionner 2 diamants
     * @return boolean si on peut
     */
    public static boolean peutOnEchanger (){
        return (buttonSelected.size() == 2);
    }

    /**
     * Fonction permettant d'échanger deux diamants voisins par le biais du bouton echanger
     */
    public void echangerCase(){

        if (peutOnEchanger()){
            JButton diamant = buttonSelected.get(0);
            JButton diamant2 = buttonSelected.get(1);
            buttonSelected.clear();

            int x = diamant.getX() / diamant.getWidth(); int y = diamant.getY() / diamant.getHeight();
            int x2 = diamant2.getX() / diamant2.getWidth(); int y2 = diamant2.getY() / diamant2.getHeight();

            int val = obtenirValeur(diamant);
            int val2 = obtenirValeur(diamant2);


            echangerDiamant(x,y,val,x2,y2,val2);
            reinitialiserSelection();



        }

    }

    /**
     * Fonction qui permet d'échanger deux diamants donnés
     * @param x absisces du diamant 1
     * @param y ordonnée du diamant 1
     * @param valeur valeur du diamant 1
     * @param x1 absisces du diamant 2
     * @param y1 ordonnée du diamant 2
     * @param valeur1 valeur du diamant 2
     */
    public void echangerDiamant(int x, int y, int valeur , int x1, int y1, int valeur1){

        Diamant map[][] = modele.getMap();
        map[x][y].setValeur(valeur1);
        map[x1][y1].setValeur(valeur);

        if (modeRapide)
            modeRapide();


        modele.setMap(map);
        modele.incrementerEchanger();
        infos.mettreAJour();
        plateau.mettreAJour();



    }

    /**
     * Permet de suprimmer les séléctions effectées
     */
    public void reinitialiserSelection(){
        infos.setX1("");  infos.setY1("");
        infos.setX2("");  infos.setY2("");

        for (JButton bouton : buttonSelected)
            bouton.setBorder(BorderFactory.createLineBorder(Color.gray));

        buttonSelected.clear();
        infos.mettreAJour();
        action.mettreAJour();
    }


    /**
     * Fonction retournant la valeur d'un diamant
     * @param j JButton : le diamant
     * @return int : la valeur
     */
    private int obtenirValeur(JButton j){
        int x = j.getX() / j.getWidth(); int y = j.getY() / j.getHeight();
        Diamant d = modele.getMap()[x][y];

        return d.getValeur();
    }

    /** Permet de séléctionner un diamant */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton j =((JButton) e.getSource());
        selectionnerBouton(j);


    }



    /** - - -  PARTIE DRAG N DROP  - - - - */


    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        JButton source = (JButton) e.getSource();
        largeurBouton = source.getWidth() /2;
        hauteurBouton = source.getHeight()/2;
        diamX = source.getX() / source.getWidth();
        diamY = source.getY() / source.getHeight();

        X = e.getX();
        Y = e.getY();



        /**
         * J'ai essayé ! mais c'est super compliqué ahah j'arrive pas à récup la destination :( (l'image oui mais pas le diams)
                 TransferHandler handle = source.getTransferHandler();
                 handle.exportAsDrag(source, e, TransferHandler.COPY);
         */





    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        X1 = e.getX();
        Y1 = e.getY();

        Diamant ancien = modele.getMap()[diamX][diamY];
        int nouvelleValeur;
        Diamant[][] monde = modele.getMap();


            if (X1 >= X + largeurBouton && (diamX+1 < monde.length)){
                nouvelleValeur = modele.getMap()[diamX + 1][diamY].getValeur();
                echangerDiamant(diamX + 1, diamY, nouvelleValeur, diamX, diamY, ancien.getValeur());

                // Dedans car sinon, meme pour un simple clique ca va actualiser , pas cool pour le cpu !
                modele.setMap(monde);
                plateau.mettreAJour();
                modele.incrementerEchanger();
                infos.mettreAJour();

            }else if (X1 <= X - largeurBouton &&  diamX-1 >= 0 ){
                nouvelleValeur = modele.getMap()[diamX - 1][diamY].getValeur();
                echangerDiamant(diamX-1, diamY, nouvelleValeur, diamX, diamY, ancien.getValeur());

                modele.setMap(monde);
                plateau.mettreAJour();
                modele.incrementerEchanger();
                infos.mettreAJour();

            } else if (Y1 <= Y - largeurBouton && diamY-1 >=0 ){
                nouvelleValeur = modele.getMap()[diamX][diamY-1].getValeur();
                echangerDiamant(diamX , diamY-1, nouvelleValeur, diamX, diamY, ancien.getValeur());

                modele.setMap(monde);
                plateau.mettreAJour();
                modele.incrementerEchanger();
                infos.mettreAJour();

            }else if (Y1 >= Y + largeurBouton && diamY+1 < monde[0].length) {
                nouvelleValeur = modele.getMap()[diamX][diamY+1].getValeur();
                echangerDiamant(diamX , diamY+1, nouvelleValeur, diamX, diamY, ancien.getValeur());
                modele.setMap(monde);
                plateau.mettreAJour();
                modele.incrementerEchanger();
                infos.mettreAJour();

            }



    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
