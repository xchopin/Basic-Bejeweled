package Diamant;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Xavier on 14/05/2016.
 */
public class LancerJeu extends JFrame {

    static Jeu bejeweled;
    JFrame fenetre;
    public final static int HAUTEUR = 600;
    public final static int LARGEUR = 900;


    public LancerJeu(Diamant map[][]) {


        bejeweled = new Jeu(map);
        this.fenetre = new JFrame("Jeu du mineur - Xavier CHOPIN");
        this.fenetre.setPreferredSize(new Dimension(LARGEUR, HAUTEUR));
        this.fenetre.setBackground(Color.lightGray);

        this.fenetre.setLayout(new BorderLayout());
        this.placerVue();
        this.ajouterMenu();

        this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.fenetre.setVisible(true);
        this.fenetre.pack();
    }

    /**
     * Méthode qui place les vues dans la jframe
     */
    private void placerVue(){
        for (Vue v : bejeweled.getListVue()){
            if (v instanceof VuePlateau){
                fenetre.add(v.getJPanel(),BorderLayout.WEST);
            }else if ( v instanceof VueInfos){
              fenetre.add(v.getJPanel(), BorderLayout.CENTER);
            }else{
                fenetre.add(v.getJPanel(), BorderLayout.SOUTH);
            }

        }
    }




    /**
     * Fonction qui ajoute un JMenuBar à la fenêtre
     */
    private void ajouterMenu(){
        MenuControleur controleur = new MenuControleur(bejeweled,bejeweled.getPlateau(), bejeweled.getInfos(),bejeweled.getAction());
        JMenuBar menuBar = new JMenuBar();

        JMenu menu1 = new JMenu("Fichier");
        JMenu menu2 = new JMenu("Jeu");
        JMenu menu3 = new JMenu("A propos");

        JMenuItem item0 = new JMenuItem("Ouvrir");
        JMenuItem item1 = new JMenuItem("Enregistrer");
        JMenuItem item2 = new JMenuItem("Fermer");
        JMenuItem item3 = new JMenuItem("Rejouer");
        JMenuItem item4 = new JMenuItem("Generer un nouveau plateau");
        JMenuItem item5 = new JMenuItem("Changer la taille du plateau");
        JMenuItem item6 = new JMenuItem("?");
        JMenuItem item7 = new JMenuItem("Mode Debug");

        menu1.add(item0).addActionListener(controleur);
        menu1.add(item1).addActionListener(controleur);
        menu1.add(item2).addActionListener(controleur);
        menu2.add(item3).addActionListener(controleur);
        menu2.add(item4).addActionListener(controleur);
        menu2.add(item5).addActionListener(controleur);
        menu2.add(item7).addActionListener(controleur);
        menu3.add(item6).addActionListener(controleur);

        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        fenetre.setJMenuBar(menuBar);

    }

    /**
     * Méthode pour convertir un tableau 2d d'int en tableau 2D de diamant
     * @param tab
     * @return
     */
    public static Diamant[][] convertToDiamant(int [][] tab){
        Diamant[][] plateau = new Diamant[tab.length][tab[0].length];

        for (int i= 0; i < tab.length ; i++){
            for (int j = 0; j < tab[0].length ; j++){
                plateau[i][j] = new Diamant(i,j,tab[i][j]);
            }
        }

        return plateau;
    }


    /**
     * Fonction qui permet de générer une map aléatoirement
     * @param x largeur
     * @param y hauteur
     * @return
     */
    public static Diamant[][] genererMap(int x, int y){
        int random;
        Diamant[][] tab = new Diamant[x][y];
        for (int i = 0; i < y; i++){
            for (int j = 0; j < x; j++){
                random = (int)(Math.random() * 4);
                tab[j][i] = new Diamant(j,i,random);
            }
        }

        return tab;
    }


    public static void main(String[] args){
        new LancerJeu(genererMap(6,6));
    }

}

