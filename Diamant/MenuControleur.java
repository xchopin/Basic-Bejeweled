package Diamant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Xavier on 19/05/2016.
 */
public class MenuControleur extends Controleur {

    private VuePlateau vuePlateau;
    private VueInfos vueInfos;
    private VueAction vueAction;

    public MenuControleur(Jeu m, VuePlateau v, VueInfos v1, VueAction v2) {

        super(m);
        vuePlateau = v;
        vueInfos = v1;
        vueAction = v2;
    }

    /**
     * Fonction affichant les infos "à propos" dans le JMENU
     */
    private void aPropos(){
        JFrame popup = new JFrame("A propos");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel panelSud = new JPanel();
        panelSud.setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("src/images/univ.png");
        JLabel univ = new JLabel();
        univ.setIcon(logo);


        JLabel titre = new JLabel("<html>   <h1> A propos</h1> <br> </html>");

        JLabel description = new JLabel("<html>" +
                "Projet Bejeweled(R),<br>" +
                "Un projet sympatique meme s'il n'y a pas beaucoup de temps a cause des autres projets :p" +
                "<br><br><br><br><hr> </html>");

        JLabel author = new JLabel("<html> <i> (c) Xavier CHOPIN -  2016</i> </html>");
        popup.setVisible(true);

        panel.add(titre,BorderLayout.NORTH);
        panel.add(description,BorderLayout.CENTER);
        panelSud.add(univ,BorderLayout.EAST);
        panelSud.add(author,BorderLayout.WEST);
        panel.add(panelSud,BorderLayout.SOUTH);
        panel.setVisible(true);
        popup.getContentPane().add(new JScrollPane(panel));
        popup.setResizable(false);

        popup.pack();
        popup.setSize(700, 300);

    }

    /**
     * Fonction determinant si un string est un nombre ou non
     * @param s la valeur a tester
     * @return
     */
    protected static boolean estUnNombre(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Fonction permettant de changer la taille du plateau de jeu
     */
    private void changerTaillePlateau(){
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Largeur :  "));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Hauteur : "));
        myPanel.add(yField);

        int resultat = JOptionPane.showConfirmDialog(null, myPanel,
                "Taille du nouveau plateau (15 max | 3 min)", JOptionPane.OK_CANCEL_OPTION);
        if (resultat == JOptionPane.OK_OPTION) {
            JOptionPane jop = new JOptionPane();

            if (estUnNombre(xField.getText()) && estUnNombre(yField.getText())){
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());

                if (x > 15 ||y > 15){
                    jop.showMessageDialog(null, "Erreur la valeur maximale est 15 ", "ERREUR !", JOptionPane.WARNING_MESSAGE);
                }else if (x < 0 || y < 0){
                    jop.showMessageDialog(null, "Erreur veuillez entrer une valeur positive ", "ERREUR !", JOptionPane.WARNING_MESSAGE);
                }else if (x < 4 || y < 4){
                    jop.showMessageDialog(null, "Erreur veuillez entrer une valeur superieure a 3 ", "ERREUR !", JOptionPane.WARNING_MESSAGE);
                }else{
                    vuePlateau.changerMap(y,x);

                }
            }else{
                jop.showMessageDialog(null, "Pourquoi avez vous rentre du texte ?!! ", "ERREUR !", JOptionPane.WARNING_MESSAGE);
            }


        }

     }


    /**
     * Fonction pour générer une map de même taille que le plateau de base
     */
    private void genererMap(){
        int x = modele.getMap().length;
        int y = modele.getMap()[0].length;
        modele.setMap(LancerJeu.genererMap(x,y));
        modele.setScore(0);
        modele.setNbEchange(0);
        vuePlateau.mettreAJour();
        vueInfos.mettreAJour();
    }

    /**
     * Methode pour reinitialiser le plateau (ne marche pas je ne sais pas pourquoi...)
     */
        private void rejouer(){

        modele.setMap(modele.getSauvegarde());
        modele.setScore(0);
        modele.setNbEchange(0);
        vuePlateau.mettreAJour();
        vueInfos.mettreAJour();
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        String action = ((JMenuItem) e.getSource()).getText();
        if (action.contains("Fermer")){
            System.exit(0);
        } else if (action == "?") {
            aPropos();
        } else if (action.contains("taille du plateau")){
            changerTaillePlateau();
        }else if (action.contains("Generer")){
            genererMap();
        }else if (action.contains("Rejouer")){
            rejouer();
        }else if (action.contains("Mode Debug")){
            vueAction.switchModeDebug();

        }

    }
}
