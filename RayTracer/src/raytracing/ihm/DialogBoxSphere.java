/** Une boite de dialogue pour la sphere
 * @author Matthias Benkort
 * @version 1.0
 */
package raytracing.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Hashtable;
import raytracing.scene.geometry.Point;
import raytracing.scene.positionable.element.Material;
import raytracing.scene.positionable.element.Sphere;
import raytracing.scene.Color;
import raytracing.scene.Scene;

public class DialogBoxSphere extends JDialog{
    private JTextField xCoord, yCoord, zCoord, radius, name;
    private JLabel error;
    private JPanel globalPanel, panelError; 
    private PanelColor panelColor;
    private PanelMaterial panelMaterial;
    private JButton addButton, cancelButton, updateButton;
	
    private Scene scene;
    private Content content;
    private Sphere s, sModif;

    /** Creation et affichage d'une fenetre de saisie
     * @param scene La scene a commander
     * @param content Content de la fenetre principale
     */
    public DialogBoxSphere(Scene scene, Content content){
	this.scene = scene;
	this.content = content;
	this.initBoxSphere(null);
	this.initBoxSphereControllers();
	this.setVisible(true);						
    }

    /** Modification et affichage d'une fenetre de saisie
     * @param scene La scene a commander
     * @param content Content de la fenetre principale
     * @param La sphere a modifier
     */
    public DialogBoxSphere(Scene scene, Content content, Sphere s){
	this.scene = scene;
        this.s = s;
	this.content = content;
	this.initBoxSphere(s);
	this.initBoxSphereControllers();
	this.setVisible(true);	
    }

    /** Initialiser la fenetre de saisie d'une nouvelle sphere */
    private void initBoxSphere(Sphere s){
	this.setResizable(false);
	this.setModal(true);
		
	//Initialisation du container principale
	this.globalPanel = new JPanel();
	this.globalPanel.setLayout(new BoxLayout(globalPanel, BoxLayout.PAGE_AXIS));
	this.globalPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	this.setContentPane(globalPanel);
	this.setTitle("Ajouter une sphère");

	//Demander le nom
	JPanel panelName = new JPanel(new FlowLayout());
	panelName.setBorder(BorderFactory.createTitledBorder("Nom de la sphère"));		
	this.name = new JTextField(20);			      
	panelName.add(this.name);
	this.globalPanel.add(panelName);

	//Demander le centre
	JPanel panelCentre = new JPanel(new FlowLayout());
	panelCentre.setBorder(BorderFactory.createTitledBorder("Centre de la sphère"));
	//Coordonnees
	panelCentre.add(new JLabel("x : "));
	this.xCoord = new JTextField(3);
	panelCentre.add(this.xCoord);

	panelCentre.add(new JLabel("y : "));
	this.yCoord = new JTextField(3);
	panelCentre.add(this.yCoord);

	panelCentre.add(new JLabel("z : "));
	this.zCoord = new JTextField(3);
	panelCentre.add(this.zCoord);
            
    

	this.globalPanel.add(panelCentre);

	//Demander le rayon
	JPanel panelRadius = new JPanel(new FlowLayout());
	panelRadius.setBorder(BorderFactory.createTitledBorder("Rayon de la sphère"));		
	this.radius = new JTextField(20);
	panelRadius.add(this.radius);
	this.globalPanel.add(panelRadius);
		
	//Demander la couleur
        if(s!=null){
            panelColor = new PanelColor("Choix de la couleur", s.getMaterial().getCAmbient());
        }else{
	    panelColor = new PanelColor("Choix de la couleur");
        }
	this.globalPanel.add(panelColor);
		
	//Demander le materiau
        if(s!=null){
	    panelMaterial = new PanelMaterial(s.getMaterial());
	    if(s.getMaterial().toString().equals("Materiau personnalise")){
		panelMaterial.getPanelCoeffMaterial().setVisible(true);
		panelMaterial.validate();
		pack();
	    }
        }else{
	    panelMaterial = new PanelMaterial();
        }
	this.globalPanel.add(panelMaterial);
		
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
		
        updateButton = new JButton("Modifier");
	addButton = new JButton("Ajouter");

        if(s != null){
	    addButton.setVisible(false);	        
        }else{
	    updateButton.setVisible(false);	
	}
		
	panelButton.add(updateButton);	        
	panelButton.add(addButton);	
		
	cancelButton = new JButton("Annuler");
	panelButton.add(cancelButton);
	this.globalPanel.add(panelButton);


        if(s != null){
            this.name.setText(s.getName());            
            this.radius.setText(""+s.getRadius());
            this.xCoord.setText(""+s.getCenter().getX());
            this.yCoord.setText(""+s.getCenter().getY());
            this.zCoord.setText(""+s.getCenter().getZ());
	}  
		
	this.pack();
	this.setLocationRelativeTo(null);
    }
	
    private void initBoxSphereControllers(){
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);        
		
	//Apparition de la section personnalisee des materiaux
	this.panelMaterial.getMaterialList().addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    JComboBox source = (JComboBox)e.getSource();
		    if(source.getSelectedItem().toString().equals("Materiau personnalise")){
			panelMaterial.getPanelCoeffMaterial().setVisible(true);
			panelMaterial.validate();
			pack();
		    }else{
			panelMaterial.getPanelCoeffMaterial().setVisible(false);
			panelMaterial.validate();
			pack();
		    }
		}
	    });
		
	//Ajouter une sphere
	this.addButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    try{
			Point center = new Point(Double.parseDouble(xCoord.getText()), Double.parseDouble(yCoord.getText()), Double.parseDouble(zCoord.getText()));
			Color cAmbient = panelColor.getColor();
			Color cDiffuse = panelColor.getColor();
			Material material = panelMaterial.getMaterial(cAmbient, cDiffuse);
			s = new Sphere(center, Double.parseDouble(radius.getText()), name.getText(), material);					
			scene.addElement(s);
			content.addElementToList(s);
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
		    }catch(RuntimeException eRt){
			scene.removeElement(s);					
			error.setText(eRt.getMessage());					
			pack();
		    }
		}
	    });	
 
	//modifier une sphere
	this.updateButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    try{
			Point center = new Point(Double.parseDouble(xCoord.getText()), Double.parseDouble(yCoord.getText()), Double.parseDouble(zCoord.getText()));
			Color cAmbient = panelColor.getColor();
			Color cDiffuse = panelColor.getColor();
			Material material = panelMaterial.getMaterial(cAmbient, cDiffuse);	
			sModif = new Sphere(center, Double.parseDouble(radius.getText()), name.getText(), material);
					
			scene.updateElement(s, sModif);
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
		    }catch(RuntimeException eRt){
			scene.removeElement(sModif);
			content.removeElementFromList(s);					
			updateButton.setVisible(false);
			addButton.setVisible(true);						
			error.setText(eRt.getMessage());					
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
