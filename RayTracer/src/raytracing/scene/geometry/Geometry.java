/**Geometry permet d'effectuer des calculs vectoriels à partir de points et de
 * vecteurs
 * @author Clément Dolou, Maxime Hervé
 * @version 1.4
 */
package raytracing.scene.geometry;

public abstract class Geometry {
    public static final double EPSILON = Math.pow(10,-9);

    /** Distance entre deux points.
     * @param p1  1er point
     * @param p2  2nd point
     * @return distance entre this et autre
     */
    public static double distance(Point p1, Point p2) {
	return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) +
			 Math.pow(p1.getY() - p2.getY(), 2) +
			 Math.pow(p1.getZ() - p2.getZ(), 2));
    }
  


    /** Produit scalaire de deux vecteurs.
     * @param v1 1er vecteur
     * @param v2 2eme vecteur
     * @return produit scalaire de v1 et v2
     */
    public static double scalarProduct( Vector v1 , Vector v2 ) {
	return v1.getX()*v2.getX() + v1.getY()*v2.getY() + v1.getZ()*v2.getZ();
    }
	


    /** Image d'un point par une translation.
     * @param p point
     * @param v vecteur
     * @return image de p par v
     */
    public static Point translate(Point p, Vector v) {
	return new Point(p.getX() + v.getX(),
			 p.getY() + v.getY(),
			 p.getZ() + v.getZ());
    }



    /** Produit vectoriel de deux vecteurs
     * @param v1 1er vecteur
     * @param v2 2eme vecteur
     * @return produit vectoriel de v1 et v2
     */
    public static Vector vectorialProduct( Vector v1 , Vector v2) {
	return new Vector(v1.getY()*v2.getZ() - v1.getZ()*v2.getY(),
			  v1.getZ()*v2.getX() - v1.getX()*v2.getZ(),
			  v1.getX()*v2.getY() - v1.getY()*v2.getX());
    }



    /** Multiplication d'un vecteur par un scalaire
     * @param v  vecteur
     * @param k  reel
     * @return produit réel de v par k 
     */

    public static Vector realProduct  ( Vector v , double k ) {
	return new Vector (k*v.getX(),  k*v.getY(), k*v.getZ());
    }


    /** Somme de deux de vecteurs
     * @param v1 1er vecteur
     * @param v2 2eme vecteur
     * @return somme de v1 et v2
     */
    public static Vector sum( Vector v1 , Vector v2) {
	return new Vector(v1.getX()+v2.getX(),
			  v1.getY()+v2.getY(),
			  v1.getZ()+v2.getZ());
    }
	
    /**Normaliser un vecteur
     * @param vector Vecteur a normaliser
     * @return Le vecteur normalise
     */
    public static Vector normalize(Vector vector){
	return Geometry.realProduct(vector,1/vector.getNorm());
    }
	
    /** Calcule le determinant d'un systeme de trois vecteurs
     * @param v1 1er vecteur
     * @param v2 2nd vecteur
     * @param v3 3eme vecteur
     */
    public static double det(Vector v1, Vector v2, Vector v3) {
	return v1.getX()*(v2.getY()*v3.getZ()-v3.getY()*v2.getZ()) -
	    v1.getY()*(v2.getX()*v3.getZ()-v3.getX()*v2.getZ()) +
	    v1.getZ()*(v2.getX()*v3.getY()-v3.getX()*v2.getY());
    }

    /** Calcule le symétrique orthogonal d'un vecteur par rapport à un
     * autre vecteur.
     * @param v1 vecteur dont on veut calculer le symétrique
     * @param v2 vecteur de symétrie qui doit être unitaire
     */
    public static Vector symmetrical(Vector v, Vector n) {
	// On calcule l'opposé du projeté
	Vector negProjection = realProduct(n, -2*scalarProduct(v, n));
	Vector result = sum(v, negProjection);
	return result;
    }

    /** Determine si un point appartient a un plan
     * @param p0 1er point caracterisant le plan
     * @param p0 1er point caracterisant le plan
     * @param p0 1er point caracterisant le plan
     * @param m Point dont l'appartenance est a verifier
     * @return vrai si le point appartient au plan, faux sinon
     */
    public static boolean belong(Point p0, Point p1, Point p2, Point m){
	Vector n = vectorialProduct(new Vector(p0,p1), new Vector(p0,p2));
	double d = p0.getX()*n.getX() + p0.getY()*n.getY() + p0.getZ()*n.getZ();
	return Math.abs(n.getX()*m.getX()+n.getY()*m.getY()+n.getZ()*m.getZ()-d) < EPSILON;
    }
}





