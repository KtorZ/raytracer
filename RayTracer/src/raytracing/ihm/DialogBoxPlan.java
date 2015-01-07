/** Une boite de dialogue pour le plan
 * @author Maxime Hervé
 * @version 1.2
 */
package raytracing.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Hashtable;
import raytracing.scene.geometry.Point;
import raytracing.scene.positionable.element.Material;
import raytracing.scene.positionable.element.Plan;
import raytracing.scene.Color;
import raytracing.scene.Scene;

public class DialogBoxPlan extends JDialog{
    private JTextField field, name;
    private JTextField xCoordOrigine, yCoordOrigine, zCoordOrigine;
    private JTextField xCoordPoint1, yCoordPoint1, zCoordPoint1;
    private JTextField xCoordPoint2, yCoordPoint2,zCoordPoint2;
	
    private JButton addButton, updateButton, cancelButton;
    private JLabel error;
    private JPanel globalPanel, panelError; 
    private PanelColor panelColor;
    private PanelMaterial panelMaterial;
	
    private Scene scene;
    private Content content;
    private Plan p, pModif;

    /** Creation et affichage d'une fenetre de saisie
     * @param scene La scene a commander
     * @param content Content de la fenetre principale
     */
    public DialogBoxPlan(Scene scene, Content content){
	this.scene = scene;
	this.content = content;
	this.initBoxPlan(null);
	this.initBoxPlanControllers();
	this.setVisible(true);
    }
	
    /** Creation et affichage d'une fenetre de saisie
     * @param scene La scene a commander
     * @param content Content de la fenetre principale
     * @param Plan a modifier
     */
    public DialogBoxPlan(Scene scene, Content content, Plan p){
	this.scene = scene;
	this.p = p;
	this.content = content;
	this.initBoxPlan(p);
	this.initBoxPlanControllers();
	this.setVisible(true);
    }
	
