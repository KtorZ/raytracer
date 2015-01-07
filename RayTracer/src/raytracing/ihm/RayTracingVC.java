/** Application permettant de simuler un lancer de rayon sur une scene 3D.
 * @author Benkort Matthias, Dolou Clement, Gaborit Nicolas, Herve Maxime 
 * @version 1.0
 */
package raytracing.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import raytracing.scene.positionable.ViewPoint;
import raytracing.scene.Color;
import raytracing.scene.Scene;
import java.awt.Image;

import raytracing.scene.positionable.*;
import raytracing.scene.positionable.element.*;
import raytracing.scene.geometry.Point;


public class RayTracingVC extends JFrame implements SceneObserver{
    MenuBar menuBar;
    Content content;
    Scene scene;
	

    /** Initialiser la vue à partir du modele
     * @param Scene scene
     */
    public RayTracingVC(String title){	
	super(title);
		
	this.menuBar = new MenuBar();
	this.content = new Content();
		
		
        this.scene = new Scene(new Color(0,0,0), new Color(0,0,0), 1, 0, "Default");
        this.scene.addObserver(this);
		
	this.setControllers();
		
	this.setJMenuBar(this.menuBar);
	this.setContentPane(this.content);		
    }
	
    /** Definir tous les controleurs associés aux divers elements de la vue.
     */
    private void setControllers(){
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	try{
	    /* Initialisation */
            this.menuBar.getMenuItem("Scene", "Ajouter sphere").setEnabled(false);
            this.menuBar.getMenuItem("Scene", "Ajouter cube").setEnabled(false);
            this.menuBar.getMenuItem("Scene", "Ajouter plan infini").setEnabled(false);
            this.menuBar.getMenuItem("Scene", "Ajouter lumiere").setEnabled(false);
            this.menuBar.getMenuItem("Scene", "Ajouter point de vue").setEnabled(false);
            this.menuBar.getMenuItem("Scene", "Supprimer l'element...").setEnabled(false);
            this.menuBar.getMenuItem("Scene", "Modifier l'element...").setEnabled(false);
            this.menuBar.getMenuItem("Scene", "Exporter vue...").setEnabled(false);
            
	    //---------------- Menu Scene

	    //Ajouter une sphere
	    this.menuBar.getMenuItem("Scene", "Ajouter sphere").addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
			DialogBoxSphere dialogBoxSphere = new DialogBoxSphere(scene, content);
			menuBar.getMenuItem("Scene", "Supprimer l'element...").setEnabled(true);								
			menuBar.getMenuItem("Scene", "Modifier l'element...").setEnabled(true);								
		    }
		});
			
	    //Ajouter un cube
	    this.menuBar.getMenuItem("Scene", "Ajouter cube").addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
			DialogBoxCube dialogBoxCube = new DialogBoxCube(scene, content);
			menuBar.getMenuItem("Scene", "Supprimer l'element...").setEnabled(true);								
			menuBar.getMenuItem("Scene", "Modifier l'element...").setEnabled(true);	
		    }
		});		
		
	    //Ajouter un plan
	    this.menuBar.getMenuItem("Scene", "Ajouter plan infini").addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
			DialogBoxPlan dialogBoxPlan = new DialogBoxPlan(scene, content);
			menuBar.getMenuItem("Scene", "Supprimer l'element...").setEnabled(true);								
			menuBar.getMenuItem("Scene", "Modifier l'element...").setEnabled(true);						
		    }
		});	
			
	    //Ajouter une source de lumiere
	    this.menuBar.getMenuItem("Scene", "Ajouter lumiere").addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
			DialogBoxSpotLight dialogBoxSpotLight = new DialogBoxSpotLight(scene, content);
			menuBar.getMenuItem("Scene", "Supprimer l'element...").setEnabled(true);								
			menuBar.getMenuItem("Scene", "Modifier l'element...").setEnabled(true);					
		    }
		});		
			
	    //Ajouter un point de vue		
	    this.menuBar.getMenuItem("Scene", "Ajouter point de vue").addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
			DialogBoxViewPoint dialogBoxViewPoint = new DialogBoxViewPoint(scene , content);
			menuBar.getMenuItem("Scene", "Supprimer l'element...").setEnabled(true);								
			menuBar.getMenuItem("Scene", "Modifier l'element...").setEnabled(true);	
			menuBar.getMenuItem("Scene", "Exporter vue...").setEnabled(true);								
		    }
		});		
			
	    //Supprimer un element
	    this.menuBar.getMenuItem("Scene", "Supprimer l'element...").addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
			Positionable pos = content.getSelectedPositionable();
					
			try{
			    if(pos == null)
				throw new IllegalArgumentException("Aucun objet selectionne");
							
			    int answer = JOptionPane.showConfirmDialog(null, "Etes-vous sur de vouloir supprimer : "+pos+" ?", "Suppression...",
								       JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						
			    if(answer == JOptionPane.YES_OPTION) {
				content.removeElementFromList(pos);
				if(pos instanceof Element){
				    scene.removeElement((Element)pos);
				}else if(pos instanceof SpotLight){
				    scene.removeSpotLight((SpotLight)pos);
				}else if(pos instanceof ViewPoint){
				    scene.removeViewPoint((ViewPoint)pos);
				    content.removeTab((ViewPoint)pos);
				    if(content.noMoreTab()){
					menuBar.getMenuItem("Scene", "Exporter vue...").setEnabled(false);
				    }
				}

				//On grise les menus qui ne doivent plus être accessible.
				if(content.ListIsEmpty()){
				    menuBar.getMenuItem("Scene", "Supprimer l'element...").setEnabled(false);
				    menuBar.getMenuItem("Scene", "Modifier l'element...").setEnabled(false);
				    menuBar.getMenuItem("Scene", "Exporter vue...").setEnabled(false);
				}
			    }
			}catch(IllegalArgumentException ePos){
			    ErrorBox.showError(ePos.getMessage());
			}
		    }
		});		
			
            //Modifier un element
	    this.menuBar.getMenuItem("Scene", "Modifier l'element...").addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
			Positionable pos = content.getSelectedPositionable();
			try{					
			    if(pos == null)
				throw new IllegalArgumentException("Aucun objet sélectionné");
							
			    if(pos instanceof Sphere){
				DialogBoxSphere dialogBoxSphere = new DialogBoxSphere(scene, content, (Sphere)pos);
			    }else if(pos instanceof Plan){
				DialogBoxPlan dialogBoxPlan = new DialogBoxPlan(scene, content, (Plan)pos);
			    }else if(pos instanceof Cube){
				DialogBoxCube dialogBoxCube = new DialogBoxCube(scene, content, (Cube)pos);
			    }else if(pos instanceof ViewPoint){
				DialogBoxViewPoint dialogBoxViewPoint = new DialogBoxViewPoint(scene, content, (ViewPoint)pos);
			    }	
			    else if(pos instanceof SpotLight){
				DialogBoxSpotLight dialogBoxSpotLight = new DialogBoxSpotLight(scene, content, (SpotLight)pos);
			    }											
			}catch(IllegalArgumentException ePos){
			    ErrorBox.showError(ePos.getMessage());
			}					
		    }
		});		
			
	    //Exporter la vue
	    this.menuBar.getMenuItem("Scene", "Exporter vue...").addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
			Positionable pos = content.getSelectedPositionable();
			try{
			    if(pos == null || !(pos instanceof ViewPoint))	
				throw new IllegalArgumentException("Selectionnez un point de vue");
							
			    scene.exportPPM((ViewPoint)pos);
			    JOptionPane.showMessageDialog(null, "Vue "+pos+" exportée à la racine\n", "Information" , JOptionPane.INFORMATION_MESSAGE);
			}catch(IllegalArgumentException eIA){
			    ErrorBox.showError(eIA.getMessage());
			}catch(RuntimeException eRt){
			    ErrorBox.showError(eRt.getMessage());
			}	
		    }
		});	

            //---------------- Menu projet
            //Quitter
            this.menuBar.getMenuItem("Projet", "Quitter").addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
			int answer = JOptionPane.showConfirmDialog(null, "Etes-vous sur de vouloir quitter ?", "Quitter le programme...",
								   JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if(answer == JOptionPane.YES_OPTION)
			    System.exit(0);
		    }
		});   

            //Nouveau Projet
            this.menuBar.getMenuItem("Projet", "Nouveau").addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
			DialogBoxScene dialogBoxScene = new DialogBoxScene(scene, content, menuBar);
		    }
		});  
			
            //---------------- Menu Aide
            this.menuBar.getMenuItem("?", "Aide").addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
			DialogBoxHelp dialogBoxHelp = new DialogBoxHelp();
		    }
		});  
			
	}catch(IllegalArgumentException e){
            ErrorBox.showError(e.getMessage());
	    System.exit(1);
	}
    }
	
    /** @see SceneObserver */
    public void updateImage(Image image, ViewPoint vp){
        try{
	    content.updateRightPane(new ImageIcon(image), vp);
        }catch(IllegalArgumentException e){
            ErrorBox.showError(e.getMessage());
            System.exit(1);
        }
    }
}
