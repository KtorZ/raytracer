/** Fenetre d'aide de l'application
 * @author Matthias Benkort
 * @version 1.0
 */
package raytracing.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DialogBoxHelp extends JDialog{
    JTextArea textArea;
    JComboBox helpList;
	
    public DialogBoxHelp(){
	this.init();
	this.initControllers();
	this.setVisible(true);
    }
	
    private void init(){
	//Réglages de la fenêtre.
	this.setResizable(false);
	this.setModal(true);
	this.setTitle("Aide");		
	JPanel contentPanel = new JPanel(new BorderLayout());
	contentPanel.setBorder(BorderFactory.createTitledBorder("Aide"));
	this.add(contentPanel);
		
	//Initialisation du pannel d'information
	this.textArea = new JTextArea(10,35);
	JScrollPane infoPanel = new JScrollPane(this.textArea);
	infoPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	contentPanel.add(infoPanel);
		
	//Initialisation de la comboBox
	this.helpList = new JComboBox();
		
	StringHelp listItem;

	listItem = new StringHelp("Sélectionnez une rubrique...", "");
	this.helpList.addItem(listItem);
			
	listItem = new StringHelp("Création d'une scène",
				  "Pour créer une scène, cliquez sur le menu Projet > Nouveau\n\n"+
				
				  "Une fois le menu sélectionné, il vous est demandé de remplir certains champs concernant votre scène :  nom, indice de \n"+
				  "réfraction, nombre de déviations, couleur ambiante et couleur de l'infini\n\n"+
				
				  "Le nom de la scène permet de l'identifier en cas d'enregistrement (fonctionnalité non implémentée).\n\n"+
				
				  "L'indice de réfraction caractérise l'indice de votre milieu. Cet indice entre en jeu dans le calcul de la composante \n"+
				  "réfractée\n\n"+
				
				  "Le nombre de déviation pemet de définir une profondeur de calcul pour les composantes réfléchie et réfractée. Pour un\n"+
				  "rendu réaliste, un nombre de 6 à 8 déviations est conseillé. Attention cependant à ne pas prendre de valeur trop élevées\n"+
				  "pour limiter le temps de calcul\n\n"+
				
				  "La couleur ambiante caractérise l'éclairement ambiant de la scène. Pour une lumière vive, sélectionner des couleurs claires.\n"+
				  "Cette couleur à une forte influence sur la visualisation de la composante diffuse (une forte lumière ambiante ne permet\n"+
				  "pas de visualiser convenablement la composante diffuse\n\n"+
				
				  "La couleur de l'infini définit la couleur de fond de votre scène. C'est cette couleur qui apparaitra si aucun n'objet n'est\n"+
				  "intersecté lors du lancer de rayons."
				  );
	this.helpList.addItem(listItem);		
			
	listItem = new StringHelp("Création d'une sphère", 
				  "Pour créer une sphère, cliquez sur le menu Scene > Ajouter Objet > Ajouter sphere.\n\n"+
					
				  "Attention, vous devez préalablement avoir créé une scène pour accéder à ce menu. Une fois le menu sélectionné,\n"+
				  "il vous est demandé de remplir certains champs concernant la sphère : nom, centre, rayon, couleur, matériau.\n\n"+
					
				  "Le nom de la sphère permet de l'identifier dans la liste des positionnables. Ce dernier ne peut être vide.\n\n"+
				  "La couleur permet de définir les coefficients d'absorptions diffuse et ambiant.\n\n"+
					
				  "Quant au matériau, il permet de définir divers coefficients. Les coefficients de transmission et de reflexion\n"+
				  "sont relatifs aux composantes rouge, verte et bleue."
				  );
	this.helpList.addItem(listItem);	
			
	listItem = new StringHelp("Création d'un cube", 
				  "Pour créer un cube, cliquez sur le menu Scene > Ajouter Objet > Ajouter cube.\n\n"+ 
					
				  "Attention, vous devez préalablement avoir créé une scène pour accéder à ce menu. Une fois le menu sélectionné,\n"+
				  "il vous est demandé de remplir certains champs concernant le cube : nom, p1, p2, p3, couleur, matériau.\n\n"+
					
				  "Le nom du cube permet de l'identifier dans la liste des positionnables. Ce dernier ne peut être vide.\n\n"+
					
				  "La position du cube dans l'espace est caractérisée par 3 points d'une même face : p1, p2 et p3. Une fois cette\n"+
				  "face positionnée, le cube est extrudé dans le sens directe, donné par les vecteurs p1p2 et p1p3. Si les 3 points\n"+
				  "ne permettent pas de définir un triangle rectangle isocèle, vous obtiendrez une erreur.\n\n"+
					
				  "La couleur permet de définir les coefficients d'absorptions diffuse et ambiant.\n\n"+
				  "Quant au matériau, il permet de définir divers coefficients. Les coefficients de transmission et de reflexion\n"+
				  "sont relatifs aux composantes rouge, verte et bleue."
				  );
	this.helpList.addItem(listItem);
			
	listItem = new StringHelp("Création d'un plan", 
				  "Pour créer un plan infini, cliquez sur le menu Scene > Ajouter Objet > Ajouter plan infini.\n\n"+ 
					
				  "Attention, vous devez préalablement avoir créé une scène pour accéder à ce menu. Une fois le menu sélectionné,\n"+
				  "il vous est demandé de remplir certains champs concernant le plan : nom, p1, p2, p3, couleur, matériau.\n\n"+
					
				  "Le nom du plan permet de l'identifier dans la liste des positionnables. Ce dernier ne peut être vide.\n\n"+
					
				  "La position du plan dans l'espace est caractérisée par 3 points : p1, p2 et p3. Les vecteurs p1p2 et p1p3\n"+
				  "permettent de définir le sens de la normale. Attention à bien orienter la normale lorsque le plan est transparent : \n"+
				  "la normale est par convention exterieur à l'objet et le programme lévera une erreur si votre point de vue est placé\n"+
				  "à l'interieur du demi espace défini par le plan\n\n"+
					
				  "La couleur permet de définir les coefficients d'absorptions diffuse et ambiant.\n\n"+
				  "Quant au matériau, il permet de définir divers coefficients. Les coefficients de transmission et de reflexion\n"+
				  "sont relatifs aux composantes rouge, verte et bleue."
				  );
	this.helpList.addItem(listItem);			
			
	listItem = new StringHelp("Création d'une source", 
				  "Pour créer une source de lumière, cliquez sur le menu Scene > Ajouter source de lumière.\n\n"+ 
					
				  "Attention, vous devez préalablement avoir créé une scène pour accéder à ce menu. Une fois le menu sélectionné,\n"+
				  "il vous est demandé de remplir certains champs concernant la source de lumière : nom, position, couleur.\n\n"+
					
				  "Le nom de la source permet de l'identifier dans la liste des positionnables. Ce dernier ne peut être vide.\n\n"+			
					
				  "La couleur permet de la valeur de l'éclairement. Pour une lumière vive, sélectionner des couleurs claires.\n"
				  );
	this.helpList.addItem(listItem);			

	listItem = new StringHelp("Création d'un point de vue", 
				  "Pour créer un point de vue, cliquez sur le menu Scene > Ajouter point de vue.\n\n"+ 
					
				  "Attention, vous devez préalablement avoir créé une scène pour accéder à ce menu. Une fois le menu sélectionné,\n"+
				  "il vous est demandé de remplir certains champs concernant la source de lumière : nom, position du point de vue,\n"+
				  "direction du regard, sens de la vertical, fov, et résolution de l'écran.\n\n"+
					
				  "Le nom du point de vue permet de l'identifier dans la liste des positionnables. Ce dernier ne peut être vide.\n\n"+			
					
				  "La position du point de vue définit le point depuis lequel s'effectue le lancer de rayons. Le programme ne gère pas\n"+
				  "les points de vue internes aux objets. Attention aux normales des objets lorsque vous placez vos points de vue.\n\n"+
					
				  "La direction du regard permet de la direction selon laquelle les rayons vont être lancés. L'écran sera orthogonal à\n"+
				  "cette direction.\n\n"+
					
				  "Le sens de la verticale définit le sens dans lequel votre image va être vu. Ce vecteur ne doit pas être colinéaire au\n"+
				  "précédent\n\n"+
					
				  "Le fov (field of view) ou champ de vision est un angle en degré (entre 1 et 179). Il permet de définir la distance entre\n"+
				  "le point de vue et l'écran.\n\n"+
					
				  "Enfin, la résolution en pixel renseigne sur le nombre de points par unité de longueur. Il définit également la résolution\n"+
				  "de l'image en cas d'exportation"
				  );
	this.helpList.addItem(listItem);
			
	listItem = new StringHelp("Modifier un positionnable", 
				  "Pour modifier une positionnable, il vous suffit de le sélectionner dans la liste, puis de cliquez sur le menu Scene > Modifier l'élément"
				  );
	this.helpList.addItem(listItem);
			
	listItem = new StringHelp("Supprimer un positionnable", 
				  "Pour supprimer une positionnable, il vous suffit de le sélectionner dans la liste, puis de cliquez sur le menu Scene > Supprimer l'élément"
				  );
	this.helpList.addItem(listItem);	
			
	listItem = new StringHelp("Exporter une vue", 
				  "Pour exporter une vue, sélectionnez un point de vue dans liste, puis cliquez sur le menu Scene > Exporter vue...\n"+
				  "La vue est alors exportée à la racine de l'application, et porte le nom du point de vue associé."
				  );
	this.helpList.addItem(listItem);		

	listItem = new StringHelp("Point de vue à l'intérieur d'un objet", 
				  "L'erreur point de vue à l'intérieur d'un objet est lancée lorsque votre point de vue se situe à l'intérieur de l'espace délimité\n"+
				  "par un objet transparent. Vérifier donc l'ordre de vos points pour les plans, de sorte que votre point de vue se situe dans la\n"+
				  "partie de l'espace extérieure du plan (non pointée par la normale)."
				  );
	this.helpList.addItem(listItem);				
						
	contentPanel.add(this.helpList, BorderLayout.NORTH);
		
	this.setPreferredSize(new Dimension(700,300));
	this.pack();
	this.setLocationRelativeTo(null);		
    }
	
    private void initControllers(){
	this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	this.helpList.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    JComboBox source = (JComboBox)e.getSource();
		    StringHelp selected = (StringHelp)source.getSelectedItem();
		    textArea.setText(selected.getContent());
		    validate();
		    pack();
		}
	    });
    }
	
    private class StringHelp{
	private String name;
	private String content;
	
	public StringHelp(String name, String content){
	    this.name = name;
	    this.content = content;
	}
		
	public String getContent(){
	    return this.content;
	}
		
	public String toString(){
	    return this.name;
	}
    }

}
