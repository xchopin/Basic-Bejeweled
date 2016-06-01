package Diamant;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Xavier on 14/05/2016.
 */

public class VueInfos extends JPanel implements Vue {

   private JPanel panel,nord,centre;
   private BorderLayout layout;
   private GridLayout g1, g2;
   private String x1,x2,y1,y2;


    public void setX1(String x1) {
        this.x1 = x1;
    }

    public void setX2(String x2) {
        this.x2 = x2;
    }

    public void setY1(String y1) {
        this.y1 = y1;
    }

    public void setY2(String y2) {
        this.y2 = y2;
    }

    JLabel  j1 = new JLabel("j1 : " ), j2 = new JLabel("j1 : " ),
            j3 = new JLabel("j2 : " ), j4 = new JLabel("j2 : " ),
            j5 = new JLabel("nombre d'echanges : "), j6 = new JLabel("score :");


    Jeu jeu;

    public VueInfos(Jeu j){
        // Content
        this.jeu = j;

        // Graphics
        this.panel = new JPanel();
        this.nord = new JPanel();
        this.centre = new JPanel();
        this.layout = new BorderLayout();
        this.panel.setLayout(layout);
        this.g1 = new GridLayout(4,2);
        this.g2 = new GridLayout(1,2);

        // NORD
        this.panel.add(nord,BorderLayout.NORTH);
        this.nord.setLayout(g1);
        this.nord.setBorder( BorderFactory.createTitledBorder(" Cordonnees des cases "));

        this.nord.add(j1); this.nord.add(j2);
        this.nord.add(j3);this.nord.add(j4);

        //CENTRE
        this.panel.add(centre,BorderLayout.CENTER);
        this.centre.setLayout(g2);
        this.centre.setBorder( BorderFactory.createTitledBorder(" Score "));

        this.centre.add(j5);
        this.centre.add(j6);

        x1 = ""; x2 =""; y1="" ; y2="";

    }


    public JPanel getJPanel(){
        return this.panel;
    }


    @Override
    public void mettreAJour() {
        j1.setText("j1 : " + x1);
        j2.setText("j1 : " + y1);
        j3.setText("j2 : " + x2);
        j4.setText("j2 : " + y2);
        j5.setText("nombre d'echanges : " + jeu.getNbEchange());
        j6.setText("score : " + jeu.getScore());
    }
}
