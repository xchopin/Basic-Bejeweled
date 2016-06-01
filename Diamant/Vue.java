package Diamant;

import javax.swing.*;

/**
 * Created by Xavier on 14/05/2016.
 */
public interface Vue {
    public void mettreAJour();

    /**
     * Fonction accesseur
     * @return le panel de la vue
     */
    public JPanel getJPanel();
}
