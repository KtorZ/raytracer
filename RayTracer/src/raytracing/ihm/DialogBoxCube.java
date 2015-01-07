/**
 * Une boîte de dialogue pour définir un cube.
 * @author Nicolas Gaborit
 * @version 1.2
 */
package raytracing.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Hashtable;
import raytracing.scene.geometry.Point;
import raytracing.scene.positionable.element.Material;
import raytracing.scene.positionable.element.Cube;
import raytracing.scene.Color;
import raytracing.scene.Scene;

public class DialogBoxCube extends JDialog{    
    private JTextField name,
	xCoord1, yCoord1, zCoord1,
	xCoord2, yCoord2, zCoord2,
	xCoord3, yCoord3, zCoord3;
    
    private JLabel error;
    private JPanel globalPanel, panelError; 
    private PanelColor panelColor;
    private PanelMaterial panelMaterial;
    private JButton addButton, cancelButton, updateButton;
    
    private Content content;
    private Cube cube, cubeModif;
    private Scene scene;
    

    /** Creation et affichage d'une fenetre de saisie
     * @param scene La scene a commander
     * @param content Content de la fenetre principale
     */
    public DialogBoxCube(Scene scene, Content content){
	this.scene = scene;
	this.content = content;
	this.initBoxCube(null);
	this.initBoxCubeControllers();
	this.setVisible(true);						
    }
	
