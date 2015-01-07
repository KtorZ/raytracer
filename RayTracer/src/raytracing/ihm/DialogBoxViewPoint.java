/** Une boite de dialogue pour les points de vue
 *@author Herve Maxime
 *@version 1.2
 */

package raytracing.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Hashtable;
import raytracing.scene.geometry.Point;
import raytracing.scene.geometry.Vector;
import raytracing.scene.positionable.SpotLight;
import raytracing.scene.Color;
import raytracing.scene.Scene;
import raytracing.scene.positionable.ViewPoint;



public class DialogBoxViewPoint extends JDialog {

    private JTextField name ;
    private JTextField fov ;
    private JTextField resI , resJ ;
    private JTextField xCoordSource , yCoordSource , zCoordSource ;
    private JTextField xCoordLook , yCoordLook , zCoordLook ;
    private JTextField xCoordVertical , yCoordVertical , zCoordVertical ;
    private JButton addButton, cancelButton, updateButton;
    private JLabel error;
    private JPanel globalPanel , panelError ;


    private Scene scene;
    private ViewPoint v, vModif ;
    private Content content ;
	
    /** Modification et affichage d'une fenetre de saisie
     * @param scene La scene a commander
     * @param content Content de la fenetre principale
     */
    public DialogBoxViewPoint(Scene scene , Content content){
	this.scene = scene;
	this.content = content;
	this.initBoxViewPoint(null);
	this.initBoxViewPointControllers();
	this.setVisible(true);
    } 
	
    /** Modification et affichage d'une fenetre de saisie
     * @param scene La scene a commander
     * @param content Content de la fenetre principale
     * @param Le point de vue a modifier
     */
    public DialogBoxViewPoint(Scene scene , Content content, ViewPoint v){
	this.scene = scene;
	this.v = v;
	this.content = content;
	this.initBoxViewPoint(v);
	this.initBoxViewPointControllers();
	this.setVisible(true);
    } 
	
