/** 
 * Representer une facette.
 * @author Clément Dolou
 * @version 1.1
 */
package raytracing.scene.positionable.element;

import raytracing.scene.geometry.*;
import raytracing.scene.Ray;

public class Triangle extends Plan{

    /** Initialiser une facette à partir de 3 points, son nom et un materiau.
     * @param points Point délimitant la facette. les vecteur P0P1 et P0P2 détermine le sens de la normal
     * @param name Nom de la facette
     * @param material Materiau de la facette
     */
    public Triangle(Point points[], String name, Material material){
	super(points, name, material);
    }    

    /** @see Element */
    public Point getIntersection(Ray ray){
    	Vector solveResult = this.solveSystem(ray);
		
	if (solveResult.getX() > 1+Geometry.EPSILON ||
	    solveResult.getX() < 0-Geometry.EPSILON ||
	    solveResult.getY() > (1-solveResult.getX()) + Geometry.EPSILON ||
	    solveResult.getY() < 0-Geometry.EPSILON)
	    throw new NoIntersectionException();

	Point resultat = Geometry.translate(ray.getSource(), Geometry.realProduct(ray.getSupport(), solveResult.getZ()));

	if(!resultat.isEqual(ray.getSource())){
	    if(Geometry.scalarProduct(ray.getSupport(), new Vector(ray.getSource(), resultat)) < 0)
		throw new NoIntersectionException();
	}else{
	    throw new NoIntersectionException();
	}	

	return 	resultat;
    }
}
