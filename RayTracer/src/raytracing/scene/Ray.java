/** 
 * Representer un rayon lumineux.
 * @author Matthias Benkort
 * @version 1.0
 */
package raytracing.scene;

import raytracing.scene.geometry.*;

public class Ray{

    /** Le vecteur directeur, unitaire, support du rayon. */
    private Vector support;

    /** Le point source depuis lequel le rayon est emis. */
    private Point source;

    /** Initialiser un rayon a partir d'un point source et d'un vecteur diectionnel
     * @param source Le point source du rayon.
     * @param support Le vecteur directionnel
     * @throws IllegalArgumentException
     */
    public Ray(Point source, Vector support){
	this.setSource(source);
	this.setSupport(support);
    }

    /** Obtenir le point source du rayon.
     * @return Le point source du rayon
     */
    public Point getSource(){
	return this.source;
    }

    /** Obtenir le vecteur directeur du rayon.
     * @return Vecteur directeur du rayon
     */
    public Vector getSupport(){
	return this.support;
    }

    /** Definir le point source du rayon.
     * @param source Point a l'origine du rayon.
     * @throws IllegalArgumentException Lorsque le point est null
     */
    public void setSource(Point source){
	if(source == null)
	    throw new IllegalArgumentException("Point source invalide");
			
	this.source = source;
    }

    /** Definir le vecteur directeur du rayon, et le rendre unitaire
     * @param support Vecteur dirigeant le rayon
     * @throws IllegalArgumentException Lorsque le vecteur support est null ou nul
     */
    public void setSupport(Vector support){
	if(support == null || support.getNorm() < Geometry.EPSILON)
	    throw new IllegalArgumentException("Vecteur support invalide");
			
	this.support = Geometry.normalize(support);
    }

    /** @see Object */
    public String toString(){
	return "src : ("+source.getX()+", "+source.getY()+", "+source.getZ()+")"+
	    " Supp : ("+support.getX()+", "+support.getY()+", "+support.getZ()+")";
    }

}
