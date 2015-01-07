/** 
 * Representer une sphere positionnable.
 * @author Matthias Benkort
 * @version 1.6
 */
package raytracing.scene.positionable.element;

import raytracing.scene.geometry.*;
import raytracing.scene.Ray;
import java.util.Stack;
import java.util.EmptyStackException;

public class Sphere extends Element{

    /** Le rayon de la sphere. */
    private double radius;

    /** Le centre de la sphere. */
    private Point center;

    /** Initialiser une sphere a partir de son rayon et de son centre
     * @param center Le centre de la sphere
     * @param radius Le rayon de la sphere
     * @throws IllegalArgumentException
     */
    public Sphere(Point center, double radius, String name, Material material){
	super(name, material);
	this.setCenter(center);
	this.setRadius(radius);
    }

    /** @see Element */
    public Point getIntersection(Ray ray){
	Vector SC =  new Vector(ray.getSource(), this.center);
	double dScalSC = Geometry.scalarProduct(ray.getSupport(),SC);
	double delta = this.radius*this.radius+dScalSC*dScalSC-Math.pow(SC.getNorm(),2);
		
	if(delta < 0)
	    throw new NoIntersectionException();		
		
	double t1 = dScalSC-Math.sqrt(delta);
	double t2 = dScalSC+Math.sqrt(delta);

	double t = (t1 < 0) ? t2 : Math.min(t1,t2);
	double tSymm = t1+t2-t; 
		
	Point intersection2 = Geometry.translate(ray.getSource(), Geometry.realProduct(ray.getSupport(), tSymm));
	Point intersection = Geometry.translate(ray.getSource(), Geometry.realProduct(ray.getSupport(), t));

	if(intersection.isEqual(ray.getSource())){
	    intersection = intersection2;
	}
		
	if(Geometry.scalarProduct(ray.getSupport(), new Vector(ray.getSource(), intersection)) < 0)
	    throw new NoIntersectionException();
			
	return intersection;
    }
		
    /** @see Element */
    public Vector getNormal(Point intersection){
	if(Geometry.distance(intersection, this.center) - this.radius > Geometry.EPSILON)
	    throw new RuntimeException("Intersection pas sur la sphere");
			
	return Geometry.realProduct(new Vector(this.center, intersection),1/this.radius);
    }

    /** Obtenir le rayon de la sphere.
     * @return Le rayon
     */
    public double getRadius(){
	return this.radius;
    }

    /** Obtenir le centre de la sphere.
     * @return Le centre de la sphere
     */
    public Point getCenter(){
	return this.center;
    }
	
    /** Definir le rayon de la sphere
     * @param Le rayon de la sphere
     * @throws IllegalArgumentException Lorsque le rayon est negatif.
     */
    public void setRadius(double radius){
	if(radius <= 0)
	    throw new IllegalArgumentException("Rayon invalide");
		
	this.radius = radius;
    }
	
    /** Definir le centre de la sphere
     * @param Le centre de la sphere
     * @throws IllegalArgumentException Lorsque le centre est null
     */
    public void setCenter(Point center){
	if(center == null)
	    throw new IllegalArgumentException("Centre invalide");
			
	this.center = center;
    }
	

}