    /** Creation et affichage d'une fenetre de saisie
     * @param scene La scene a commander
     * @param content Content de la fenetre principale
     * @param cube Cube à modifier
     */
    public DialogBoxCube(Scene scene, Content content, Cube cube){
	this.scene = scene;
	this.cube = cube;
	this.content = content;
	this.initBoxCube(cube);
	this.initBoxCubeControllers();
	this.setVisible(true);						
    }  
    /** Initialiser la fenetre de saisie d'une nouvelle cube */
    private void initBoxCube(Cube cube){
	this.setResizable(false);
	this.setModal(true);
		
	//Initialisation du container principale
	this.globalPanel = new JPanel();
	this.globalPanel.setLayout(new BoxLayout(globalPanel, BoxLayout.PAGE_AXIS));
	this.globalPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	this.setContentPane(globalPanel);
	this.setTitle("Ajouter un cube");

	//Demander le nom
	JPanel panelName = new JPanel(new FlowLayout());
	panelName.setBorder(BorderFactory.createTitledBorder("Nom du cube"));		
	this.name = new JTextField(20);
	panelName.add(this.name);
	this.globalPanel.add(panelName);

	//Demander les trois points
	JPanel panel3Points = new JPanel(new BorderLayout());
	panel3Points.setBorder(BorderFactory.createTitledBorder("Position du cube"));


	//Coordonnees
	JPanel panelCoord = new JPanel(new FlowLayout());
	panelCoord.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));		

	JPanel panelPoint1 = new JPanel();
	panelPoint1.setLayout(new BoxLayout(panelPoint1, BoxLayout.PAGE_AXIS));
	panelPoint1.setBorder(BorderFactory.createTitledBorder("Point 1"));

	JPanel panelPoint2 = new JPanel();
	panelPoint2.setLayout(new BoxLayout(panelPoint2, BoxLayout.PAGE_AXIS));
	panelPoint2.setBorder(BorderFactory.createTitledBorder("Point 2"));

	JPanel panelPoint3 = new JPanel();
	panelPoint3.setLayout(new BoxLayout(panelPoint3, BoxLayout.PAGE_AXIS));
	panelPoint3.setBorder(BorderFactory.createTitledBorder("Point 3"));

	panelPoint1.add(new JLabel("x1 : "));
	this.xCoord1 = new JTextField(3);
	panelPoint1.add(this.xCoord1);
		
	panelPoint1.add(new JLabel("y1 : "));
	this.yCoord1 = new JTextField(3);
	panelPoint1.add(this.yCoord1);
		
	panelPoint1.add(new JLabel("z1 : "));
	this.zCoord1 = new JTextField(3);
	panelPoint1.add(this.zCoord1);
		
	panelPoint2.add(new JLabel("x2 : "));
	this.xCoord2 = new JTextField(3);
	panelPoint2.add(this.xCoord2);
		
	panelPoint2.add(new JLabel("y2 : "));
	this.yCoord2 = new JTextField(3);
	panelPoint2.add(this.yCoord2);
		
	panelPoint2.add(new JLabel("z2 : "));
	this.zCoord2 = new JTextField(3);
	panelPoint2.add(this.zCoord2);
		
	panelPoint3.add(new JLabel("x3 : "));
	this.xCoord3 = new JTextField(3);
	panelPoint3.add(this.xCoord3);
		
	panelPoint3.add(new JLabel("y3 : "));
	this.yCoord3 = new JTextField(3);
	panelPoint3.add(this.yCoord3);

	panelPoint3.add(new JLabel("z3 : "));
	this.zCoord3 = new JTextField(3);
	panelPoint3.add(this.zCoord3);
		
	panelCoord.add(panelPoint1);
	panelCoord.add(panelPoint2);
	panelCoord.add(panelPoint3);

	panel3Points.add(panelCoord, BorderLayout.WEST);
	//Schema explicatif
	JLabel labelTest = new JLabel();
	labelTest.setIcon(new ImageIcon("raytracing/ihm/Cube_Scheme.png"));
	labelTest.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));		

	panel3Points.add(labelTest, BorderLayout.EAST);
		
	this.globalPanel.add(panel3Points);



	//Demander la couleur
	if(cube!=null){
	    panelColor = new PanelColor("Choix de la couleur", cube.getMaterial().getCAmbient());
	}else{
	    panelColor = new PanelColor("Choix de la couleur");	
	}
	this.globalPanel.add(panelColor);
			
	//Demander le materiau
	if(cube!=null){
	    panelMaterial = new PanelMaterial(cube.getMaterial());
	    if(cube.getMaterial().toString().equals("Materiau personnalise")){
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

	if(cube != null){
	    addButton.setVisible(false);	        
	}else{
	    updateButton.setVisible(false);	
	}
		
	panelButton.add(updateButton);	        
	panelButton.add(addButton);	
			
	cancelButton = new JButton("Annuler");
	panelButton.add(cancelButton);
	this.globalPanel.add(panelButton);

	if(cube != null){
	    this.name.setText(cube.getName());
	    Point vertexs[] = cube.getVertexs();
			
	    this.xCoord1.setText(""+vertexs[0].getX());
	    this.yCoord1.setText(""+vertexs[0].getY());
	    this.zCoord1.setText(""+vertexs[0].getZ());
			
	    this.xCoord2.setText(""+vertexs[1].getX());
	    this.yCoord2.setText(""+vertexs[1].getY());
	    this.zCoord2.setText(""+vertexs[1].getZ());
			
	    this.xCoord3.setText(""+vertexs[2].getX());
	    this.yCoord3.setText(""+vertexs[2].getY());
	    this.zCoord3.setText(""+vertexs[2].getZ());
	}
			
	this.pack();
	this.setLocationRelativeTo(null);
    }
	
    private void initBoxCubeControllers(){
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
		
		
	//Ajouter un cube
	this.addButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    try{
			Point p_vertexs[] = new Point[3];
			p_vertexs[0] = new Point(Double.parseDouble(xCoord1.getText()), Double.parseDouble(yCoord1.getText()), Double.parseDouble(zCoord1.getText()));
			p_vertexs[1] = new Point(Double.parseDouble(xCoord2.getText()), Double.parseDouble(yCoord2.getText()), Double.parseDouble(zCoord2.getText()));
			p_vertexs[2] = new Point(Double.parseDouble(xCoord3.getText()), Double.parseDouble(yCoord3.getText()), Double.parseDouble(zCoord3.getText()));
			Color cAmbient = panelColor.getColor();
			Color cDiffuse = panelColor.getColor();
			Material material = panelMaterial.getMaterial(cAmbient, cDiffuse);
			cube = new Cube(p_vertexs, name.getText(), material);
					
			scene.addElement(cube);
			content.addElementToList(cube);			
					
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
			scene.removeElement(cube);
			pack();
		    }
		}
	    });	
			
	//Modifier un cube
	this.updateButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    try{
			Point p_vertexs[] = new Point[3];
			p_vertexs[0] = new Point(Double.parseDouble(xCoord1.getText()), Double.parseDouble(yCoord1.getText()), Double.parseDouble(zCoord1.getText()));
			p_vertexs[1] = new Point(Double.parseDouble(xCoord2.getText()), Double.parseDouble(yCoord2.getText()), Double.parseDouble(zCoord2.getText()));
			p_vertexs[2] = new Point(Double.parseDouble(xCoord3.getText()), Double.parseDouble(yCoord3.getText()), Double.parseDouble(zCoord3.getText()));
			Color cAmbient = panelColor.getColor();
			Color cDiffuse = panelColor.getColor();
			Material material = panelMaterial.getMaterial(cAmbient, cDiffuse);
			cubeModif = new Cube(p_vertexs, name.getText(), material);
									
			scene.updateElement(cube, cubeModif);
			content.removeElementFromList(cube);
			content.addElementToList(cubeModif);	
					
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
			scene.removeElement(cubeModif);	
			content.removeElementFromList(cube);				
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
