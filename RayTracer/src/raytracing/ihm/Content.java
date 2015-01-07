/** Afficher une representation de la scene sous forme d'images et d'une liste de positionnables.
 * @author Matthias Benkort
 * @version 2.0
 */
package raytracing.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Hashtable;
import java.util.Map.Entry;
import raytracing.scene.positionable.Positionable;
import raytracing.scene.positionable.ViewPoint;
import raytracing.scene.Color;

public class Content extends JSplitPane{
    /** Liste des onglets associÃ© Ã  des points de vue*/
    private Hashtable<ViewPoint, JLabel> tabs;

    /** ModÃ¨le de la liste */
    private DefaultListModel listModel; 

    /** Liste de positionnables */
    private JList list;

    /** Initialiser le corps d'affichage 
     */
    public Content(){
	//Initialisation des attributs
	this.tabs = new Hashtable<ViewPoint, JLabel>();
	this.listModel = new DefaultListModel();
	this.list = new JList(this.listModel);
	this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
	//Partie gauche du SplitPane
	JPanel listPane = new JPanel(new BorderLayout());
        listPane.setBorder(BorderFactory.createTitledBorder("Elements de la scene"));
	listPane.add(this.list, BorderLayout.CENTER);
	this.setLeftComponent(new JScrollPane(listPane));
		
	//Partie droite du SplitPane
	JTabbedPane rightPane = new JTabbedPane();	
        rightPane.setBorder(BorderFactory.createTitledBorder("Vues de la scene"));
	this.setRightComponent(rightPane);

	//Reglages du SplitSpane
	this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
	this.setOneTouchExpandable(false);
	this.setDividerLocation(200);
	this.getLeftComponent().setMinimumSize(new Dimension(200,200));
	this.getRightComponent().setMinimumSize(new Dimension(400,200));
	this.setPreferredSize(new Dimension(800,600));
    }

    /** Ajouter un positionable a la liste des positionnables 
     * @param pos Le positionnable à¡¡jouter
     * @throws IllegalArgumentException Lorsque le positionnable est null
     */
    public void addElementToList(Positionable pos){
	if(pos == null)
	    throw new IllegalArgumentException("Impossible d'ajouter l'element a la liste");
		
	this.listModel.insertElementAt(pos,0);
    }
	
    /** Retirer un positionnable de la liste des positionnables
     * @param pos Le positionnable à¡²etirer
     * @throws IllegalArgumentException Lorsque le positionnable est null
     *   IllegalArgumentException Lorsque le positionnable n'est pas dans la liste
     */
    public void removeElementFromList(Positionable pos){
	if(pos == null)
	    throw new IllegalArgumentException("Impossible de supprimer l'element de la liste");
	
	this.listModel.removeElement(pos);
    }
	
    /** Retirer tous les elements de la liste
     */
    public void removeAllElementFromList(){
	this.listModel.removeAllElements();
    }
	
    /** Obtenir le positionnable selectionne par l'utilisateur
     * @return Le positionnable selectionne
     */
    public Positionable getSelectedPositionable(){
	return (Positionable)this.list.getSelectedValue();
    }
	
    /** Verifier si la liste de positionnables est vide
     * @return True si elle est vide, false sinon
     */
    public boolean ListIsEmpty(){
	return this.listModel.isEmpty();
    }
	
    /** Mettre a la jour l'un des onglets 
     * @param image Image de mise a jour
     * @param vp Onglet a mettre a jour
     * @throws IllegalArgumentException si image ou vp est null, ou si vp n'est pas parmi les onglets disponibles
     */
    public void updateRightPane(ImageIcon image, ViewPoint vp){
        if(vp == null)
            throw new IllegalArgumentException("Onglet invalide");

        if(image == null)
            throw new IllegalArgumentException("Image invalide");

        if(!this.tabs.containsKey(vp))
            throw new IllegalArgumentException("Onglet pas dans la liste");

	JLabel targetedLabel = this.tabs.get(vp);
	targetedLabel.setIcon(image);
	targetedLabel.paint(targetedLabel.getGraphics());
	this.validate();
    }
	
    /** Ajouter un nouvel onglet
     * @param vp Onglet a ajouter
     * @throws IllegalArgumentException lorsque l'onglet est null
     */
    public void addTab(ViewPoint vp){
	if(vp == null)
	    throw new IllegalArgumentException("Impossible d'ajouter l'onglet");
		
	JLabel view = new JLabel();
	JPanel viewContainer = new JPanel(new FlowLayout());
	viewContainer.add(view);
	this.tabs.put(vp, view);
        JTabbedPane rightPane = (JTabbedPane)this.getRightComponent();
	rightPane.add(vp.toString(), viewContainer);
    }
	
    /** Supprimer un onglet
     * @param vp Onglet a supprimer
     * @throws IllegalArgumentException lorsque l'onglet est null ou n'existe pas
     */
    public void removeTab(ViewPoint vp){
	if(vp == null)
	    throw new IllegalArgumentException("Suppresion d'un onglet invalide");
			
	this.tabs.remove(vp);
		
	JTabbedPane rightPane = (JTabbedPane)this.getRightComponent();
	rightPane.removeAll();
	for(Entry<ViewPoint, JLabel> entry : this.tabs.entrySet()) {
	    ViewPoint key = entry.getKey();
	    JLabel view = entry.getValue();
	    JPanel viewContainer = new JPanel(new FlowLayout());
	    viewContainer.add(view);			
	    rightPane.add(key.toString(), viewContainer);
	}		
    }
	
    /** Savoir s'il n'y a plus aucune tab
     * @return False s'il reste des tabs, true sinon
     */
    public boolean noMoreTab(){
	JTabbedPane rightPane = (JTabbedPane)this.getRightComponent();
	return (rightPane.getSelectedIndex() == -1);
    }
	
    /** Supprimer tous les onglets
     */
    public void removeAllTab(){
	JTabbedPane rightPane = (JTabbedPane)this.getRightComponent();
	rightPane.removeAll();
    }
}
