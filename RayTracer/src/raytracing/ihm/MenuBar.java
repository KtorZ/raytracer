/** Permet de construire la barre de menu de l'application
 * @author Matthias Benkort
 * @version 2.0
 */
package raytracing.ihm;

import java.awt.event.*;
import javax.swing.*;
import java.util.Hashtable;
import java.util.Map.Entry;

public class MenuBar extends JMenuBar{
    private Hashtable<String , Hashtable<String, JMenuItem>> menus = new Hashtable<String , Hashtable<String, JMenuItem>>();
	
    /** Initialiser la barre de menu */
    public MenuBar(){
	//Creation de la liste des differents menus
	JMenu menu, subMenu;
	Hashtable<String, JMenuItem> itemTable;	
		
	//Menu Projet
	menu = new JMenu("Projet");
	menu.getAccessibleContext().setAccessibleDescription(
							     "Actions relatives au projet ( enregistrement ou chargement de scene, quitter etc...)");
	itemTable = addMenusItemTo(
				   menu, 
				   new String[]{"Nouveau", "-", "Quitter"},
				   new int[]{KeyEvent.VK_N, KeyEvent.VK_Q},
				   new int[]{ActionEvent.CTRL_MASK, ActionEvent.CTRL_MASK}
				   );	
	this.menus.put("Projet",itemTable);
	this.add(menu);		
		
	//Menu Scene
	menu = new JMenu("Scene");
	menu.getAccessibleContext().setAccessibleDescription(
							     "Actions relatives a la gestion de la scene.");
			
	//SousMenu Ajouter Objet
	subMenu = new JMenu("Ajouter Objet");
	itemTable = addMenusItemTo(
				   subMenu,
				   new String[]{"Ajouter sphere", "Ajouter cube", "Ajouter plan infini"}
				   );
	this.menus.put("Scene",itemTable);
	menu.add(subMenu);
		
	itemTable = addMenusItemTo(
				   menu, 
				   new String[]{"Ajouter lumiere", "Ajouter point de vue","-", "Modifier l'element...", "Supprimer l'element...","-","Exporter vue..."}
				   );
	merge(this.menus.get("Scene"),itemTable);
	this.add(menu);	
		
	//Menu aide
	menu = new JMenu("?");
	menu.getAccessibleContext().setAccessibleDescription(
							     "Aide concernant le fonctionnement de l'application");
		
	itemTable = addMenusItemTo(
				   menu,
				   new String[]{"Aide"}
				   );
	this.menus.put("?",itemTable);
	this.add(menu);
    }
	
    /** Obtenir un element d'un menu
     * @param String menu Menu vise
     * @param String item Element du menu vise
     * @return JMenuItem Element demande
     */
    public JMenuItem getMenuItem(String menu, String item) throws IllegalArgumentException{
	if(!this.menus.containsKey(menu))
	    throw new IllegalArgumentException("La cle "+menu+" ne correspond a aucun menu");
		
	if(!this.menus.get(menu).containsKey(item))
	    throw new IllegalArgumentException("La cle "+item+" ne correspond a aucun element du menu \""+menu+"\".");
			
	return this.menus.get(menu).get(item); 
    }
	
    /** Construire de maniere automatiser les elements d'un menu. 
     * @param JMenuItem menu Menu auquel ajouter les elements
     * @param String itemNames[] Noms des elements a ajouter. La chaine "-" produit l'ajout d'un separateur au menu,
     * et est ignoree pour le reste des operations.
     * @param itemKeys int[] KeyEvent correspondant aux touches de raccourcis des elements.
     * @param itemMasks int[] ActionEvent correspondant aux masques associes aux touches de raccourcis.
     * @return Hashtable<String, JMenuItem> Tableau associatif des JMenuItem crees associe a leur nom.
     */
    private static Hashtable<String, JMenuItem> addMenusItemTo(JMenuItem menu, String itemNames[], int itemKeys[], int itemMasks[]){		
	Hashtable<String, JMenuItem> itemTable = new Hashtable<String, JMenuItem>();
	JMenuItem menuItem;	
	int sep = 0;
	try{
	    for(int i=0;i < itemNames.length;i++){
		if(itemNames[i].equals("-") && menu instanceof JMenu){
		    sep++;
		    JMenu menuTemp = (JMenu) menu;
		    menuTemp.addSeparator();
		}else{
		    menuItem = new JMenuItem(itemNames[i],itemKeys[i-sep]);
		    if(itemKeys[i-sep] != 0){
			menuItem.setAccelerator(KeyStroke.getKeyStroke(itemKeys[i-sep], itemMasks[i-sep]));
		    }
		    itemTable.put(itemNames[i],menuItem);
		    menu.add(menuItem);	
		}
	    }
	}catch(java.lang.ArrayIndexOutOfBoundsException e){
	    System.out.println("Attention : Le menu \""+menu.getAccessibleContext().getAccessibleName()+"\" ne s'est pas construit entierement."); 
	}
	return itemTable;				
    }
	
    /** Construire de maniere automatiser les elements d'un menu. 
     * @param JMenuItem menu Menu auquel ajouter les elements
     * @param String itemNames[] Noms des elements a ajouter
     * @return Hashtable<String, JMenuItem> Tableau associatif des JMenuItem crees associe a leur nom.
     */	
    private static Hashtable<String, JMenuItem> addMenusItemTo(JMenuItem menu, String itemNames[]){		
	return addMenusItemTo(menu, itemNames, new int[itemNames.length], new int[itemNames.length]);
    }
	
    /** Ajouter le contenu d'une Hashtable a une autre 
     * @param Hashtable<V,F> Tableau recepteur
     * @param Hashtable<V,F> Tableau a ajouter
     */
    private static <V,F> void merge(Hashtable<V,F> hTableSrc, Hashtable<V,F> hTableToAdd){
	for(Entry<V,F> entry : hTableToAdd.entrySet()){
	    hTableSrc.put(entry.getKey(),entry.getValue());
	}
    }
	
}
