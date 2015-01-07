/** Une boite de dialogue pour les sources de lumière
 * @author Herve Maxime
 */

package raytracing.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Hashtable;
import raytracing.scene.geometry.Point;
import raytracing.scene.positionable.SpotLight;
import raytracing.scene.Color;
import raytracing.scene.Scene;

public class DialogBoxSpotLight extends JDialog{
    private JTextField field, name, xCoord, yCoord, zCoord  ;
    private JButton addButton, cancelButton, updateButton;
    private JLabel error;
    private JPanel globalPanel , panelError ; 
    private PanelColor panelColor ;
	
    private Scene scene;
    private SpotLight s, sModif; 
	
    private Content content;
	
    /** Creation et affichage d'une fenetre de saisie
     * @param scene La scene a commander
     * @param content Content de la fenetre principale
     */
    public DialogBoxSpotLight(Scene scene, Content content){
	this.scene = scene;
	this.content = content;
	this.initBoxSpotLight(null);
	this.initBoxSpotLightControllers();
	this.setVisible(true);
    }
	
    /** Creation et affichage d'une fenetre de saisie
     * @param scene La scene a commander
     * @param content Content de la fenetre principale
     */
    public DialogBoxSpotLight(Scene scene, Content content, SpotLight s){
	this.scene = scene;
	this.s = s;
	this.content = content;
	this.initBoxSpotLight(s);
	this.initBoxSpotLightControllers();
	this.setVisible(true);
    }
	
    /** Initialiser la fenetre de saisie d'une nouvelle source de lumiere */
    public void initBoxSpotLight(SpotLight s){
	this.setResizable(false);
	this.setModal(true);
		
	//Initialisation du container principale
	this.globalPanel = new JPanel();
	this.globalPanel.setLayout(new BoxLayout(globalPanel, BoxLayout.PAGE_AXIS));
	this.globalPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	this.setContentPane(globalPanel);
	this.setTitle("Ajouter une source de lumière ");

	// Nom
	JPanel panelName = new JPanel(new FlowLayout());
	panelName.setBorder(BorderFactory.createTitledBorder("Nom de la source"));		
	this.name = new JTextField(20);			
	panelName.add(this.name);
	this.globalPanel.add(panelName);

	// Position
	JPanel panelPosition = new JPanel(new FlowLayout());
	panelPosition.setBorder(BorderFactory.createTitledBorder("Position de la source"));		
	//Coordonnees
	panelPosition.add(new JLabel("x : "));
	this.xCoord = new JTextField(3);
	panelPosition.add(this.xCoord);

	panelPosition.add(new JLabel("y : "));
	this.yCoord = new JTextField(3);
	panelPosition.add(this.yCoord);

	panelPosition.add(new JLabel("z : "));
	this.zCoord = new JTextField(3);
	panelPosition.add(this.zCoord);
	this.globalPanel.add(panelPosition);

	//Demander la couleur
	if(s!=null){
	    panelColor = new PanelColor("Choix de la couleur", s.getColor());
	}else{
	    panelColor = new PanelColor("Choix de la couleur");		
	}
	this.globalPanel.add(panelColor);

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

		
	this.addButton = new JButton("Ajouter");
	this.updateButton = new JButton("Modifier");
		
	if(s!=null){
	    panelButton.add(this.updateButton);	
	}else{
	    panelButton.add(this.addButton);			
	}
		
	//Annuler la creation.
	this.cancelButton = new JButton("Annuler");
	panelButton.add(this.cancelButton);
	this.globalPanel.add(panelButton);

	if(s != null){
	    this.name.setText(s.getName());
	    this.xCoord.setText(""+s.getPosition().getX());
	    this.yCoord.setText(""+s.getPosition().getY());
	    this.zCoord.setText(""+s.getPosition().getZ());	
	}
		
	this.pack();
	this.setLocationRelativeTo(null);
    }
    private void initBoxSpotLightControllers(){
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
		
	//Modifier une source de lumiere
	this.updateButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    try{
			Point position = new Point(Double.parseDouble(xCoord.getText()), Double.parseDouble(yCoord.getText()), Double.parseDouble(zCoord.getText()));
			Color cDiffuse = panelColor.getColor();
			sModif = new SpotLight(position, cDiffuse , name.getText());
			scene.updateSpotLight(s, sModif);
			content.removeElementFromList(s);
			content.addElementToList(sModif);					
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
			eIA.printStackTrace();
		    }catch(RuntimeException eRt){
			error.setText(eRt.getMessage());
			scene.removeSpotLight(sModif);
			pack();	
		    }
		}

	    });
		
	//Ajouter une source de lumiere
	this.addButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    try{
			Point position = new Point(Double.parseDouble(xCoord.getText()), Double.parseDouble(yCoord.getText()), Double.parseDouble(zCoord.getText()));
			Color cDiffuse = panelColor.getColor();
			s = new SpotLight(position, cDiffuse , name.getText());
			error.setText("");
			scene.addSpotLight(s);
			content.addElementToList(s);					
			dispose();
		    }catch(NumberFormatException eNF){
			error.setText("Nombre attendu");
			panelError.validate();
			pack();
		    }catch(IllegalArgumentException eIA){
			error.setText(eIA.getMessage());
			panelError.validate();
			pack();
			eIA.printStackTrace();
		    }catch(RuntimeException eRt){
			error.setText(eRt.getMessage());
			scene.removeSpotLight(s);
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