    /** Initialiser la fenetre de saisie d'un nouveau plan */
    public void initBoxPlan(Plan p) {
	this.setResizable(false);
	this.setModal(true);
	
	//Initialisation du container principale
	this.setTitle("Ajouter un plan infini");
	this.globalPanel = new JPanel();
	this.globalPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));		
	this.globalPanel.setLayout(new BoxLayout(globalPanel, BoxLayout.PAGE_AXIS));
	this.setContentPane(globalPanel);

	//Nom
	JPanel panelName = new JPanel(new FlowLayout());
	panelName.setBorder(BorderFactory.createTitledBorder("Nom du plan"));
	this.name = new JTextField(20);			
	panelName.add(this.name);
	this.globalPanel.add(panelName);

	//"Origine du plan"
	JPanel panelOrigine = new JPanel(new FlowLayout());
	panelOrigine.setBorder(BorderFactory.createTitledBorder("Origine du plan"));

	// Coordonee x de l'origine
	panelOrigine.add(new JLabel("x : " ));
	this.xCoordOrigine = new JTextField(3);
	panelOrigine.add(this.xCoordOrigine);

	// Coordonee y de l'origine
	panelOrigine.add(new JLabel("y : "));
	this.yCoordOrigine = new JTextField(3);
	panelOrigine.add(this.yCoordOrigine);

	// Coordonee z de l'origine
	panelOrigine.add(new JLabel("z : "));
	this.zCoordOrigine = new JTextField(3);
	panelOrigine.add(this.zCoordOrigine);

	this.globalPanel.add(panelOrigine);

	// Point 1
	JPanel panelPoint1 = new JPanel(new FlowLayout());
	panelPoint1.setBorder(BorderFactory.createTitledBorder("Point du plan #1"));

	//Coordonee x du point 1
	panelPoint1.add(new JLabel("x : "));
	this.xCoordPoint1 = new JTextField(3);
	panelPoint1.add(this.xCoordPoint1);

	//Coordonee y du point 1
	panelPoint1.add(new JLabel("y : "));
	this.yCoordPoint1 = new JTextField(3);
	panelPoint1.add(this.yCoordPoint1);


	//Coordonee z du point 1
	panelPoint1.add(new JLabel("z : "));
	this.zCoordPoint1 = new JTextField(3);
	panelPoint1.add(this.zCoordPoint1);

	this.globalPanel.add(panelPoint1);
		
	// Point 2
	JPanel panelPoint2 = new JPanel(new FlowLayout());
	panelPoint2.setBorder(BorderFactory.createTitledBorder("Point du plan #2"));		

	//Coordonee x du point 2
	panelPoint2.add(new JLabel("x : "));
	this.xCoordPoint2 = new JTextField(3);
	panelPoint2.add(this.xCoordPoint2);

	//Coordonee y du point 2
	panelPoint2.add(new JLabel("y : "));
	this.yCoordPoint2 = new JTextField(3);
	panelPoint2.add(this.yCoordPoint2);


	//Coordonee z du point 2
	panelPoint2.add(new JLabel("z : "));
	this.zCoordPoint2 = new JTextField(3);
	panelPoint2.add(this.zCoordPoint2);

	this.globalPanel.add(panelPoint2);

	//Demander la couleur
	//Demander la couleur
        if(p!=null){
            panelColor = new PanelColor("Choix de la couleur", p.getMaterial().getCAmbient());
        }else{
	    panelColor = new PanelColor("Choix de la couleur");
        }
	this.globalPanel.add(panelColor);
		
	//Demander le materiau
        if(p!=null){
	    panelMaterial = new PanelMaterial(p.getMaterial());
	    if(p.getMaterial().toString().equals("Materiau personnalise")){
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

        if(p != null){
	    addButton.setVisible(false);	        
        }else{
	    updateButton.setVisible(false);	
	}
		
	panelButton.add(updateButton);	        
	panelButton.add(addButton);	
		
	this.cancelButton = new JButton("Annuler");
	panelButton.add(this.cancelButton);
	this.globalPanel.add(panelButton);
		
	if(p != null){
	    this.name.setText(p.getName());
			
	    Point points[] = p.getPoints();
			
	    this.xCoordOrigine.setText(""+points[0].getX());
	    this.yCoordOrigine.setText(""+points[0].getY());
	    this.zCoordOrigine.setText(""+points[0].getZ());
			
	    this.xCoordPoint1.setText(""+points[1].getX());
	    this.yCoordPoint1.setText(""+points[1].getY());
	    this.zCoordPoint1.setText(""+points[1].getZ());
			
	    this.xCoordPoint2.setText(""+points[2].getX());
	    this.yCoordPoint2.setText(""+points[2].getY());
	    this.zCoordPoint2.setText(""+points[2].getZ());
	}
		
	this.pack();
	this.setLocationRelativeTo(null);
    }
    public void initBoxPlanControllers(){
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
		
	//Ajouter un plan
	this.addButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    try{
			Point[] pts = new Point[3]; 
			pts[0] =  new Point(Double.parseDouble(xCoordOrigine.getText()) , Double.parseDouble(yCoordOrigine.getText()) , Double.parseDouble(zCoordOrigine.getText()));
			pts[1] =  new Point(Double.parseDouble(xCoordPoint1.getText()) , Double.parseDouble(yCoordPoint1.getText()) , Double.parseDouble(zCoordPoint1.getText()));
			pts[2] =  new Point(Double.parseDouble(xCoordPoint2.getText()) , Double.parseDouble(yCoordPoint2.getText()) , Double.parseDouble(zCoordPoint2.getText()) );
			Color cAmbient = panelColor.getColor();
			Color cDiffuse = panelColor.getColor();
			Material material = panelMaterial.getMaterial(cAmbient, cDiffuse);
			p = new Plan(pts, name.getText(), material);
			error.setText("");
			scene.addElement(p);
			content.addElementToList(p);				
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
			error.setText(eRt.getMessage());
			scene.removeElement(p);
			pack();
		    }
		}
	    });	
		
	//Modifier un plan
	this.updateButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    try{
			Point[] pts = new Point[3]; 
			pts[0] =  new Point(Double.parseDouble(xCoordOrigine.getText()) , Double.parseDouble(yCoordOrigine.getText()) , Double.parseDouble(zCoordOrigine.getText()));
			pts[1] =  new Point(Double.parseDouble(xCoordPoint1.getText()) , Double.parseDouble(yCoordPoint1.getText()) , Double.parseDouble(zCoordPoint1.getText()));
			pts[2] =  new Point(Double.parseDouble(xCoordPoint2.getText()) , Double.parseDouble(yCoordPoint2.getText()) , Double.parseDouble(zCoordPoint2.getText()) );
			Color cAmbient = panelColor.getColor();
			Color cDiffuse = panelColor.getColor();
			Material material = panelMaterial.getMaterial(cAmbient, cDiffuse);
			pModif = new Plan(pts, name.getText(), material);
			scene.updateElement(p, pModif);
			content.removeElementFromList(p);						
			content.addElementToList(pModif);						
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
			error.setText(eRt.getMessage());
			updateButton.setVisible(false);
			addButton.setVisible(true);
			content.removeElementFromList(p);											
			scene.removeElement(pModif);
			pack();
		    }
		}
	    });	
		
	//Annuler la creation
	cancelButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    dispose();
		}
	    });		
    }
}
