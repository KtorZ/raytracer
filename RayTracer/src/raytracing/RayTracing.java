package raytracing;
/** 
 * Lancement de l'application
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import raytracing.ihm.*;
import raytracing.scene.Color;
import raytracing.scene.Scene;

public class RayTracing{	
    public static void main(String args[]){
	SwingUtilities.invokeLater(new Runnable() {
		public void run() {
		    try{				
			JFrame fenetreAppli = new RayTracingVC("RayTracing");					
			fenetreAppli.pack();
			fenetreAppli.setLocationRelativeTo(null);
			fenetreAppli.setVisible(true);	
		    }catch(IllegalArgumentException e){
			ErrorBox.showError(e.getMessage());
			System.exit(1);
		    }
		}
	    });
    }
}
