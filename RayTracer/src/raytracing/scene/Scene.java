/** 
 * Representer une scene 3D.
 * @author Matthias Benkort
 * @version 1.3
 */
package raytracing.scene;

import raytracing.scene.*;
import raytracing.scene.geometry.*;
import raytracing.scene.positionable.*;
import raytracing.scene.positionable.element.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;
import java.awt.image.BufferedImage;
import raytracing.ihm.SceneObserver;
import java.awt.*;
import javax.swing.*;

public class Scene implements ObservableScene{
    /** Liste des observateurs */
    private ArrayList<SceneObserver> obsList = new ArrayList<SceneObserver>();

    /** Liste des differents point de vue. */
    private ArrayList<ViewPoint> viewPoints = new ArrayList<ViewPoint>();

    /** Liste des differentes sources de lumiere. */
    private ArrayList<SpotLight> spotLights = new ArrayList<SpotLight>();

    /** Liste des differents elements. */
    private ArrayList<Element> elements = new ArrayList<Element>();

    /** Stockage des images PPM générées */
    private Hashtable<ViewPoint, ImagePPM> imgList = new Hashtable<ViewPoint, ImagePPM>();

    /** Lumiere ambiante. */
    private Color ambientLight;

    /** Indice de refraction du milieu ambiant */
    private double iRefraction;

    /** Couleur de l'infini*/
    private Color infinityColor; 

    /** Nombre maximum de reflexion/refraction successives. */
    private int nbIter;

    /** Nom de la scene */
    private String name;

    /** Initialiser une scene vide a partir de l'eclairement ambiant.
     * @param ambientLight Eclairement ambiant.
     * @throws IllegalArgumentException 
     */
    public Scene(Color ambientLight, Color infinityColor, double iRefraction, int nbIter, String name){
	this.setAmbientLight(ambientLight);
	this.setInfinityColor(infinityColor);
	this.setIRefraction(iRefraction);
	this.setNbIter(nbIter);
	this.setName(name);
    }

    /** Ajouter un point de vue a la scene.
     * @param viewPoint Point de vue a ajouter.
     * @throws RuntimeException Lorsque le point de vue est null
     */
    public void addViewPoint(ViewPoint viewPoint){
	if(viewPoint == null)
	    throw new RuntimeException("Point de vue invalide");
			
	if(this.viewPoints.contains(viewPoint))
	    throw new RuntimeException("Point de vue deja present");
				
	this.viewPoints.add(viewPoint);
			
	this.notifyObservers();
    }

    /** Ajouter une source de lumiere a la scene.
     * @param spotLight Source de lumiere a ajouter.
     */
    public void addSpotLight(SpotLight spotLight){
	if(spotLight == null)
	    throw new IllegalArgumentException("Source de lumiere invalide");
			
	if(this.spotLights.contains(spotLight))
	    throw new IllegalArgumentException("Source de lumiere deja presente");
				
	this.spotLights.add(spotLight);
			
	this.notifyObservers();		
    }

    /** Ajouter un element a la scene.
     * @param element Element a ajouter.
     * @throws RuntimeException Lorsque l'element est null
     */
    public void addElement(Element element){
	if(element == null)
	    throw new RuntimeException("Element invalide");
			
	if(this.elements.contains(element))
	    throw new RuntimeException("Element deja present");
				
	this.elements.add(element);		
			
	this.notifyObservers();
    }

    /** Ajouter une image à la liste des images 
     * @param vp Le point de vue associé a l'image.
     * @param img Image a ajouter
     * @throws RuntimeException
     */
    private void addImage(ViewPoint vp, ImagePPM img){
        if(vp == null || img == null)
            throw new RuntimeException("Impossible d'ajouter l'image");
     
        this.imgList.put(vp, img);
    }

    /** Ajouter une image à la liste des images 
     * @param vp Le point de vue associé a l'image.
     * @param img Image a ajouter
     * @throws RuntimeException
     */
    private void clearImgList(){
        if(!this.imgList.isEmpty())
            this.imgList.clear();
    }