    /** Initialiser la fenetre de saisie d'une nouvelle sphere */
    public void initBoxViewPoint(ViewPoint v){
	this.setResizable(false);
	this.setModal(true);
		
	//Initialisation du container principale
	this.globalPanel = new JPanel();
	this.globalPanel.setLayout(new BoxLayout(globalPanel, BoxLayout.PAGE_AXIS));
	this.globalPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	this.setContentPane(globalPanel);
	this.setTitle("Ajouter un point de vue ");

	// Nom
	JPanel panelName = new JPanel(new FlowLayout());
	panelName.setBorder(BorderFactory.createTitledBorder("Nom du point de vue"));		
	this.name = new JTextField(20);			
	panelName.add(this.name);
	this.globalPanel.add(panelName);

	JPanel panelEcranSchem = new JPanel(new FlowLayout());	
	JPanel panelGaucheEcranSchem = new JPanel();
	panelGaucheEcranSchem.setLayout(new BoxLayout(panelGaucheEcranSchem, BoxLayout.PAGE_AXIS));
		
	//Source
	JPanel panelSource = new JPanel(new FlowLayout());
	panelSource.setBorder(BorderFactory.createTitledBorder("Position de la source"));
			
	panelSource.add(new JLabel("x : "));
	this.xCoordSource = new JTextField(3);
	panelSource.add(this.xCoordSource);

	panelSource.add(new JLabel("y : "));
	this.yCoordSource = new JTextField(3);
	panelSource.add(this.yCoordSource);

	panelSource.add(new JLabel("z : "));
	this.zCoordSource = new JTextField(3);
	panelSource.add(this.zCoordSource);

	panelGaucheEcranSchem.add(panelSource);
			
	// Direction du regard 
	JPanel panelLook = new JPanel( new FlowLayout());
	panelLook.setBorder(BorderFactory.createTitledBorder("Direction du regard"));
			
	panelLook.add(new JLabel("x : "));
	this.xCoordLook = new JTextField(3);
	panelLook.add(this.xCoordLook);

				
	panelLook.add( new JLabel("y : "));
	this.yCoordLook = new JTextField(3);
	panelLook.add(this.yCoordLook);

				
	panelLook.add( new JLabel("z : "));
	this.zCoordLook = new JTextField(3);
	panelLook.add(this.zCoordLook);

	panelGaucheEcranSchem.add(panelLook);
			
	// Verticale de l'écran
	JPanel panelVertical = new JPanel(new FlowLayout());
	panelVertical.setBorder(BorderFactory.createTitledBorder("Verticale de l'écran"));

	panelVertical.add(new JLabel("x : "));
	this.xCoordVertical = new JTextField(3);
	panelVertical.add(this.xCoordVertical);


	panelVertical.add(new JLabel("y : "));
	this.yCoordVertical = new JTextField(3);
	panelVertical.add(this.yCoordVertical);


	panelVertical.add(new JLabel("z : "));
	this.zCoordVertical = new JTextField(3);
	panelVertical.add(this.zCoordVertical);


	panelGaucheEcranSchem.add(panelVertical);



	// Angle de vision
	JPanel panelFov = new JPanel(new FlowLayout());
	panelFov.setBorder(BorderFactory.createTitledBorder("Angle de vision"));

	panelFov.add(new JLabel(" Angle de vision : "));
	this.fov = new JTextField(3);
	panelFov.add(this.fov);

	panelGaucheEcranSchem.add(panelFov);
			
	//Schéma explicatif
	JLabel labelTest = new JLabel();
	labelTest.setIcon(new ImageIcon("raytracing/ihm/ViewPoint_Scheme.png"));
	labelTest.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));	
			
	panelEcranSchem.add(panelGaucheEcranSchem);
	panelEcranSchem.add(labelTest);
	this.globalPanel.add(panelEcranSchem);
		
	// Resolution de l'ecran
	JPanel panelResolution = new JPanel( new FlowLayout());
	panelResolution.setBorder(BorderFactory.createTitledBorder("Résolution de l'écran"));

	panelResolution.add(new JLabel("Largeur de l'écran : "));
	this.resI = new JTextField(4);
	panelResolution.add(this.resI);

			

	panelResolution.add(new JLabel("Hauteur de l'écran : "));
	this.resJ = new JTextField(4);
	panelResolution.add(this.resJ);

	this.globalPanel.add(panelResolution);



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

        if(v != null){
	    addButton.setVisible(false);	        
        }else{
	    updateButton.setVisible(false);	
	}
		
	panelButton.add(updateButton);	        
	panelButton.add(addButton);	

	//Annuler la creation.
	this.cancelButton = new JButton("Annuler");
	panelButton.add(this.cancelButton);
	this.globalPanel.add(panelButton);
		
	if(v != null){
	    this.name.setText(v.getName());
	    this.fov.setText(""+v.getFov());
	    this.resI.setText(""+v.getResI());
	    this.resJ.setText(""+v.getResJ());
			
	    this.xCoordSource.setText(""+v.getSource().getX());
	    this.yCoordSource.setText(""+v.getSource().getY());
	    this.zCoordSource.setText(""+v.getSource().getZ());
			
	    this.xCoordLook.setText(""+v.getLook().getX());
	    this.yCoordLook.setText(""+v.getLook().getY());
	    this.zCoordLook.setText(""+v.getLook().getZ());
			
	    this.xCoordVertical.setText(""+v.getVertical().getX());
	    this.yCoordVertical.setText(""+v.getVertical().getY());
	    this.zCoordVertical.setText(""+v.getVertical().getZ());
	}

	this.pack();
	this.setLocationRelativeTo(null);


    }

    private void initBoxViewPointControllers(){
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		
	//Ajouter un point de vue 
	this.addButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    try{				
			Point source = new Point(Double.parseDouble(xCoordSource.getText()), Double.parseDouble(yCoordSource.getText()), Double.parseDouble(zCoordSource.getText()));
			Vector look = new Vector(Double.parseDouble(xCoordLook.getText()), Double.parseDouble(yCoordLook.getText()), Double.parseDouble(zCoordLook.getText()));
			Vector vertical = new Vector(Double.parseDouble(xCoordVertical.getText()), Double.parseDouble(yCoordVertical.getText()), Double.parseDouble(zCoordVertical.getText()));
			v = new ViewPoint(source ,look ,vertical ,Double.parseDouble(fov.getText()), Integer.parseInt(resI.getText()), Integer.parseInt(resJ.getText()), name.getText());															
				
			content.addTab(v);			
			scene.addViewPoint(v);	
			content.addElementToList(v);						
					
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
			scene.removeViewPoint(v);			
			content.removeTab(v);			
			pack();
		    }
		}
	    });	
		
	//Modifier un point de vue 
	this.updateButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    try{
			Point source = new Point(Double.parseDouble(xCoordSource.getText()), Double.parseDouble(yCoordSource.getText()), Double.parseDouble(zCoordSource.getText()));
			Vector look = new Vector(Double.parseDouble(xCoordLook.getText()), Double.parseDouble(yCoordLook.getText()), Double.parseDouble(zCoordLook.getText()));
			Vector vertical = new Vector(Double.parseDouble(xCoordVertical.getText()), Double.parseDouble(yCoordVertical.getText()), Double.parseDouble(zCoordVertical.getText()));
			vModif = new ViewPoint(source ,look ,vertical ,Double.parseDouble(fov.getText()), Integer.parseInt(resI.getText()), Integer.parseInt(resJ.getText()), name.getText()); 					            
					
			content.addTab(vModif);		
			content.removeTab(v);						
			scene.updateViewPoint(v, vModif);
			content.addElementToList(vModif);					
			content.removeElementFromList(v);
					
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
			scene.removeViewPoint(vModif);
			content.removeElementFromList(v);
			content.removeTab(vModif);						
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



		
		

