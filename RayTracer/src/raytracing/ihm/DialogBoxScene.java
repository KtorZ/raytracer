/** Une boite de dialogue pour la scène
 * @author Matthias Benkort
 * @version 1.0
 */
package raytracing.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import raytracing.scene.geometry.Point;
import raytracing.scene.positionable.element.*;
import raytracing.scene.positionable.*;
import raytracing.scene.Color;
import raytracing.scene.Scene;

public class DialogBoxScene extends JDialog{
    private JTextField name, iRef, nbDev;
    private JLabel error;
    private JPanel globalPanel, panelError; 
    private PanelColor panelColor, panelColorInfinity;
    private JButton createButton, cancelButton;
	
    private Scene scene;
    private MenuBar menuBar;
    private Content content;

    /** Creation et affichage d'une fenetre de saisie
     * @param content Content de la fenetre principale
     */
    public DialogBoxScene(Scene scene, Content content, MenuBar menuBar){
        this.scene = scene;
        this.menuBar = menuBar;
        this.content = content;
	this.initBoxScene();
	this.initBoxSceneControllers();
	this.setVisible(true);						
    }
	
    /** Initialiser la fenetre de saisie d'une nouvelle scene */
    private void initBoxScene(){
	this.setResizable(false);
	this.setModal(true);
		
	//Initialisation du container principale
	this.globalPanel = new JPanel();
	this.globalPanel.setLayout(new BoxLayout(globalPanel, BoxLayout.PAGE_AXIS));
	this.globalPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	this.setContentPane(globalPanel);
	this.setTitle("Nouvelle scène");

	//Demander le nom
	JPanel panelName = new JPanel(new FlowLayout());
	panelName.setBorder(BorderFactory.createTitledBorder("Nom de la scène"));		
	this.name = new JTextField(20);			
	panelName.add(this.name);
	this.globalPanel.add(panelName);

	//Demander l'indice de refraction
	JPanel panelIRef = new JPanel(new FlowLayout());
	panelIRef.setBorder(BorderFactory.createTitledBorder("Indice de réfraction du milieu"));
	//Coordonnees
	this.iRef = new JTextField(3);
	panelIRef.add(this.iRef);
	this.globalPanel.add(panelIRef);

	//Demander le nombre de déviation
	JPanel panelNbDev = new JPanel(new FlowLayout());
	panelNbDev.setBorder(BorderFactory.createTitledBorder("Nombre de déviation max"));		
	this.nbDev = new JTextField(3);
	panelNbDev.add(this.nbDev);
	this.globalPanel.add(panelNbDev);
		
		
	JPanel panelColors = new JPanel(new FlowLayout());
	//Demander la couleur ambiante
	this.panelColor = new PanelColor("Choix de la lumière ambiante");
	panelColors.add(this.panelColor);

	//Demander la couleur de l'infini
	this.panelColorInfinity = new PanelColor("Choix de la couleur de l'infini");
	panelColors.add(this.panelColorInfinity);
	this.globalPanel.add(panelColors);
		
	//Afficher les messages d'erreur
	panelError = new JPanel(new FlowLayout());
	panelError.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));		
	error = new JLabel("");
	error.setForeground(java.awt.Color.RED);
	panelError.add(error);
	this.globalPanel.add(panelError);

	//Creer les bouton
	JPanel panelButton = new JPanel(new FlowLayout());
	panelButton.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));	
		
	createButton = new JButton("Créer");
	panelButton.add(createButton);	
		
	cancelButton = new JButton("Annuler");
	panelButton.add(cancelButton);
	this.globalPanel.add(panelButton);

		
	this.pack();
	this.setLocationRelativeTo(null);
    }
	
    private void initBoxSceneControllers(){
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);        
		
	//Ajouter une scene
	this.createButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    try{
			//Récupération des données et Mise à jour de la scene.
			Color cAmbient = panelColor.getColor();
			Color cInfinity = panelColorInfinity.getColor();
			scene.setAmbientLight(cAmbient);
			scene.setInfinityColor(cInfinity);
			scene.setIRefraction(Double.parseDouble(iRef.getText()));
			scene.setName(name.getText());  
			scene.setNbIter(Integer.parseInt(nbDev.getText()));

			//Réinitialisation des éléments
			scene.removeAll();
			content.removeAllTab();
			content.removeAllElementFromList();
					
			//Mise à jour de la barre de menu et du Content.
			menuBar.getMenuItem("Scene", "Ajouter sphere").setEnabled(true);
			menuBar.getMenuItem("Scene", "Ajouter cube").setEnabled(true);
			menuBar.getMenuItem("Scene", "Ajouter plan infini").setEnabled(true);
			menuBar.getMenuItem("Scene", "Ajouter lumiere").setEnabled(true);
			menuBar.getMenuItem("Scene", "Ajouter point de vue").setEnabled(true);

			error.setText("");					
			dispose();
		    }catch(NumberFormatException eNF){
			error.setText("Nombre attendu");
			panelError.validate();
			pack();
		    }catch(IllegalArgumentException eIA){
			error.setText(eIA.getMessage());
			panelError.validate();
			pack();
		    }
		}
	    });	
		
	//Annuler la creation
	this.cancelButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    dispose();
		}
	    });		
    }
}