    /** Enregistrer une image au format PPM 
     * @param vp Point de vue associe a l'image
     * @throws RuntimeException Lorsque vp est null ou ne correspond a aucun point connu
     */
    public void exportPPM(ViewPoint vp){
        if(vp == null || !this.imgList.containsKey(vp))
            throw new RuntimeException("Exportation impossible : point de vue invalide");
       
        this.imgList.get(vp).exportPPM(vp);
    }

    /** Retirer un point de vue de la scene.
     * @param viewPoint Point de vue a retirer.
     * @throws RuntimeException lorsque le param n'est pas dans la liste.
     */
    public void removeViewPoint(ViewPoint viewPoint){
	if(!this.viewPoints.contains(viewPoint))
	    throw new RuntimeException("Point de vue non present.");
				
	this.viewPoints.remove(viewPoint);
	this.notifyObservers();		
    }

    /** Retirer une source de lumiere de la scene.
     * @param spotLight Lumiere a retirer.
     * @throws RuntimeException lorsque le param n'est pas dans la liste.
     */
    public void removeSpotLight(SpotLight spotLight){
	if(!this.spotLights.contains(spotLight))
	    throw new RuntimeException("Source de lumiere non presente.");
				
	this.spotLights.remove(spotLight);   
	this.notifyObservers();			
    }

    /** Retirer un element de la scene.
     * @param element Element a retirer.
     * @throws RuntimeException lorsque le param n'est pas dans la liste.	
     */
    public void removeElement(Element element){
	if(!this.elements.contains(element))
	    throw new RuntimeException("Element non present.");
				
	this.elements.remove(element);
	this.notifyObservers();			
    }
	
    /**Supprimer tous les positionables d'une scene
     */
    public void removeAll(){
	this.elements.clear();
	this.spotLights.clear();
	this.viewPoints.clear();
    }

    /** Mettre a jour un point de vue de la scene.
     * @param viewPoint Point de vue a mettre a jour.
     * @param newViewPoint Le nouveau point de vue
     */
    public void updateViewPoint(ViewPoint viewPoint, ViewPoint newViewPoint){
	if(!this.viewPoints.contains(viewPoint))
	    throw new RuntimeException("Point de vue non present.");
				
	this.viewPoints.set(this.viewPoints.indexOf(viewPoint), newViewPoint);		
	this.notifyObservers();			
    }

    /** Mettre a jour une source de lumiere de la scene.
     * @param spotLight Source de lumiere a mettre a jour.
     * @param newSpotLight La nouvelle source de lumière
     */
    public void updateSpotLight(SpotLight spotLight, SpotLight newSpotLight){
	if(!this.spotLights.contains(spotLight))
	    throw new RuntimeException("Source de lumiere non presente.");
				
	this.spotLights.set(this.spotLights.indexOf(spotLight), newSpotLight);	
	this.notifyObservers();			
    }

    /** Mettre a jour un element de la scene.
     * @param element Element de la scene a mettre a jour.
     */
    public void updateElement(Element element, Element newElement){
	if(!this.elements.contains(element))
	    throw new RuntimeException("Element non present.");
				
	this.elements.set(this.elements.indexOf(element), newElement);	
	this.notifyObservers();			
    }

    /** Obtenir l'eclairement ambiant.
     * @return L'eclairement ambiant de la scene.
     */
    public Color getAmbientLight(){		
	return this.ambientLight;
    }

    /** Obtenir la couleur de l'infini
     * @return La couleur de l'infini
     */
    public Color getInfinityColor(){
	return this.infinityColor;
    }

    /** Obtenir l'indice de refraction du milieu
     * @return L'indice de refraction du milieu
     */
    public double getIRefraction(){
	return this.iRefraction;
    }

    /** Obtenir le nombre maximum d'itération
     * @return Le nombre maximum de reflexions/refractions.
     */
    public double getNbIter(){
	return this.nbIter;
    }

    /** Definir l'eclairement ambiant.
     * @param ambientLight Eclairement ambiant.
     * @throws IllegalArgumentException Lorsque la couleur est null
     */
    public void setAmbientLight(Color ambientLight){
	if(ambientLight == null)
	    throw new IllegalArgumentException("Couleur invalide");
		
	this.ambientLight = ambientLight;
    }

