/** 
 * Representer un element positionnable. 
 * @author Matthias Benkort
 * @version 2.0
 */
package raytracing.scene.positionable.element;

import raytracing.scene.Ray;
import raytracing.scene.positionable.Positionable;
import raytracing.scene.geometry.*;
import java.util.Stack;
import java.util.EmptyStackException;

abstract public class Element extends Positionable{

    /** Materiau composant l'element. */
    private Material material;

    /** Initialiser un element a partir de son nom, son materiau.
     * @param name Nom de l'element
     * @param material Materiau de l'element.
     */
    public Element(String name, Material material){
	super(name);	
	this.setMaterial(material);
    }

    /** Obtenir le point d'intersection entre un element et un rayon.
     * @return Le point d'intersection.
     * @throws NoIntersectionException si le rayon n'intersecte pas l'element.
     * @param ray Le rayon considere.
     */
    public abstract Point getIntersection(Ray ray);
	
    /** Obtenir le vecteur normal unitaire, dirigé vers l'exterieur, a l'element en un certain point. 
     * @return Le vecteur normal unitaire.
     * @param intersection Point considere.
     */
    public abstract Vector getNormal(Point intersection);

    /** Obtenir le rayon réfléchi en un point de l'element.
     * @param incRay Le rayon incident
     * @param intersection Le point de reflection
     * @return Le rayon réfléchi
     */
    public Ray getReflected(Ray incRay, Point intersection){
	Vector normal = this.getNormal(intersection);
	double rayScalarNormal = Geometry.scalarProduct(incRay.getSupport(), normal);			

	if(rayScalarNormal > 0)
	    normal = Geometry.realProduct(normal, -1);			

	return new Ray(intersection, Geometry.symmetrical(incRay.getSupport(), normal));
    }

    /** Obtenir le rayon refracté en un point de l'element.
     * @param incRay Le rayon incident
     * @param intersection Le point de réfraction
     * @param nRef L'indice du milieu d'ou provient le rayon
     * @return Le rayon refracté
     */
    public Ray getRefracted(Ray incRay, Point intersection, Stack<Double>  iRefs){
	Vector normal = this.getNormal(intersection);
	double rayScalarNormal = Geometry.scalarProduct(incRay.getSupport(), normal);			
		
	// Récupération des indices de réfractions, et mise à jour de la pile.
	double nRef = iRefs.peek();
	double nRefPrim = this.getMaterial().getIRefraction();	

	//rayScalarNormal > 0 => on quitte l'element.
	if(rayScalarNormal > 0){
	    normal = Geometry.realProduct(normal, -1);
	    rayScalarNormal *= -1;
	    iRefs.pop();
	    try{
		nRefPrim = iRefs.peek();
	    }catch(EmptyStackException e){
		throw new RuntimeException("Point de vue à l'interieur d'un objet");
	    }
	}else{
	    iRefs.push(nRefPrim);
	}

	// Calculer le rayon refracté
	double coeffNormal = (nRefPrim*nRefPrim)/(nRef*nRef) + rayScalarNormal*rayScalarNormal -1;
	coeffNormal = -(rayScalarNormal + Math.sqrt(coeffNormal));
		
	Vector vRefrac = Geometry.realProduct(Geometry.sum(incRay.getSupport(), Geometry.realProduct(normal, coeffNormal)), nRef/nRefPrim);
		
	return(new Ray(intersection, vRefrac));
    }

    /** @see Positionable
     */
    public String toString(){
	return this.name+"(objet)";
    }

    /** Obtenir le materiau d'un element.
     * @return Le materiau de l'element
     */
    public Material getMaterial(){
	return this.material;
    }
	
    /** Definir le materiau d'un element.
     * @param material Materiau de l'element.
     * @throws IllegalArgumentException Lorsque material est null
     */
    public void setMaterial(Material material){
	if(material == null)
	    throw new IllegalArgumentException("Materiau invalide");
		
	this.material = material;
    }
}
