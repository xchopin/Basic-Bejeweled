package Diamant;

import Diamant.ActionControleur;
import Diamant.Diamant;
import Diamant.PlateauControleur;
import Diamant.Vue;
import Diamant.VueAction;
import Diamant.VueInfos;
import Diamant.VuePlateau;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Xavier on 14/05/2016.
 */
public class Jeu {

    public final static int SAPHIR = 0, RUBIS = 1, EMERAUDE = 2, DIAMANT = 3, VIDE = -1;

    private int nbEchange, score;
    private ArrayList<Vue> listVue ;
    private VueInfos infos;
    private VueAction action;
    private VuePlateau plateau;
    private PlateauControleur pControleur;
    private ActionControleur aControleur;
    private ArrayList<Diamant[][]> map;
    private ArrayList<Diamant[][]> sauvegarde;

    public Jeu( Diamant[][] monde){
        map = new ArrayList<Diamant[][]>(1);
        sauvegarde = new ArrayList<Diamant[][]>();

        setMap(monde);
        creerCopie();

        infos = new VueInfos(this);
        action = new VueAction(this);
        plateau = new VuePlateau(this);

        pControleur = new PlateauControleur(this,infos,action,plateau);
        aControleur = new ActionControleur(this,pControleur,action);

        action.setControleur(aControleur);
        plateau.setControleur(pControleur);

        listVue = new ArrayList<Vue>();
        listVue.add(infos);
        listVue.add(action);
        listVue.add(plateau);


        nbEchange = 0;
        score = 0;

    }

    /**
     * Fonction retournan l'image d'un diamant
     * @param x la valeur du diamant
     * @return l'image en ImageIcon
     */
    public ImageIcon getImage(int x){

        switch (x){
            case SAPHIR:
                return new ImageIcon( getClass() .getResource("/Diamant/images/saphir.png"));
            case RUBIS:
                return new ImageIcon( getClass() .getResource("/Diamant/images/rubis.png"));
            case EMERAUDE:
                return new ImageIcon( getClass() .getResource("/Diamant/images/emeraude.png"));
            case DIAMANT:
                return new ImageIcon( getClass() .getResource("/Diamant/images/diamant.png"));
            default:
                return null;

        }

    }

    /**
     * Méthode incrémentant le nombre d'échange effectué par le joueur
     */
    public void incrementerEchanger(){
        this.nbEchange++;
    }

    /**
     * Méthode pour augmenter le score quand l'on détruit des diamants
     * @param diamsDetruits nombre de diamants détruits
     */
    public void augmenterScore(int diamsDetruits){
        if (diamsDetruits == 3){
            score += 10;
        }else if (diamsDetruits > 3){
            score += 10 + (diamsDetruits*5);
        }
    }

    /**
     * Fonction pour faire descendre les pierres : colonne par colonne
     */
    public void descendrePierres() {
        while (contientCaseVide()){

            for (int X = 0; X < getMap().length; X++) {

                for (int Y = getMap()[0].length-1; Y >= 1; Y--) {   //1 Car il n'y a rien au dessus !

                   if (getMap()[X][Y].getValeur() == VIDE){
                           getMap()[X][Y].setValeur(getMap()[X][Y-1].getValeur());
                           getMap()[X][Y-1].setValeur(VIDE);
                   }
                }
            }
        }
    }

    public void creerCopie(){
        if (sauvegarde.size() == 1){
            sauvegarde.remove(0);
        }

        sauvegarde = new ArrayList<Diamant[][]>(map);
    }

    /**
     * Méthode indiquant si la map contient au moins une map vide
     * @return  boolean: s'il y a une case vide
     */
    public boolean contientCaseVide(){

        for (int X = 0; X < getMap().length; X++) {
            for (int Y = 1 ; Y < getMap()[0].length; Y++) {
                if (getMap()[X][Y].getValeur() == VIDE){
                    if (getMap()[X][Y-1].getValeur() != VIDE){
                            return true;
                    }
                }
            }
        }
         return false;
    }


    /**
     * Méthode permettant de générer de nouvelles pierres dans les colonnes vides de la map
     */
    public void nouvellesPierres(){
        int random, Y;
        for (int X = 0; X < getMap().length; X++) {
            Y = 0;
            if (getMap()[X][Y].getValeur() == VIDE) {
                while(getMap()[X][Y].getValeur() == VIDE && Y < getMap()[0].length){
                    random = (int)(Math.random() * 4);
                    getMap()[X][Y].setValeur(random);
                    Y++;
                }
            }
        }
    }

    /**
     * Méthode testant toute la map et indiquant si au moins une colonne est incomplète dans le plateau
     * @return boolean si une colonne est incomplete
     */
    public boolean contientColonneIncomplete(){
        for (int X = 0; X < getMap().length; X++) {
               if (getMap()[X][0].getValeur() == VIDE){
                   return true;
               }

        }
        return false;
    }


    /**
     * Méthode qui charge la sauvegarde (rejouer)
     */
    public void rejouer(){
        map.clear();
        map = new ArrayList<Diamant[][]>(sauvegarde);
    }




    /**
     * Méthode retournant un diamant a une position donnée
     * @param x abscisse
     * @param y ordonnée
     * @return diamant à x,y
     */
    public Diamant obtenirDiamant(int x, int y){
        if (x < getMap().length && y < getMap()[0].length)
            return getMap()[x][y];
        return null;
    }

    /** GETTER - SETTER */

    public Diamant[][] getMap(){
        if (map.size() == 1)
            return map.get(0);
        else
            return null;

    }

    public int getNbEchange(){
        return nbEchange;
    }

    public int getScore(){
        return score;
    }


    public void setMap(Diamant[][] monde){
        if (map.size() == 1)
            map.remove(0);

        map.add(monde);
    }


    public void setNbEchange(int x){
        nbEchange = x;
    }

    public void setScore(int x){
        score = x;
    }

    public VueInfos getInfos(){
        return infos;
    }

    public VueAction getAction(){
        return action;
    }

    public VuePlateau getPlateau(){
        return  plateau;
    }

    public Diamant[][] getSauvegarde() {
        return  sauvegarde.get(0);
    }

    public ArrayList<Vue> getListVue() { return listVue;}

}