    /** Definir la couleur de l'infini
     * @param infinityColor La couleur de l'infinie
     * @throws IllegalArgumentException Lorsque la couleur est null
     */
    public void setInfinityColor(Color infinityColor){
	if(infinityColor == null)
	    throw new IllegalArgumentException("Couleur invalide");

	this.infinityColor = infinityColor;
    }

    /** Definir l'indice de refraction du milieu ambiant
     * @param iRefraction L'indice de refraction du milieu ambiant.
     * @throws IllegalArgumentException lorsque l'indice est stric inferieur a 1.
     */
    public void setIRefraction(double iRefraction){
	if(iRefraction < 1)
	    throw new IllegalArgumentException("Indice de refraction du milieu invalide");
    
	this.iRefraction = iRefraction;
    }

    /** Definir le nombre maximum de reflexions/refractions.
     * @param nbIter Le nombre maximum de reflexions/refractions.
     * @throws IllegalArgumentException lorsque l'indice est négatif
     */
    public void setNbIter(int nbIter){
	if(nbIter < 0)
	    throw new IllegalArgumentException("Nombre d'itérations négatif");
    
	this.nbIter = nbIter;
    }

    /** Definir le nom de la scene
     * @param name Le nom de la scene
     * @throws IllegalArgumentException Lorsque le nom est incorrect
     */
    public void setName(String name){
        if(name == null || name.trim().equals(""))
            throw new IllegalArgumentException("Nom de la scene incorrect");

        this.name = name;
    }

    /** Obtenir le nom de la scene
     * @return Le nom de la scene
     */
    public String getName(){
        return this.name;
    }

    /** Obtenir l'imagePPM correspondant à un point de vue.
     * @param vp Point de vue concerne
     * @throws IllegalArgumentException Lorsque le point de vue est null
     */
    private void buildImage(ViewPoint vp){
	if(vp == null)
	    throw new RuntimeException("Erreur interne dans la creation de l'image.");

	Screen sc = vp.getScreen();
	int resolI = sc.getResolutionI();
	int resolJ = sc.getResolutionJ();
	ImagePPM img = new ImagePPM(new int[]{resolI,resolJ}, 255);
	Ray ray;
			
	System.out.println("Calcul de l'image associee a :"+vp);	
	System.out.print("\n0%--------------------100%\n  ");	

	long execTime = System.currentTimeMillis();
	for(int k = 0; k<resolI; k++){
	    for(int l = 0; l<resolJ; l++){
		try{
		    ray = new Ray(vp.getSource(), new Vector(vp.getSource(), sc.getPointAt(k,l)));
		    img.addColor(k,l,RayTracer.getAmbientComp(this.elements, this.ambientLight, ray),false);
		    img.addColor(k,l,RayTracer.getDiffuseComp(this.elements, this.spotLights, ray),true);
		    img.addColor(k,l,RayTracer.getSpecularComp(this.elements, this.spotLights, ray),true);
		    Stack<Double> iRefs = new Stack<Double>();
		    iRefs.push(this.iRefraction);
		    img.addColor(k,l,
				 RayTracer.getRandRComp(
							this.elements, this.spotLights, 
							this.ambientLight, 
							ray, 
							iRefs, //Pile d'indices de refraction des transmissions
							this.infinityColor, // Couleur de l'infini
							this.nbIter) //Nombre max de deviation calculees
				 ,true);
		}catch(NoIntersectionException e){
		    img.addColor(k,l,this.infinityColor,false);						
		}
	    }
	    if((100*(double)(k+1)/(double)resolI) % 5 == 0)
		System.out.print(".");
	}		
	System.out.println("Image calculee ("+(System.currentTimeMillis()-execTime)+"ms)");
	img.exportPPM(vp);
    }
	
