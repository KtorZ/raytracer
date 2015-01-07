/** 
 * Representer un point de vue/d'observation.
 */
package raytracing.scene.positionable;

import raytracing.scene.geometry.*;
import raytracing.scene.geometry.Point;

public class ViewPoint extends Positionable{

    /** Point source d'observation. */
    private Point source;

    /** Ecran a travers lequel s'effectue l'observation. */
    private Screen screen;

    /** Angle de vision de l'ecran */
    private double fov;
	
    /** Direction du regard */
    private Vector look;
	
    /** Sens de la vertical */
    private Vector vertical;
	
    /** Resolution i */
    int resI;
	
    /** Résolution j */
    int resJ;
	
	
    /** Initialiser un point de vue a partir d'un point source et d'un ecran.
     * @param source Point source.
     * @param screen Ecran associe au point de vue.
     * @param name La référence du positionable
     * @throws IllegalArgumentException
     */
    public ViewPoint(Point source, Screen screen, String name){
	super(name);
	this.setSource(source);
	this.setScreen(screen);
    }
    
    /** Initialiser un point de vue a partir d'un point source, d'un
     * vecteur donnant la direction du regard, d'un vecteur donnant la
     * direction de la verticale et d'un angle de vision et d'une
     * résolution en I et en J.
     * @param source Point source.
     * @param look Vecteur direction du regard
     * @param vertical Vecteur direction de la verticale, ne doit pas
     * être colinéaire à look
     * @param fov angle de vision qui doit être compris entre 0 et 180
     * degrés
     * @param resI resolution en I
     * @param resJ resolution en J
     * @param name Le nom du positionable
     * @throws IllegalArgumentException
     */
    public ViewPoint(Point source,
		     Vector look,
		     Vector vertical,
		     double fov,
		     int resI,
		     int resJ,
		     String name){
	super(name);
	
	this.setLook(look);
	this.setVertical(vertical);
	this.setSource(source);
	this.setFov(fov);
	this.setResI(resI);
	this.setResJ(resJ);
		
	Point p1, p2, p3;

	double fovR = Math.PI*fov/180;

	// Orthonormaliser la base (look, vertical)
	Vector lookN = Geometry.normalize(this.look);
	Vector verticalN = 
	    Geometry.normalize(Geometry.sum(vertical,
					    Geometry.realProduct(lookN, -Geometry.scalarProduct(vertical, lookN))));
		
	// Vérifier que les vecteurs sont orthogonaux
	if(Math.abs(Geometry.scalarProduct(lookN, verticalN)) > Geometry.EPSILON) {
	    throw new IllegalArgumentException("Les vecteurs du regard" + 
					       " et de la verticale sont colinéaires");
	}
		
	// Calculer la largeur et la hauteur de l'écran
	double largeur = Math.tan(fovR/2)*2;
	double hauteur = resJ*largeur/resI;

	// Calculer le vecteur donnant la direction horizontale.
	Vector horizontal = Geometry.vectorialProduct(lookN, verticalN);

	// Placer le point supérieur gauche.
	p1 = Geometry.translate(source, lookN);
	p1 = Geometry.translate(p1, Geometry.realProduct(horizontal, -largeur/2));
	p1 = Geometry.translate(p1, Geometry.realProduct(verticalN, hauteur/2));

	// Placer le point supérieur droit
	p2 = Geometry.translate(source, lookN);
	p2 = Geometry.translate(p2, Geometry.realProduct(horizontal, largeur/2));
	p2 = Geometry.translate(p2, Geometry.realProduct(verticalN, hauteur/2));

	// Placer le point inférieur gauche
	p3 = Geometry.translate(source, lookN);
	p3 = Geometry.translate(p3, Geometry.realProduct(horizontal, -largeur/2));
	p3 = Geometry.translate(p3, Geometry.realProduct(verticalN, - hauteur/2));

	// Créer l'écran et initialiser les attributs du ViewPoint
	this.screen = new Screen(new Point[] {p1, p2, p3},
				 resI, resJ);
	this.source = source;

    }

    /** @see Element */
    public String toString(){
	return this.name + "(vue)";
    }
    
    /** Obtenir la source d'un point de vue.
     * @return Le point source
     */
    public Point getSource(){
	return this.source;
    }

    /** Obtenir l'ecran associe a un point de vue.
     * @return L'ecran associe
     */
    public Screen getScreen(){
	return this.screen;
    }

    /** Obtenir l'angle de vision
     * @return L'angle de vision
     */
    public double getFov(){
	return this.fov;
    }
	
    /** Obtenir la direction du regard
     * @return La direction du regard
     */
    public Vector getLook(){
	return this.look;
    }
	
    /** Obtenir la direction de la vertical
     * @return La direction de la vertical
     */
    public Vector getVertical(){
	return this.vertical;
    }	
	
    /**Obtenir la resolution i
     * @return La résolution i
     */
    public int getResI(){
	return this.resI;
    }
	
    /** Obtenir la résolution j
     * @return La resolution j
     */
    public int getResJ(){
	return this.resJ;
    }
	
    /** Definir le point source associe a un ecran.
     * @param source Point source
     * @throws IllegalArgumentException Lorsque le point est null
     */
    public void setSource(Point source){
	if(source == null)
	    throw new IllegalArgumentException("Source invalide");
		
	this.source = source;
    }

    /** Definir l ecran associe a un point de vue.
     * @param screen Ecran associe
     * @throws IllegalArgumentException Lorsque l'ecran est null
     */
    public void setScreen(Screen screen){
	if(screen == null)
	    throw new IllegalArgumentException("Ecran invalide");
			
	this.screen = screen;
    }
	
    /** Definir l'angle de vision d'un point de vue
     * @param fov L'angle de vision
     * @throws IllegalArgumentException Lorsque le fov n'est pas dans [1;179]
     */
    private void setFov(double fov){
	// Vérifier que l'angle de vision est compris entre 1 et 179
	// et le convertir en radians
	if (fov > 179 || fov < 1){
	    throw new IllegalArgumentException("Angle de vision en dehors de " + 
					       "[1;179]");
	}	
		
	this.fov = fov;
    }
	
    /** Definir le sens du regard 	
     * @param look Le sens du regard
     * @throws IllegalArgumentException lorsque le sens est null
     */
    private void setLook(Vector look){
	if(look == null || look.isZero())
	    throw new IllegalArgumentException("Sens du regard invalide");
			
	this.look = look;
    }
	
    /** Definir le sens de la vertical 	
     * @param vertical Le sens de la vertical 
     * @throws IllegalArgumentException lorsque le sens est null ou nul
     */
    private void setVertical(Vector vertical){
	if(vertical == null || vertical.isZero())
	    throw new IllegalArgumentException("Sens de la verticale invalide");
			
	this.vertical = vertical;
    }
	
    /** Définir la résolution i
     * @param resI La resolution i
     * @throws IllegalArgumentException Lorsque i <= 0
     */
    private void setResI(int resI){
	if(resI <= 0)
	    throw new IllegalArgumentException("Résolution i invalide");
			
	this.resI = resI;
    }
	
    /** Définir la résolution j
     * @param resJ La resolution j
     * @throws IllegalArgumentException Lorsque j <= 0
     */
    private void setResJ(int resJ){
	if(resJ <= 0)
	    throw new IllegalArgumentException("Résolution j invalide");
			
	this.resJ = resJ;
    }
	
	
}