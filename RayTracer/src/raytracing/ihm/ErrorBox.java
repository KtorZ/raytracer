/** Afficher un message d'erreur de l'application
 * @author Matthias Benkort
 * @version 1.0
 */
package raytracing.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class ErrorBox {
    public static void showError(String errorMess){
        JOptionPane.showMessageDialog(null, "Il semblerait qu'il y ait une erreur :\n"+errorMess, "Oops..." , JOptionPane.ERROR_MESSAGE);
    }
}