    /** Obtenir l'imagePPM correspondant à un point de vue, en
     * calculant de manière optimisée toutes les composantes.
     * @param vp Point de vue concerne
     * @throws IllegalArgumentException Lorsque le point de vue est null
     */
    private void buildImageOptimised(ViewPoint vp){
	if(vp == null)
	    throw new RuntimeException("Erreur interne dans la creation de l'image.");

	Screen sc = vp.getScreen();
	int resolI = sc.getResolutionI();
	int resolJ = sc.getResolutionJ();
	ImagePPM img = new ImagePPM(new int[]{resolI,resolJ}, 255);
	Ray ray;
			
	System.out.println("Calcul de l'image associee a :"+vp);	
	System.out.print("\n0%--------------------100%\n  ");	

	long execTime = System.currentTimeMillis();
	for(int k = 0; k<resolI; k++){
	    for(int l = 0; l<resolJ; l++){
		try{ray = new Ray(vp.getSource(), new Vector(vp.getSource(), sc.getPointAt(k,l)));
		    Stack<Double> iRefs = new Stack<Double>();
		    iRefs.push(this.iRefraction);
		    img.addColor(k,l,
				 RayTracer.getAllComp(this.elements,
						      this.spotLights, 
						      this.ambientLight, 
						      ray, 
						      iRefs, //Pile d'indices de refraction des transmissions
						      this.infinityColor, // Couleur de l'infini
						      this.nbIter), //Nombre max de deviation calculees
				 false);
		}catch(NoIntersectionException e){
		    img.addColor(k,l,this.infinityColor,false);								}
	    }
	    //Notification 
		
	    if((100*(double)(k+1)/(double)resolI) % 5 == 0)
		System.out.print(".");
	}		
	System.out.println("Image calculee ("+(System.currentTimeMillis()-execTime)+"ms)");
	img.exportPPM(vp);
    }


    /** @see ObservableScene */
    public void addObserver(SceneObserver obs){
	if(obs == null)
	    throw new IllegalArgumentException("Observateur invalide");
		
	this.obsList.add(obs);
    }

    /** @see ObservableScene */
    public void removeObserver(SceneObserver obs){
	if(obs == null || !this.obsList.contains(obs))
	    throw new IllegalArgumentException("Observateur invalide");
		
	this.obsList.remove(obs); 
    }

    /** @see ObservableScene */
    public void notifyObservers(){
	this.clearImgList();
	for(ViewPoint vp : this.viewPoints){
	    Screen sc = vp.getScreen();
	    int resolI = sc.getResolutionI();
	    int resolJ = sc.getResolutionJ();
	    ImagePPM img = new ImagePPM(new int[]{resolI,resolJ}, 255);
	    BufferedImage imgBuff = new BufferedImage(resolJ, resolI, BufferedImage.TYPE_INT_RGB);
	    Color computedColor;
	    Ray ray;
					
	    System.out.print("\n0%--------------------100%\n  ");	
	    long execTime = System.currentTimeMillis();
	    for(int k = 0; k<resolI; k++){
		for(int l = 0; l<resolJ; l++){
		    try{ray = new Ray(vp.getSource(), new Vector(vp.getSource(), sc.getPointAt(k,l)));
			Stack<Double> iRefs = new Stack<Double>();
			iRefs.push(this.iRefraction);
			computedColor = RayTracer.getAllComp(this.elements,
							     this.spotLights, 
							     this.ambientLight, 
							     ray, 
							     iRefs, //Pile d'indices de refraction des transmissions
							     this.infinityColor, // Couleur de l'infini
							     this.nbIter//Nombre max de deviation calculees
							     ); 
								
			img.addColor(k,l,computedColor, false);
			imgBuff.setRGB(l, k, (((int)(computedColor.getRedComp()*255) << 16) | ((int)(computedColor.getGreenComp()*255) << 8) | (int)(computedColor.getBlueComp()*255)));
						
		    }catch(NoIntersectionException e){
			img.addColor(k,l,this.infinityColor,false);	
			imgBuff.setRGB(l, k, (((int)(this.infinityColor.getRedComp()*255) << 16) | ((int)(this.infinityColor.getGreenComp()*255) << 8) | (int)(this.infinityColor.getBlueComp()*255)));				
		    }
		}
		//Notification
		for(SceneObserver obs : obsList)
		    obs.updateImage(imgBuff, vp);
				
		if((100*(double)(k+1)/(double)resolI) % 5 == 0)
		    System.out.print(".");
	    }		
	    System.out.println("\nImage calculee ("+(System.currentTimeMillis()-execTime)+"ms)");	
	    this.addImage(vp, img);
	    // Pour exporter un fichier résultat
	    //	    img.exportPPM(vp); 
	}
    }


}
