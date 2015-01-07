/** 
 * Representer un plan positionnable.
 * @author Clément Dolou
 * @version 1.1
 */
package raytracing.scene.positionable.element;

import raytracing.scene.geometry.*;
import raytracing.scene.Ray;

public class Plan extends Element{

    /** Triplet de point servant a la construction et representation du plan. */
    private Point points[] = new Point[3];

    /** Resoudre le systeme lineaire permettant de determiner le point d'intersection d'un rayon avec le plan.
     * @return Le vecteur solution du systeme.
     * @param ray Le rayon a considerer.
     */
    protected Vector solveSystem(Ray ray){
    	double detSystem = Geometry.det(
					new Vector(points[0],points[1]), 
					new Vector(points[0], points[2]), 
					Geometry.realProduct(ray.getSupport(),-1));
			
	if(Math.abs(detSystem) < Geometry.EPSILON)
	    throw new NoIntersectionException();
			
	double alpha = Geometry.det(
				    new Vector(this.points[0],ray.getSource()),
				    new Vector(this.points[0], this.points[2]),
				    Geometry.realProduct(ray.getSupport(),-1));
					
	double beta = Geometry.det(
				   new Vector(this.points[0],this.points[1]),
				   new Vector(this.points[0], ray.getSource()),
				   Geometry.realProduct(ray.getSupport(),-1));
					
	double gamma = Geometry.det(
				    new Vector(this.points[0],this.points[1]),
				    new Vector(this.points[0], this.points[2]),
				    new Vector(this.points[0],ray.getSource()));
					
	Vector resultat = Geometry.realProduct(new Vector(alpha,beta,gamma), 1/detSystem);
	return resultat;
    }

    /** Initialiser un plan depuis un triplet de points.
     * @param points Triplet de points.
     */
    public Plan(Point[] points, String name, Material material){
    	super(name, material);
	this.setPoints(points);
    }

    /** @see Element */
    public Point getIntersection(Ray ray){
    	Vector v = this.solveSystem(ray);
	Point resultat = Geometry.translate(ray.getSource(), Geometry.realProduct(ray.getSupport(), v.getZ()));	
		
	if(!resultat.isEqual(ray.getSource())){
	    if(Geometry.scalarProduct(ray.getSupport(), new Vector(ray.getSource(), resultat)) < 0)
		throw new NoIntersectionException();
	}else{
	    throw new NoIntersectionException();
	}	
			
	return 	resultat;
    }

    /** @see Element */
    public Vector getNormal(Point intersection){
	Vector resultat = Geometry.vectorialProduct(
						    new Vector(this.points[0],this.points[1]),
						    new Vector(this.points[0],this.points[2]));
				
    	return Geometry.normalize(resultat);
    }

    /** Obtenir les points qui composent le plan.
     * @return Le triplet de point qui compose le plan.
     */
    public Point[] getPoints(){
	return this.points;
    }

    /** Definir les points qui composent le plan.
     * @param points Le triplet de point qui decrira le plan.
     */
    public void setPoints(Point points[]){
	if(points == null || points.length != 3)
	    throw new IllegalArgumentException("Points indefinis");
			
	if(points[0] == null || points[1] == null || points[2] == null)
	    throw new IllegalArgumentException("Un des points est indefini");
			
	this.points = points;
    }		
}
