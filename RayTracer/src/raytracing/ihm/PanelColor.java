/** Factorisation du pannel de couleur personnalise
 * @author Matthias Benkort
 * @version 1.0
 */
package raytracing.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import raytracing.scene.Color;


public class PanelColor extends JPanel implements ChangeListener{
    private JColorChooser colorChooser; 
    private raytracing.scene.Color colorChoosed;
    private JLabel banner;

    public PanelColor(String name){
	super(new BorderLayout());
        this.colorChoosed = new raytracing.scene.Color(1,1,1);
        this.initPanel(name);        
    }

    public PanelColor(String name, Color color){
	super(new BorderLayout());
        this.colorChoosed = color;
        this.initPanel(name);
    }

    private void initPanel(String name){
        banner = new JLabel("", JLabel.CENTER);

        banner.setBackground(new java.awt.Color((int)(colorChoosed.getRedComp()*255), 
						(int)(colorChoosed.getGreenComp()*255),
						(int)(colorChoosed.getBlueComp()*255)
						));
        banner.setOpaque(true);
        banner.setPreferredSize(new Dimension(25, 25));

        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.add(banner, BorderLayout.CENTER);
        bannerPanel.setBorder(BorderFactory.createTitledBorder("Couleur choisie"));
 

        colorChooser = new JColorChooser(banner.getBackground());
        colorChooser.getSelectionModel().addChangeListener(this);
        colorChooser.setBorder(BorderFactory.createTitledBorder(name));
        colorChooser.setPreviewPanel(banner); 

        this.add(colorChooser, BorderLayout.NORTH);
        this.add(bannerPanel, BorderLayout.CENTER);
    }

    /** 
     * 
     */
    public void stateChanged(ChangeEvent e){
        java.awt.Color color = this.colorChooser.getColor();
        this.colorChoosed = new raytracing.scene.Color((double)color.getRed()/255, (double) color.getGreen()/255, (double) color.getBlue()/255);
        this.banner.setBackground(color);
    }

    /** Obtenir la couleur saisie par l'utilisateur
     * @return La couleur saisie.
     */
    public Color getColor(){
        return colorChoosed;
    }



}
